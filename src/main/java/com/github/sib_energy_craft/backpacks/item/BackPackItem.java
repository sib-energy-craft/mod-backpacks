package com.github.sib_energy_craft.backpacks.item;

import lombok.Getter;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public class BackPackItem extends Item {
    protected static final String INVENTORY_NBT = "inventory";

    protected final BackPackScreenHandlerFactory.ScreenHandlerFactory screenHandlerFactory;
    @Getter
    protected final int capacity;
    @Getter
    protected final int width;
    @Getter
    protected final int height;
    private final String screenTitle;

    public BackPackItem(@NotNull Settings settings,
                        int width,
                        int height,
                        @NotNull BackPackScreenHandlerFactory.ScreenHandlerFactory screenHandlerFactory,
                        @NotNull String screenTitle) {
        super(settings);
        this.width = width;
        this.height = height;
        this.capacity = width * height;
        this.screenHandlerFactory = screenHandlerFactory;
        this.screenTitle = screenTitle;
    }

    @Override
    public void onCraft(@NotNull ItemStack stack,
                        @NotNull World world,
                        @NotNull PlayerEntity player) {
        var inventory = DefaultedList.ofSize(capacity, ItemStack.EMPTY);
        writeNbt(stack, inventory);
    }

    private void writeNbt(@NotNull ItemStack stack,
                          @NotNull DefaultedList<ItemStack> inventory) {
        var inventoryCompound = new NbtCompound();
        Inventories.writeNbt(inventoryCompound, inventory);

        var nbt = stack.getOrCreateNbt();
        nbt.put(INVENTORY_NBT, inventoryCompound);
    }

    @Override
    public @NotNull TypedActionResult<ItemStack> use(@NotNull World world,
                                                     @NotNull PlayerEntity player,
                                                     @NotNull Hand hand) {
        var itemStack = player.getStackInHand(hand);
        boolean inSneakingPose = player.isInSneakingPose();
        if(inSneakingPose) {
            return  TypedActionResult.pass(itemStack);
        }
        player.openHandledScreen(new BackPackScreenHandlerFactory(screenHandlerFactory, itemStack, screenTitle));
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public @NotNull ActionResult useOnBlock(@NotNull ItemUsageContext context) {
        var world = context.getWorld();
        if(!(world instanceof ServerWorld serverWorld)) {
            return ActionResult.PASS;
        }
        var player = context.getPlayer();
        if(player == null) {
            return ActionResult.PASS;
        }
        boolean inSneakingPose = player.isInSneakingPose();
        if(!inSneakingPose) {
            return ActionResult.PASS;
        }
        var backPack = context.getStack();
        var backPackInventory = getInventory(backPack);
        if(backPackInventory.isEmpty()) {
            return ActionResult.PASS;
        }
        var interactedBlockEntity = serverWorld.getBlockEntity(context.getBlockPos());
        if(!(interactedBlockEntity instanceof Inventory blockInventory)) {
            return ActionResult.PASS;
        }
        boolean inserted = false;
        for (var backPackStack : backPackInventory.stacks) {
            if(backPackStack.isEmpty()) {
                continue;
            }
            if(!blockInventory.containsAny(Set.of(backPackStack.getItem()))) {
                continue;
            }
            for (int i = 0; i < blockInventory.size(); i++) {
                if(!blockInventory.isValid(i, backPackStack)) {
                    continue;
                }
                var blockInventoryStack = blockInventory.getStack(i);
                if(blockInventoryStack.isEmpty()) {
                    var toInsert = backPackStack.copy();
                    backPackStack.setCount(0);
                    blockInventory.setStack(i, toInsert);
                    inserted = true;
                } else if(blockInventoryStack.isItemEqual(backPackStack) &&
                        blockInventoryStack.getCount() < blockInventoryStack.getMaxCount()) {
                    int maxToInsert = blockInventoryStack.getMaxCount() - blockInventoryStack.getCount();
                    int toInsert = Math.min(maxToInsert, backPackStack.getCount());
                    backPackStack.decrement(toInsert);
                    blockInventoryStack.increment(toInsert);
                    inserted = true;
                }
            }
        }
        if(inserted) {
            writeNbt(backPack, backPackInventory.stacks);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @NotNull
    public SimpleInventory getInventory(@NotNull ItemStack itemStack) {
        var nbt = itemStack.getNbt();
        var simpleInventory = new SimpleInventory(capacity);
        if(nbt == null) {
            return simpleInventory;
        }
        var nbtInventory = nbt.getCompound(INVENTORY_NBT);
        Inventories.readNbt(nbtInventory, simpleInventory.stacks);
        return simpleInventory;
    }

    public void onClosed(@NotNull ItemStack itemStack,
                         @NotNull SimpleInventory backPackInventory) {
        writeNbt(itemStack, backPackInventory.stacks);
    }


    @Override
    public void appendTooltip(@NotNull ItemStack stack,
                              @Nullable World world,
                              @NotNull List<Text> tooltip,
                              @NotNull TooltipContext context) {
        var item = stack.getItem();
        if(!(item instanceof BackPackItem backPackItem)) {
            return;
        }
        var inventory = backPackItem.getInventory(stack);

        var textColor = Color.GRAY.getRGB();
        var textStyle = Style.EMPTY.withColor(textColor);

        var stackNames = new HashSet<Text>();
        for (var inventoryStack : inventory.stacks) {
            if(inventoryStack.isEmpty()) {
                continue;
            }
            var stackName = inventoryStack.getName();
            stackNames.add(stackName);
        }
        for (var stackName : stackNames) {
            tooltip.add(MutableText.of(stackName.getContent()).setStyle(textStyle));
        }
    }
}
