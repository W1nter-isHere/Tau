package com.github.wintersteve25.tau.components.interactable;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.components.layout.Column;
import com.github.wintersteve25.tau.components.render.Transform;
import com.github.wintersteve25.tau.components.utils.Clip;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.layout.LayoutSetting;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.FlexSizeBehaviour;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import com.github.wintersteve25.tau.utils.Transformation;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ListView extends DynamicUIComponent implements PrimitiveUIComponent, GuiEventListener {

    private static final int scrollSensitivity = 8;

    private final List<UIComponent> children;
    private final LayoutSetting childrenAlignment;
    private final int spacing;

    private int scrollOffset;
    private int maxScroll;

    private SimpleVec2i size;
    private SimpleVec2i position;

    private boolean focus;

    public ListView(List<UIComponent> children, LayoutSetting childrenAlignment, int spacing) {
        this.children = children;
        this.childrenAlignment = childrenAlignment;
        this.spacing = spacing;
        scrollOffset = 0;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        size = layout.getSize();
        position = layout.getPosition(size);

        Column.Builder column = new Column.Builder()
                .withSpacing(spacing)
                .withAlignment(childrenAlignment);

        // TODO is there a way to avoid building it again?
        SimpleVec2i childrenSize = UIBuilder.build(layout.copy(), theme, column.build(children), new BuildContext());
        maxScroll = childrenSize.y - size.y + 1; // 1 for padding

        return UIBuilder.build(
                layout,
                theme,
                new Clip.Builder().build(new Transform(column.withSizeBehaviour(FlexSizeBehaviour.MAX).build(children), Transformation.translate(new Vector3f(0, scrollOffset, 0)))),
                context
        );
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
        if (scrollOffset >= maxScroll && pScrollY < 0) {
            return GuiEventListener.super.mouseScrolled(pMouseX, pMouseY, pScrollX, pScrollY);
        }

        if (scrollOffset >= 0 && pScrollY > 0) {
            return GuiEventListener.super.mouseScrolled(pMouseX, pMouseY, pScrollX, pScrollY);
        }

        scrollOffset += pScrollY > 0 ? scrollSensitivity : -scrollSensitivity;
        scrollOffset = clamp(scrollOffset, -maxScroll, 0);
        rebuild();

        return true;
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return SimpleVec2i.within((int) pMouseX, (int) pMouseY, position, size);
    }

    @Override
    public void setFocused(boolean pFocused) {
        focus = pFocused;
    }

    @Override
    public boolean isFocused() {
        return focus;
    }

    private int clamp(int x, int min, int max) {
        if (x < min) {
            return min;
        }

        return Math.min(x, max);
    }

    public static final class Builder {
        private int spacing;
        private LayoutSetting childrenAlignment;

        public Builder() {
        }

        public Builder withSpacing(int spacing) {
            this.spacing = spacing;
            return this;
        }

        public Builder withAlignment(LayoutSetting alignment) {
            childrenAlignment = alignment;
            return this;
        }

        public ListView build(UIComponent... children) {
            return build(Arrays.asList(children));
        }

        public ListView build(List<UIComponent> children) {
            return new ListView(children, childrenAlignment == null ? LayoutSetting.CENTER : childrenAlignment, spacing);
        }
    }
}
