package com.github.sib_energy_craft.backpacks.tag;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

/**
 * @author sibmaks
 * @since 0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WearableStorageTags {
    public static final TagKey<Item> WEARABLE_STORAGE;

    static {
        WEARABLE_STORAGE =  TagKey.of(RegistryKeys.ITEM, Identifiers.of("wearable_storage"));
    }
}
