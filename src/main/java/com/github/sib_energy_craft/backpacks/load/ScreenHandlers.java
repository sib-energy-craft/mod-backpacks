package com.github.sib_energy_craft.backpacks.load;

import com.github.sib_energy_craft.backpacks.screen.CampingBackPackScreenHandler;
import com.github.sib_energy_craft.backpacks.screen.LeatherBagScreenHandler;
import com.github.sib_energy_craft.backpacks.screen.LeatherPacketScreenHandler;
import com.github.sib_energy_craft.backpacks.screen.SimpleBackPackScreenHandler;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public final class ScreenHandlers implements DefaultModInitializer {
    public static final ScreenHandlerType<LeatherPacketScreenHandler> LEATHER_PACKET;
    public static final ScreenHandlerType<LeatherBagScreenHandler> LEATHER_BAG;
    public static final ScreenHandlerType<SimpleBackPackScreenHandler> SIMPLE_BACKPACK;
    public static final ScreenHandlerType<CampingBackPackScreenHandler> CAMPING_BACKPACK;

    static {
        var leatherPacketScreenHandlerType = new ExtendedScreenHandlerType<>(LeatherPacketScreenHandler::new);
        LEATHER_PACKET = Registry.register(Registries.SCREEN_HANDLER, Identifiers.of("leather_packet"),
                leatherPacketScreenHandlerType);

        var leatherBagScreenHandlerType = new ExtendedScreenHandlerType<>(LeatherBagScreenHandler::new);
        LEATHER_BAG = Registry.register(Registries.SCREEN_HANDLER, Identifiers.of("leather_bag"),
                leatherBagScreenHandlerType);

        var simpleBackpackScreenHandlerType = new ExtendedScreenHandlerType<>(SimpleBackPackScreenHandler::new);
        SIMPLE_BACKPACK = Registry.register(Registries.SCREEN_HANDLER, Identifiers.of("simple_backpack"),
                simpleBackpackScreenHandlerType);

        var campingBackpackScreenHandlerType = new ExtendedScreenHandlerType<>(CampingBackPackScreenHandler::new);
        CAMPING_BACKPACK = Registry.register(Registries.SCREEN_HANDLER, Identifiers.of("camping_backpack"),
                campingBackpackScreenHandlerType);
    }
}
