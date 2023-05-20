package com.github.sib_energy_craft.backpacks.item;

import lombok.Getter;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
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
public class WearableStorageItem extends Item implements ShapedRecipeOnCraft {
    protected static final String INVENTORY_NBT = "inventory";

    protected final WearableStorageScreenHandlerFactory.ScreenHandlerFactory screenHandlerFactory;
    @Getter
    protected final int capacity;
    @Getter
    protected final int width;
    @Getter
    protected final int height;
    private final String screenTitle;

    public WearableStorageItem(@NotNull Settings settings,
                               int width,
                               int height,
                               @NotNull WearableStorageScreenHandlerFactory.ScreenHandlerFactory screenHandlerFactory,
                               @NotNull String screenTitle) {
        super(settings);
        this.width = width;
        this.height = height;
        this.capacity = width * height;
        this.screenHandlerFactory = screenHandlerFactory;
        this.screenTitle = screenTitle;
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
        player.openHandledScreen(new WearableStorageScreenHandlerFactory(screenHandlerFactory, itemStack, screenTitle));
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
        simpleInventory.addListener(sender -> writeNbt(itemStack, simpleInventory.stacks));
        if(nbt == null) {
            return simpleInventory;
        }
        var nbtInventory = nbt.getCompound(INVENTORY_NBT);
        Inventories.readNbt(nbtInventory, simpleInventory.stacks);
        return simpleInventory;
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack,
                              @Nullable World world,
                              @NotNull List<Text> tooltip,
                              @NotNull TooltipContext context) {
        var item = stack.getItem();
        if(!(item instanceof WearableStorageItem wearableStorageItem)) {
            return;
        }
        var inventory = wearableStorageItem.getInventory(stack);

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

    @Override
    public void onCraft(@NotNull ShapedRecipe shapedRecipe,
                        @NotNull CraftingInventory craftingInventory,
                        @NotNull DynamicRegistryManager dynamicRegistryManager,
                        @NotNull ItemStack itemStack) {
        var craftedInventory = new SimpleInventory(capacity);
        for (int i = 0; i < craftingInventory.size(); i++) {
            var sourceStack = craftingInventory.getStack(i);
            if(sourceStack.isEmpty()) {
                continue;
            }
            var sourceItem = sourceStack.getItem();
            if(!(sourceItem instanceof WearableStorageItem wearableStorageItem)) {
                continue;
            }
            var sourceInventory = wearableStorageItem.getInventory(sourceStack);
            for (ItemStack stack : sourceInventory.stacks) {
                craftedInventory.addStack(stack);
            }
        }
        writeNbt(itemStack, craftedInventory.stacks);
    }
}
