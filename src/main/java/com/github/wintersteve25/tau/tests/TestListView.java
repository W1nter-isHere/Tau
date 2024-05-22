package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.components.interactable.Button;
import com.github.wintersteve25.tau.components.interactable.ListView;
import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.utils.*;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.layout.LayoutSetting;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.Size;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public class TestListView implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Center(
            new Sized(
                Size.staticSize(new SimpleVec2i(200, 100)),
                new Container.Builder()
                    .withChild(
                        new ListView.Builder()
                            .withAlignment(LayoutSetting.START)
                            .build(
                                new Text.Builder("Hello"),
                                new Text.Builder("Hello Again"),
                                new Text.Builder("Hello Again"),
                                new Text.Builder("Hello Again"),
                                new Text.Builder("Hello Again"),
                                new Text.Builder("Hello Again"),
                                new Text.Builder("Hello Again"),
                                new Text.Builder("Hello Again"),
                                new Text.Builder("Hello Again"),
                                new Text.Builder("Hello Again"),
                                new Text.Builder("Hello Again"),
                                new Text.Builder("Hello Again"),
                                new Sized(
                                    Size.staticSize(new SimpleVec2i(60, 20)),
                                    new Button.Builder()
                                        .withOnPress((button) -> {})
                                        .build(new Center(new Text.Builder("A Button")))
                                )
                            ))
                    ));
    }
}
