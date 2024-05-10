package com.github.wintersteve25.tau.components;

import com.github.wintersteve25.tau.layout.LayoutSetting;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Axis;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.utils.FlexSizeBehaviour;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Column implements PrimitiveUIComponent {

    private final Iterable<UIComponent> children;
    private final int spacing;
    private final FlexSizeBehaviour sizeBehaviour;
    private final LayoutSetting alignment;

    public Column(int spacing, FlexSizeBehaviour sizeBehaviour, Iterable<UIComponent> children, LayoutSetting alignment) {
        this.children = children;
        this.spacing = spacing;
        this.sizeBehaviour = sizeBehaviour;
        this.alignment = alignment;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, List<Renderable> renderables, List<Renderable> tooltips, List<DynamicUIComponent> dynamicUIComponents, List<GuiEventListener> eventListeners) {

        SimpleVec2i size;

        if (sizeBehaviour == FlexSizeBehaviour.MIN) {
            size = SimpleVec2i.zero();

            for (UIComponent child : children) {
                SimpleVec2i childSize = UIBuilder.build(layout.copy(), theme, child, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                size.y += childSize.y + spacing;
                size.x = Math.max(size.x, childSize.x);
            }
        } else {
            size = new SimpleVec2i(layout.getWidth(), layout.getHeight());
        }

        Layout childrenLayout = new Layout(size.x, size.y, layout.getPosition(Axis.HORIZONTAL, size.x), layout.getPosition(Axis.VERTICAL, size.y));
        childrenLayout.pushLayoutSetting(Axis.HORIZONTAL, alignment);

        for (UIComponent child : children) {
            SimpleVec2i childSize = UIBuilder.build(childrenLayout, theme, child, renderables, tooltips, dynamicUIComponents, eventListeners);
            childrenLayout.pushOffset(Axis.VERTICAL, childSize.y + spacing);
        }

        return size;
    }


    public static final class Builder {
        private int spacing;
        private FlexSizeBehaviour sizeBehaviour;
        private LayoutSetting alignment;

        public Builder() {
        }

        public Builder withSpacing(int spacing) {
            this.spacing = spacing;
            return this;
        }

        public Builder withSizeBehaviour(FlexSizeBehaviour sizeBehaviour) {
            this.sizeBehaviour = sizeBehaviour;
            return this;
        }

        public Builder withAlignment(LayoutSetting alignment) {
            this.alignment = alignment;
            return this;
        }

        public Column build(UIComponent... children) {
            return build(Arrays.asList(children));
        }

        public Column build(Iterable<UIComponent> children) {
            return new Column(spacing,
                    sizeBehaviour == null
                            ? FlexSizeBehaviour.MIN
                            : sizeBehaviour,
                    children,
                    alignment == null ? LayoutSetting.CENTER : alignment);
        }
    }
}
