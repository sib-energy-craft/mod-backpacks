package com.github.sib_energy_craft.backpacks.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public abstract class WearableStorageScreen<T extends WearableStorageScreenHandler> extends HandledScreen<T> {

    protected final Identifier texture;

    public WearableStorageScreen(@NotNull Identifier texture,
                                 @NotNull T handler,
                                 @NotNull PlayerInventory inventory,
                                 @NotNull Text title,
                                 int backgroundWidth,
                                 int backgroundHeight,
                                 int playerInventoryTitleBottomOffset) {
        super(handler, inventory, title);
        this.texture = texture;
        this.titleY = 6;
        this.backgroundWidth = backgroundWidth;
        this.backgroundHeight = backgroundHeight;
        this.playerInventoryTitleY = this.backgroundHeight - playerInventoryTitleBottomOffset;
    }

    @Override
    protected void drawBackground(@NotNull DrawContext drawContext, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, texture);
        drawContext.drawTexture(texture, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(@NotNull DrawContext drawContext, int mouseX, int mouseY, float delta) {
        renderBackground(drawContext);
        super.render(drawContext, mouseX, mouseY, delta);
        drawMouseoverTooltip(drawContext, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
