package com.github.wintersteve25.tau.components.base;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

/**
 * A UI Component with access to lower level content. For more can be used for more complicated components that need to be customly rendered
 */
public interface PrimitiveUIComponent extends UIComponent {
    SimpleVec2i build(Layout layout, Theme theme, BuildContext context);

    @Override
    default UIComponent build(Layout layout, Theme theme) {
        return null;
    }
}
