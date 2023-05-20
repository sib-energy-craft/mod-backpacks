package com.github.sib_energy_craft.backpacks.item;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public class BackPackScreenHandlerFactory implements NamedScreenHandlerFactory, ExtendedScreenHandlerFactory {
    private final ScreenHandlerFactory screenHandlerFactory;
    private final ItemStack backPackStack;
    private final String screenTitle;

    public BackPackScreenHandlerFactory(@NotNull ScreenHandlerFactory screenHandlerFactory,
                                        @NotNull ItemStack backPackStack,
                                        @NotNull String screenTitle) {
        this.screenHandlerFactory = screenHandlerFactory;
        this.backPackStack = backPackStack;
        this.screenTitle = screenTitle;
    }

    @NotNull
    @Override
    public Text getDisplayName() {
        return Text.translatable(screenTitle);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId,
                                    @NotNull PlayerInventory playerInventory,
                                    @NotNull PlayerEntity player) {
        return screenHandlerFactory.create(syncId, backPackStack, playerInventory);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeItemStack(backPackStack);
    }

    @FunctionalInterface
    public interface ScreenHandlerFactory {

        /**
         * Create screen handler for backpack item
         *
         * @param syncId sync id
         * @param backPackStack backpack item stack
         * @param playerInventory player inventory
         * @return screen handler for backpack
         */
        @NotNull ScreenHandler create(int syncId,
                                      @NotNull ItemStack backPackStack,
                                      @NotNull PlayerInventory playerInventory);

    }
}
