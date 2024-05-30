package com.github.wintersteve25.tau.components.base;

import com.github.wintersteve25.tau.components.render.Render;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.RenderProvider;

public interface RenderedUIComponent extends UIComponent, RenderProvider {
    @Override
    default UIComponent build(Layout layout, Theme theme) {
        return new Render(this);
    }
}
