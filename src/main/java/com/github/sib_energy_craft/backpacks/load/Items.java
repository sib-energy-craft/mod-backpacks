package com.github.sib_energy_craft.backpacks.load;

import com.github.sib_energy_craft.backpacks.item.BackPackItem;
import com.github.sib_energy_craft.backpacks.screen.MiddleBackPackScreenHandler;
import com.github.sib_energy_craft.backpacks.screen.SmallBackPackScreenHandler;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Rarity;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.register;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public final class Items implements DefaultModInitializer {
    public static final BackPackItem SMALL_BACKPACK;
    public static final BackPackItem MIDDLE_BACKPACK;

    static {
        var simpleSettings = new Item.Settings()
                .rarity(Rarity.COMMON)
                .maxCount(1);

        var smallBackPackItem = new BackPackItem(
                simpleSettings,
                3,
                3,
                SmallBackPackScreenHandler::new,
                "container.small_backpack"
        );
        SMALL_BACKPACK = register(ItemGroups.INVENTORY, Identifiers.of("small_backpack"), smallBackPackItem);

        var middleBackPackItem = new BackPackItem(
                simpleSettings,
                5,
                5,
                MiddleBackPackScreenHandler::new,
                "container.middle_backpack"
        );
        MIDDLE_BACKPACK = register(ItemGroups.INVENTORY, Identifiers.of("middle_backpack"), middleBackPackItem);
    }
}
