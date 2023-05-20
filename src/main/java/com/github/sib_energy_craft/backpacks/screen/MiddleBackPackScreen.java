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
public class MiddleBackPackScreen extends BackPackScreen<MiddleBackPackScreenHandler> {
    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/middle_backpack.png");

    public MiddleBackPackScreen(@NotNull MiddleBackPackScreenHandler handler,
                                @NotNull PlayerInventory inventory,
                                @NotNull Text title) {
        super(TEXTURE, handler, inventory, title, 176, 206, 92);
    }
}
