package com.github.sib_energy_craft.backpacks.screen;

import com.github.sib_energy_craft.backpacks.load.client.ClientScreens;
import com.github.sib_energy_craft.backpacks.load.server.ServerScreens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public class LeatherPacketScreenHandler extends WearableStorageScreenHandler {

    public LeatherPacketScreenHandler(int syncId,
                                      @NotNull ItemStack backPackStack,
                                      @NotNull PlayerInventory playerInventory) {
        super(ClientScreens.LEATHER_PACKET, syncId, playerInventory, backPackStack, 106, 48, 62, 22);
    }

    public LeatherPacketScreenHandler(int syncId,
                                      @NotNull PlayerInventory inventory,
                                      @NotNull PacketByteBuf buf) {
        this(syncId, buf.readItemStack(), inventory);
    }

    public LeatherPacketScreenHandler(int syncId,
                                      @NotNull PlayerInventory playerInventory) {
        super(ServerScreens.LEATHER_PACKET, syncId, playerInventory, ItemStack.EMPTY, 106, 48, 62, 22);
    }
}
