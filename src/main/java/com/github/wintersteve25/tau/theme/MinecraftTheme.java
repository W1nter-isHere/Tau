package com.github.wintersteve25.tau.theme;

import com.github.wintersteve25.tau.Tau;
import com.github.wintersteve25.tau.utils.Color;
import com.github.wintersteve25.tau.utils.InteractableState;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.List;

public class MinecraftTheme implements Theme {
    public static final MinecraftTheme INSTANCE = new MinecraftTheme();

    private static final ResourceLocation TEXTURE = new ResourceLocation(Tau.MOD_ID, "textures/gui/container.png");
    private static final Color TEXT = new Color(0xFFE8E8E8);

    @Override
    public void drawButton(GuiGraphics graphics, int x, int y, int width, int height, float partialTicks, int mouseX, int mouseY, InteractableState state) {
        graphics.blitWithBorder(TEXTURE, x, y, 0, 166 + state.getNumber() * 20, width, height, 200, 20, 2, 3, 2, 2);
    }

    @Override
    public void drawContainer(GuiGraphics graphics, int x, int y, int width, int height, float partialTicks, int mouseX, int mouseY) {
        graphics.blitWithBorder(TEXTURE, x, y, 0, 0, width, height, 176, 166, 4);
    }

    @Override
    public void drawScrollbar(GuiGraphics graphics, int x, int y, int width, int height, float partialTicks, int mouseX, int mouseY) {
    }

    @Override
    public void drawTooltip(GuiGraphics graphics, int mouseX, int mouseY, Font font, List<ClientTooltipComponent> tooltips) {
        renderTooltipInternal(graphics, font, tooltips, mouseX, mouseY, this::positionTooltip);
    }

    // Copied from GuiGraphics
    private void renderTooltipInternal(GuiGraphics graphics, Font pFont, List<ClientTooltipComponent> pComponents, int pMouseX, int pMouseY, ClientTooltipPositioner pTooltipPositioner) {
        if (!pComponents.isEmpty()) {
            net.neoforged.neoforge.client.event.RenderTooltipEvent.Pre preEvent = net.neoforged.neoforge.client.ClientHooks.onRenderTooltipPre(ItemStack.EMPTY, graphics, pMouseX, pMouseY, graphics.guiWidth(), graphics.guiHeight(), pComponents, pFont, pTooltipPositioner);
            if (preEvent.isCanceled()) return;
            int i = 0;
            int j = pComponents.size() == 1 ? -2 : 0;

            for(ClientTooltipComponent clienttooltipcomponent : pComponents) {
                int k = clienttooltipcomponent.getWidth(preEvent.getFont());
                if (k > i) {
                    i = k;
                }

                j += clienttooltipcomponent.getHeight();
            }

            int i2 = i;
            int j2 = j;
            Vector2ic vector2ic = pTooltipPositioner.positionTooltip(graphics.guiWidth(), graphics.guiHeight(), preEvent.getX(), preEvent.getY(), i2, j2);
            int l = vector2ic.x();
            int i1 = vector2ic.y();
            graphics.pose().pushPose();
            int j1 = 400;
            net.neoforged.neoforge.client.event.RenderTooltipEvent.Color colorEvent = net.neoforged.neoforge.client.ClientHooks.onRenderTooltipColor(ItemStack.EMPTY, graphics, l, i1, preEvent.getFont(), pComponents);
            graphics.drawManaged(() -> TooltipRenderUtil.renderTooltipBackground(graphics, l, i1, i2, j2, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundEnd(), colorEvent.getBorderStart(), colorEvent.getBorderEnd()));
            graphics.pose().translate(0.0F, 0.0F, 400.0F);
            int k1 = i1;

            for(int l1 = 0; l1 < pComponents.size(); ++l1) {
                ClientTooltipComponent clienttooltipcomponent1 = pComponents.get(l1);
                clienttooltipcomponent1.renderText(preEvent.getFont(), l, k1, graphics.pose().last().pose(), graphics.bufferSource());
                k1 += clienttooltipcomponent1.getHeight() + (l1 == 0 ? 2 : 0);
            }

            k1 = i1;

            for(int k2 = 0; k2 < pComponents.size(); ++k2) {
                ClientTooltipComponent clienttooltipcomponent2 = pComponents.get(k2);
                clienttooltipcomponent2.renderImage(preEvent.getFont(), l, k1, graphics);
                k1 += clienttooltipcomponent2.getHeight() + (k2 == 0 ? 2 : 0);
            }

            graphics.pose().popPose();
        }
    }

    private Vector2ic positionTooltip(int screenWidth, int screenHeight, int pMouseX, int pMouseY, int pWidth, int pHeight) {
        Vector2i vector2i = (new Vector2i(pMouseX, pMouseY)).add(12, -12);
        this.positionTooltip(screenWidth, screenHeight, vector2i, pWidth, pHeight);
        return vector2i;
    }

    private void positionTooltip(int screenWidth, int screenHeight, Vector2i pPosition, int pWidth, int pHeight) {
        if (pPosition.x + pWidth > screenWidth) {
            pPosition.x = Math.max(pPosition.x - 24 - pWidth, 4);
        }

        int i = pHeight + 3;
        if (pPosition.y + i > screenHeight) {
            pPosition.y = screenHeight - i;
        }
    }

    @Override
    public Color getTextColor() {
        return TEXT;
    }
}
