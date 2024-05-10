package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.Center;
import com.github.wintersteve25.tau.components.Sized;
import com.github.wintersteve25.tau.components.WidgetWrapper;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.Size;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;

public class TestWidgetWrapper implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Center(new Sized(
            Size.staticSize(200, 20),
            new WidgetWrapper(new ExtendedSlider(
                0, 0, 0, 0,
                Component.literal("Prefix"),
                Component.literal("Suffix"),
                0, 1, 0, true
            ))
        ));
    }
}
