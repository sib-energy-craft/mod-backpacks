package com.github.sib_energy_craft.backpacks.screen.slot;

import com.github.sib_energy_craft.backpacks.tag.WearableStorageTags;
import com.github.sib_energy_craft.sec_utils.utils.TagUtils;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public class WearableStorageSlot extends Slot {
    public WearableStorageSlot(@NotNull Inventory inventory,
                               int index,
                               int x,
                               int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(@NotNull ItemStack stack) {
        if(TagUtils.hasTag(WearableStorageTags.WEARABLE_STORAGE, stack)) {
            return false;
        }
        return super.canInsert(stack);
    }
}
