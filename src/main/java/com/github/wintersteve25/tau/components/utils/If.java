package com.github.wintersteve25.tau.components.utils;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public class If implements PrimitiveUIComponent {

    private final boolean condition;
    private final UIComponent child;

    public If(boolean condition, UIComponent child) {
        this.condition = condition;
        this.child = child;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        if (condition) return UIBuilder.build(layout, theme, child, context);
        return SimpleVec2i.zero();
    }
}
