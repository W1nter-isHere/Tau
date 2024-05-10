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

public final class Align implements PrimitiveUIComponent {

    private final UIComponent child;
    private final LayoutSetting horizontal;
    private final LayoutSetting vertical;

    public Align(UIComponent child, LayoutSetting horizontal, LayoutSetting vertical) {
        this.child = child;
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, List<Renderable> renderables, List<Renderable> tooltips, List<DynamicUIComponent> dynamicUIComponents, List<GuiEventListener> eventListeners) {
        if (horizontal != null) {
            layout.pushLayoutSetting(Axis.HORIZONTAL, horizontal);
        }

        if (vertical != null) {
            layout.pushLayoutSetting(Axis.VERTICAL, vertical);
        }

        SimpleVec2i size = UIBuilder.build(layout, theme, child, renderables, tooltips, dynamicUIComponents, eventListeners);

        if (horizontal != null) {
            layout.popLayoutSetting(Axis.HORIZONTAL);
        }

        if (vertical != null) {
            layout.popLayoutSetting(Axis.VERTICAL);
        }

        return size;
    }


    public static final class Builder {
        private LayoutSetting horizontal;
        private LayoutSetting vertical;

        public Builder() {
        }

        public Builder withHorizontal(LayoutSetting horizontal) {
            this.horizontal = horizontal;
            return this;
        }

        public Builder withVertical(LayoutSetting vertical) {
            this.vertical = vertical;
            return this;
        }

        public Align build(UIComponent child) {
            return new Align(child, horizontal, vertical);
        }
    }
}
