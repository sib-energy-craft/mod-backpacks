package com.github.sib_energy_craft.backpacks.mixin;

import com.github.sib_energy_craft.backpacks.item.ShapedRecipeOnCraft;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author sibmaks
 * @since 0.0.1
 */
@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {

    @Inject(method = "craft", at = @At("RETURN"))
    public void craft(CraftingInventory craftingInventory,
                      DynamicRegistryManager registryManager,
                      CallbackInfoReturnable<ItemStack> callbackInfoReturnable) {
        var outputStack = callbackInfoReturnable.getReturnValue();
        var item = outputStack.getItem();
        if(item instanceof ShapedRecipeOnCraft modifiedItemStackOnShapedRecipe) {
            modifiedItemStackOnShapedRecipe.onCraft(getThis(), craftingInventory, registryManager, outputStack);
        }
    }

    private ShapedRecipe getThis() {
        return (ShapedRecipe) (Object) this;
    }
}
