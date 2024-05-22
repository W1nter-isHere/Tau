package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.utils.Clip;
import com.github.wintersteve25.tau.components.utils.Text;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.Size;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public class TestClip implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Center(
            new Clip(
                new Text.Builder("HelloAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
                new SimpleVec2i(0, 0),
                Size.percentage(0.65f, 1f)));
    }
}
