package com.github.sib_energy_craft.backpacks.load.client;

import com.github.sib_energy_craft.backpacks.load.ScreenHandlers;
import com.github.sib_energy_craft.backpacks.screen.*;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.register;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public final class ClientScreens implements DefaultClientModInitializer {

    static {
        HandledScreens.register(ScreenHandlers.LEATHER_PACKET, LeatherPacketScreen::new);
        HandledScreens.register(ScreenHandlers.LEATHER_BAG, LeatherBagScreen::new);
        HandledScreens.register(ScreenHandlers.SIMPLE_BACKPACK, SimpleBackPackScreen::new);
        HandledScreens.register(ScreenHandlers.CAMPING_BACKPACK, CampingBackPackScreen::new);
    }
}
