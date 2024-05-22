package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.Tau;
import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.utils.Sized;
import com.github.wintersteve25.tau.components.interactable.TextField;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.Size;
import net.minecraft.network.chat.Component;

public class TestTextField implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Center(
            new Sized(
                Size.staticSize(200, 20),
                new TextField.Builder()
                    .withHintText(Component.literal("Hint Text"))
                    .withOnChange(Tau.LOGGER::info)
            )
        );
    }
}
