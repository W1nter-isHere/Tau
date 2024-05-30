package com.github.wintersteve25.tau.menu;

import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;

public class DataDependentComponent extends DynamicUIComponent {

    private final UIComponent child;

    public DataDependentComponent(UIComponent child) {
        this.child = child;
    }

    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return child;
    }
}
