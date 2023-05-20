package com.github.sib_energy_craft.backpacks.item;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public interface ShapedRecipeOnCraft {
    /**
     * Method called on return value of {@link ShapedRecipe#craft(CraftingInventory, DynamicRegistryManager)}
     *
     * @param shapedRecipe crafting recipe
     * @param dynamicRegistryManager manager from invoke
     * @param itemStack output stack
     */
    void onCraft(@NotNull ShapedRecipe shapedRecipe,
                @NotNull CraftingInventory craftingInventory,
                @NotNull DynamicRegistryManager dynamicRegistryManager,
                @NotNull ItemStack itemStack);
}
