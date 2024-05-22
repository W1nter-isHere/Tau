package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.utils.Text;
import com.github.wintersteve25.tau.components.utils.Tooltip;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import net.minecraft.network.chat.Component;

public class TestTooltip implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Center(
            new Tooltip.Builder()
                .withComponent(Component.literal("Test"))
                .build(new Text.Builder("HALLO!"))
        );
    }
}
