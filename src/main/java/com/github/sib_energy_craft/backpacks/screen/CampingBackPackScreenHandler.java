package com.github.sib_energy_craft.backpacks.screen;

import com.github.sib_energy_craft.backpacks.load.client.ClientScreens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public class CampingBackPackScreenHandler extends WearableStorageScreenHandler {

    public CampingBackPackScreenHandler(int syncId,
                                        @NotNull ItemStack backPackStack,
                                        @NotNull PlayerInventory playerInventory) {
        super(ClientScreens.CAMPING_BACKPACK, syncId, playerInventory, backPackStack, 200, 142, 35, 22);
    }

    public CampingBackPackScreenHandler(int syncId,
                                        @NotNull PlayerInventory inventory,
                                        @NotNull PacketByteBuf buf) {
        this(syncId, buf.readItemStack(), inventory);
    }
}
