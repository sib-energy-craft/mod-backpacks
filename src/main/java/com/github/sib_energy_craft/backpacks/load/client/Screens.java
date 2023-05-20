package com.github.sib_energy_craft.backpacks.load.client;

import com.github.sib_energy_craft.backpacks.screen.MiddleBackPackScreenHandler;
import com.github.sib_energy_craft.backpacks.screen.SmallBackPackScreenHandler;
import com.github.sib_energy_craft.backpacks.screen.MiddleBackPackScreen;
import com.github.sib_energy_craft.backpacks.screen.SmallBackPackScreen;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.register;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public final class Screens implements DefaultClientModInitializer {
    public static final ScreenHandlerType<SmallBackPackScreenHandler> SMALL_BACKPACK;
    public static final ScreenHandlerType<MiddleBackPackScreenHandler> MIDDLE_BACKPACK;

    static {
        SMALL_BACKPACK = register(Identifiers.of("small_backpack"), SmallBackPackScreenHandler::new,
                SmallBackPackScreen::new);
        MIDDLE_BACKPACK = register(Identifiers.of("middle_backpack"), MiddleBackPackScreenHandler::new,
                MiddleBackPackScreen::new);
    }
}
