package com.github.wintersteve25.tau.menu;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.RenderedUIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public class DataDependentRender implements PrimitiveUIComponent {
    private final RenderedUIComponent renderer;

    public DataDependentRender(RenderedUIComponent renderer) {
        this.renderer = renderer;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        return UIBuilder.build(layout, theme, renderer, context);
    }
}
