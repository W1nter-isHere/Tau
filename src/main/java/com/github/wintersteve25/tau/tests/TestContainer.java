package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.utils.Container;
import com.github.wintersteve25.tau.components.utils.Text;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;

public class TestContainer implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Container.Builder()
            .withChild(new Center(new Text.Builder("Blue!!")));
    }
}
