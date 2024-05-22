package com.github.wintersteve25.tau.components.layout;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Stack implements PrimitiveUIComponent {
    private final Iterable<UIComponent> children;

    public Stack(Iterable<UIComponent> children) {
        this.children = children;
    }

    public Stack(UIComponent... children) {
        this.children = Arrays.stream(children).collect(Collectors.toList());
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        for (UIComponent child : children) {
            UIBuilder.build(layout, theme, child, context);
        }

        return layout.getSize();
    }
}
