package com.github.sib_energy_craft.backpacks.screen;

import com.github.sib_energy_craft.backpacks.load.ScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public class SimpleBackPackScreenHandler extends WearableStorageScreenHandler {

    public SimpleBackPackScreenHandler(int syncId,
                                       @NotNull PlayerInventory playerInventory,
                                       @NotNull ItemStack backPackStack) {
        super(ScreenHandlers.SIMPLE_BACKPACK, syncId, playerInventory, backPackStack, 182, 124, 44, 22);
    }

    public SimpleBackPackScreenHandler(int syncId,
                                       @NotNull PlayerInventory playerInventory,
                                       @NotNull PacketByteBuf buf) {
        this(syncId, playerInventory, buf.readItemStack());
    }
}
