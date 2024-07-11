package com.github.wintersteve25.tau.components.utils;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public final class Positioned implements PrimitiveUIComponent {
    private final SimpleVec2i position;
    private final UIComponent child;

    public Positioned(SimpleVec2i position, UIComponent child) {
        this.position = position;
        this.child = child;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        Layout childLayout = new Layout(layout.getWidth(), layout.getHeight(), position.x, position.y);
        return UIBuilder.build(childLayout, theme, child, context);
    }
}
