package com.github.wintersteve25.tau.utils;

import net.minecraft.client.gui.GuiGraphics;

@FunctionalInterface
public interface RenderProvider {
    void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, int x, int y, int width, int height);
}
