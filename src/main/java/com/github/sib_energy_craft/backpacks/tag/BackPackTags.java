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
public final class BackPackTags {
    public static final TagKey<Item> BACKPACK;

    static {
        BACKPACK =  TagKey.of(RegistryKeys.ITEM, Identifiers.of("backpacks"));
    }
}
