package com.github.wintersteve25.tau.components;

import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;

import java.util.List;

public class If implements PrimitiveUIComponent {

    private final boolean condition;
    private final UIComponent child;

    public If(boolean condition, UIComponent child) {
        this.condition = condition;
        this.child = child;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, List<Renderable> renderables, List<Renderable> tooltips, List<DynamicUIComponent> dynamicUIComponents, List<GuiEventListener> eventListeners) {
        if (condition) return UIBuilder.build(layout, theme, child, renderables, tooltips, dynamicUIComponents, eventListeners);
        return SimpleVec2i.zero();
    }
}
