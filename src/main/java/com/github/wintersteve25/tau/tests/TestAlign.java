package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.layout.Align;
import com.github.wintersteve25.tau.components.utils.Text;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.layout.LayoutSetting;
import com.github.wintersteve25.tau.theme.Theme;

public class TestAlign implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Align.Builder()
                .withVertical(LayoutSetting.END)
                .withHorizontal(LayoutSetting.CENTER)
                .build(new Text.Builder("Whats up?"));
    }
}
