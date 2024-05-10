package com.github.wintersteve25.tau.components;

import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.Transformation;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Axis;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.layout.LayoutSetting;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class ListView extends DynamicUIComponent implements PrimitiveUIComponent, ContainerEventHandler {

    private static final int scrollSensitivity = 8;

    private final List<UIComponent> children;
    private final LayoutSetting childrenAlignment;
    private final int spacing;

    private int scrollOffset;
    private int maxScroll;
    private SimpleVec2i position;
    private SimpleVec2i size;
    private List<GuiEventListener> childrenEventHandlers;

    private boolean drag;
    private GuiEventListener focused;

    public ListView(List<UIComponent> children, LayoutSetting childrenAlignment, int spacing) {
        this.children = children;
        this.childrenAlignment = childrenAlignment;
        this.spacing = spacing;
        scrollOffset = 0;
        this.childrenEventHandlers = new ArrayList<>();
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, List<Renderable> renderables, List<Renderable> tooltips, List<DynamicUIComponent> dynamicUIComponents, List<GuiEventListener> eventListeners) {
        size = layout.getSize();
        position = layout.getPosition(size);
        childrenEventHandlers.clear();

        int childrenHeight = 0;
        Layout childrenLayout = new Layout(size.x, size.y, position.x, position.y);
        childrenLayout.pushLayoutSetting(Axis.HORIZONTAL, childrenAlignment);
        List<Renderable> childrenRenderables = new ArrayList<>();

        for (UIComponent child : children) {
            SimpleVec2i childSize = UIBuilder.build(childrenLayout, theme, child, childrenRenderables, tooltips, dynamicUIComponents, childrenEventHandlers);
            childrenHeight += childSize.y;
            childrenLayout.pushOffset(Axis.VERTICAL, childSize.y + spacing);
        }

        maxScroll = childrenHeight - size.y;

        return UIBuilder.build(
            layout,
            theme,
            new Clip.Builder()
                .build(new Transform(new RenderableComponent((graphics, pMouseX, pMouseY, pPartialTicks) -> {
                    for (Renderable renderable : childrenRenderables) {
                        renderable.render(graphics, pMouseX, pMouseY, pPartialTicks);
                    }
                }), Transformation.translate(new Vector3f(0, scrollOffset, 0)))),
            renderables,
            tooltips,
            dynamicUIComponents,
            eventListeners
        );
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return childrenEventHandlers;
    }

    @Override
    public boolean isDragging() {
        return drag;
    }

    @Override
    public void setDragging(boolean pIsDragging) {
        drag = pIsDragging;
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
        if (scrollOffset >= maxScroll && pScrollY < 0) {
            return ContainerEventHandler.super.mouseScrolled(pMouseX, pMouseY, pScrollX, pScrollY);
        }

        if (scrollOffset >= 0 && pScrollY > 0) {
            return ContainerEventHandler.super.mouseScrolled(pMouseX, pMouseY, pScrollX, pScrollY);
        }

        Optional<GuiEventListener> childAt = getChildAt(pMouseX, pMouseY);
        if (childAt.isPresent() && childAt.get().mouseScrolled(pMouseX, pMouseY, pScrollX, pScrollY)) {
            return true;
        }

        scrollOffset += pScrollY > 0 ? scrollSensitivity : -scrollSensitivity;
        scrollOffset = clamp(scrollOffset, -maxScroll, 0);
        rebuild();

        return true;
    }

    @Nullable
    @Override
    public GuiEventListener getFocused() {
        return focused;
    }

    @Override
    public void setFocused(@Nullable GuiEventListener pFocused) {
        focused = pFocused;
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return SimpleVec2i.within((int) pMouseX, (int) pMouseY, position, size);
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
