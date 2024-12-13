package com.github.wintersteve25.tau.theme;

import com.github.wintersteve25.tau.utils.Color;
import com.github.wintersteve25.tau.utils.InteractableState;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public interface Theme {
    void drawButton(GuiGraphics graphics, int x, int y, int width, int height, float partialTicks, int mouseX, int mouseY, InteractableState state);

    void drawContainer(GuiGraphics graphics, int x, int y, int width, int height, float partialTicks, int mouseX, int mouseY);

    void drawScrollbar(GuiGraphics graphics, int x, int y, int width, int height, float partialTicks, int mouseX, int mouseY);

    void drawSlider(
            ResourceLocation defaultBackground,
            ResourceLocation defaultHandle,
            double sliderValue,
            GuiGraphics graphics,
            int x,
            int y,
            int width,
            int height,
            float partialTicks,
            int mouseX,
            int mouseY,
            boolean isHoveredOrFocused
    );

    void drawTooltip(GuiGraphics graphics, int mouseX, int mouseY, Font font, List<ClientTooltipComponent> tooltips, Optional<ClientTooltipPositioner> positioner);

    void drawSlot(GuiGraphics graphics, int x, int y);

    Color getTextColor();
}
