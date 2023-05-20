package com.github.sib_energy_craft.backpacks.screen;

import com.github.sib_energy_craft.backpacks.item.BackPackItem;
import com.github.sib_energy_craft.backpacks.screen.slot.BackPackSlot;
import lombok.Getter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public abstract class BackPackScreenHandler extends ScreenHandler {
    @Getter
    private final ItemStack backPackStack;
    private final BackPackItem backPackItem;
    @Getter
    private final Inventory playerInventory;
    private final SimpleInventory backPackInventory;

    protected BackPackScreenHandler(@NotNull ScreenHandlerType<?> type,
                                    int syncId,
                                    @NotNull PlayerInventory playerInventory,
                                    @NotNull ItemStack backPackStack,
                                    int quickAccessY,
                                    int playerInventoryY,
                                    int backPackX,
                                    int backPackY) {
        super(type, syncId);
        this.backPackStack = backPackStack;
        this.playerInventory = playerInventory;
        var item = backPackStack.getItem();
        if(!(item instanceof BackPackItem _backPackItem)) {
            throw new IllegalArgumentException("Item stack is not BackPackItem");
        }
        this.backPackItem = _backPackItem;

        int slots = 0;
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, slots++, 8 + i * 18, quickAccessY));
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, slots++, 8 + j * 18, playerInventoryY + i * 18));
            }
        }

        int backPackWidth = backPackItem.getWidth();
        this.backPackInventory = _backPackItem.getInventory(backPackStack);
        for (int i = 0; i < backPackInventory.size(); i++) {
            this.addSlot(new BackPackSlot(backPackInventory, i, backPackX + (i % backPackWidth) * 18,
                    backPackY + (i / backPackWidth) * 18));
        }
    }

    @NotNull
    @Override
    public ItemStack quickMove(@NotNull PlayerEntity player, int index) {
        var itemStack = ItemStack.EMPTY;
        var slot = this.slots.get(index);
        if (slot.hasStack()) {
            var slotStack = slot.getStack();
            itemStack = slotStack.copy();
            if(index >= 0 && index < 36 &&!insertItem(slotStack, 36, 36 + backPackItem.getCapacity(), false)) {
                if (index < 9 && !insertItem(slotStack, 9, 36, false)) {
                    return ItemStack.EMPTY;
                } else if (index >= 9 && !insertItem(slotStack, 0, 9, false)) {
                    return ItemStack.EMPTY;
                }
            } else if(!insertItem(slotStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (slotStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, slotStack);
        }
        return itemStack;
    }

    @Override
    public boolean canUse(@NotNull PlayerEntity player) {
        return true;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            backPackItem.onClosed(backPackStack, backPackInventory);
        }
        super.onClosed(player);
    }
}
