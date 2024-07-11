package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.layout.Stack;
import com.github.wintersteve25.tau.components.utils.Container;
import com.github.wintersteve25.tau.components.utils.Sized;
import com.github.wintersteve25.tau.components.interactable.TextField;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.FlexSizeBehaviour;
import com.github.wintersteve25.tau.utils.Size;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.network.chat.Component;

public class TestStack implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Stack(
                FlexSizeBehaviour.MAX,
                new Container.Builder(),
                new Center(new Sized(
                        Size.staticSize(new SimpleVec2i(100, 20)),
                        new TextField.Builder()
                                .withMessage(Component.literal("Hello"))
                                .withHintText(Component.literal("Hello!")))
                ));
    }
}
