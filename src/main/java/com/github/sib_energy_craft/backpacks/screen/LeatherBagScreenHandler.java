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
public class LeatherBagScreenHandler extends WearableStorageScreenHandler {

    public LeatherBagScreenHandler(int syncId,
                                   @NotNull ItemStack backPackStack,
                                   @NotNull PlayerInventory playerInventory) {
        super(ClientScreens.LEATHER_BAG, syncId, playerInventory, backPackStack, 142, 84, 62, 22);
    }

    public LeatherBagScreenHandler(int syncId,
                                   @NotNull PlayerInventory inventory,
                                   @NotNull PacketByteBuf buf) {
        this(syncId, buf.readItemStack(), inventory);
    }
}
