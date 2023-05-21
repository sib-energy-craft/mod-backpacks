package com.github.sib_energy_craft.backpacks.load.server;

import com.github.sib_energy_craft.backpacks.screen.LeatherPacketScreenHandler;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.load.DefaultServerModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public final class ServerScreens implements DefaultServerModInitializer {
    public static final ScreenHandlerType<LeatherPacketScreenHandler> LEATHER_PACKET;

    static {
        var type = new ScreenHandlerType<>(LeatherPacketScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        LEATHER_PACKET = Registry.register(Registries.SCREEN_HANDLER, Identifiers.of("leather_packet"), type);
    }
}
