package com.github.sib_energy_craft.backpacks.screen;

import com.github.sib_energy_craft.backpacks.load.client.Screens;
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
                                       @NotNull ItemStack backPackStack,
                                       @NotNull PlayerInventory playerInventory) {
        super(Screens.SIMPLE_BACKPACK, syncId, playerInventory, backPackStack, 182, 124, 44, 22);
    }

    public SimpleBackPackScreenHandler(int syncId,
                                       @NotNull PlayerInventory inventory,
                                       @NotNull PacketByteBuf buf) {
        this(syncId, buf.readItemStack(), inventory);
    }
}
