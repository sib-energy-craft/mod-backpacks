package com.github.sib_energy_craft.backpacks.load;

import com.github.sib_energy_craft.backpacks.item.WearableStorageItem;
import com.github.sib_energy_craft.backpacks.screen.CampingBackPackScreenHandler;
import com.github.sib_energy_craft.backpacks.screen.LeatherPacketScreenHandler;
import com.github.sib_energy_craft.backpacks.screen.SimpleBackPackScreenHandler;
import com.github.sib_energy_craft.backpacks.screen.LeatherBagScreenHandler;
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
    public static final Item DRESSED_LEATHER;
    public static final WearableStorageItem LEATHER_PACKET;
    public static final WearableStorageItem LEATHER_BAG;
    public static final WearableStorageItem SIMPLE_BACKPACK;
    public static final WearableStorageItem CAMPING_BACKPACK;

    static {
        var commonSettings = new Item.Settings()
                .rarity(Rarity.COMMON);
        DRESSED_LEATHER = register(ItemGroups.TOOLS, Identifiers.of("dressed_leather"), commonSettings);

        var wearableSettings = new Item.Settings()
                .rarity(Rarity.COMMON)
                .maxCount(1);

        var leatherPacketItem = new WearableStorageItem(
                wearableSettings,
                3,
                1,
                LeatherPacketScreenHandler::new,
                "container.leather_packet"
        );
        LEATHER_PACKET = register(ItemGroups.TOOLS, Identifiers.of("leather_packet"), leatherPacketItem);

        var leatherBagItem = new WearableStorageItem(
                wearableSettings,
                3,
                3,
                LeatherBagScreenHandler::new,
                "container.leather_bag"
        );
        LEATHER_BAG = register(ItemGroups.TOOLS, Identifiers.of("leather_bag"), leatherBagItem);

        var simpleBackPackItem = new WearableStorageItem(
                wearableSettings,
                5,
                5,
                SimpleBackPackScreenHandler::new,
                "container.simple_backpack"
        );
        SIMPLE_BACKPACK = register(ItemGroups.TOOLS, Identifiers.of("simple_backpack"), simpleBackPackItem);

        var campingBackPackItem = new WearableStorageItem(
                wearableSettings,
                6,
                6,
                CampingBackPackScreenHandler::new,
                "container.camping_backpack"
        );
        CAMPING_BACKPACK = register(ItemGroups.TOOLS, Identifiers.of("camping_backpack"), campingBackPackItem);
    }
}
