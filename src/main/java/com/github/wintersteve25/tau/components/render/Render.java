package com.github.wintersteve25.tau.components.render;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.layout.Axis;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.utils.RenderProvider;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public final class Render implements PrimitiveUIComponent {
    private final RenderProvider renderer;

    public Render(RenderProvider renderer) {
        this.renderer = renderer;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        int width = layout.getWidth();
        int height = layout.getHeight();
        int x = layout.getPosition(Axis.HORIZONTAL, width);
        int y = layout.getPosition(Axis.VERTICAL, height);
        context.renderables().add((guiGraphics, pMouseX, pMouseY, pPartialTicks) -> renderer.render(guiGraphics, pMouseX, pMouseY, pPartialTicks, x, y, width, height));
        return layout.getSize();
    }
}
