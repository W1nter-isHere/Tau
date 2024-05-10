package com.github.wintersteve25.tau.components;

import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Axis;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.layout.LayoutSetting;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

import java.util.List;

public final class Center implements PrimitiveUIComponent {

    private final UIComponent child;

    public Center(UIComponent child) {
        this.child = child;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, List<Renderable> renderables, List<Renderable> tooltips, List<DynamicUIComponent> dynamicUIComponents, List<GuiEventListener> eventListeners) {
        layout.pushLayoutSetting(Axis.HORIZONTAL, LayoutSetting.CENTER);
        layout.pushLayoutSetting(Axis.VERTICAL, LayoutSetting.CENTER);

        SimpleVec2i size = UIBuilder.build(layout, theme, child, renderables, tooltips, dynamicUIComponents, eventListeners);

        layout.popLayoutSetting(Axis.HORIZONTAL);
        layout.popLayoutSetting(Axis.VERTICAL);

        return size;
    }
}
