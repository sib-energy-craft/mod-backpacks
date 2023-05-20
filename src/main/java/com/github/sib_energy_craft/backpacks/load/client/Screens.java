package com.github.sib_energy_craft.backpacks.load.client;

import com.github.sib_energy_craft.backpacks.screen.*;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.register;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public final class Screens implements DefaultClientModInitializer {
    public static final ScreenHandlerType<LeatherPacketScreenHandler> LEATHER_PACKET;
    public static final ScreenHandlerType<LeatherBagScreenHandler> LEATHER_BAG;
    public static final ScreenHandlerType<SimpleBackPackScreenHandler> SIMPLE_BACKPACK;
    public static final ScreenHandlerType<CampingBackPackScreenHandler> CAMPING_BACKPACK;

    static {
        LEATHER_PACKET = register(Identifiers.of("leather_packet"), LeatherPacketScreenHandler::new,
                LeatherPacketScreen::new);
        LEATHER_BAG = register(Identifiers.of("leather_bag"), LeatherBagScreenHandler::new,
                LeatherBagScreen::new);
        SIMPLE_BACKPACK = register(Identifiers.of("simple_backpack"), SimpleBackPackScreenHandler::new,
                SimpleBackPackScreen::new);
        CAMPING_BACKPACK = register(Identifiers.of("camping_backpack"), CampingBackPackScreenHandler::new,
                CampingBackPackScreen::new);
    }
}
