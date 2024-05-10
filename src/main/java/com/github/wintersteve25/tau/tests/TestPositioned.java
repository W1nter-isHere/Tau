package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.Positioned;
import com.github.wintersteve25.tau.components.Text;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public class TestPositioned implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Positioned(
            new SimpleVec2i(100, 20),
            new Text.Builder("Positioned")
        );
    }
}
