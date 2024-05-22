package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.layout.Row;
import com.github.wintersteve25.tau.components.interactable.Button;
import com.github.wintersteve25.tau.components.utils.Sized;
import com.github.wintersteve25.tau.components.utils.Text;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.Size;

public class TestRow implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Center(new Row.Builder()
            .withSpacing(5)
            .build(
                new Sized(
                    Size.staticSize(100, 20),
                    new Button.Builder().build(new Center(new Text.Builder("Hello")))
                ),
                new Text.Builder("Hello"),
                new Text.Builder("This is a row")
            ));
    }
}
