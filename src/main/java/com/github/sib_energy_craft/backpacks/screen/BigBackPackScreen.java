package com.github.sib_energy_craft.backpacks.screen;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public class BigBackPackScreen extends BackPackScreen<BigBackPackScreenHandler> {
    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/big_backpack.png");

    public BigBackPackScreen(@NotNull BigBackPackScreenHandler handler,
                             @NotNull PlayerInventory inventory,
                             @NotNull Text title) {
        super(TEXTURE, handler, inventory, title, 176, 224, 92);
    }
}
