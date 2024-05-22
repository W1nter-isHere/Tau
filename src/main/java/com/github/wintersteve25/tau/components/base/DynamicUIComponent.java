package com.github.wintersteve25.tau.components.base;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.build.UIBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * A UI component that can be updated on demand
 */
public abstract class DynamicUIComponent implements UIComponent {

    private Layout layout;
    private Theme theme;
    private DynamicChange<Renderable> renderables;
    private DynamicChange<Renderable> tooltips;
    private DynamicChange<DynamicUIComponent> dynamicUIComponents;
    private DynamicChange<GuiEventListener> eventListeners;

    public boolean dirty;

    public void tick() {
    }

    public void destroy() {
    }

    protected void rebuild() {
        dirty = true;
    }

    public final void rebuildImmediately() {
        List<Renderable> replacementRenderables = new ArrayList<>();
        List<Renderable> replacementTooltips = new ArrayList<>();
        List<DynamicUIComponent> replacementDynamicUIComponents = new ArrayList<>();
        List<GuiEventListener> replacementEventListeners = new ArrayList<>();

        UIBuilder.build(layout, theme, this, new BuildContext(replacementRenderables, replacementTooltips, replacementDynamicUIComponents, replacementEventListeners));

        replacementRenderables = new ArrayList<>(replacementRenderables);
        replacementTooltips = new ArrayList<>(replacementTooltips);
        replacementDynamicUIComponents = new ArrayList<>(replacementDynamicUIComponents);
        replacementEventListeners = new ArrayList<>(replacementEventListeners);

        int renderablesCountDiff = renderables.update(replacementRenderables);
        int tooltipsCountDiff = tooltips.update(replacementTooltips);
        int eventListenersCountDiff = eventListeners.update(replacementEventListeners);
        int dynamicCountDiff = dynamicUIComponents.update(replacementDynamicUIComponents);

        this.renderables.endIndex += renderablesCountDiff;
        this.tooltips.endIndex += tooltipsCountDiff;
        this.eventListeners.endIndex += eventListenersCountDiff;
        this.dynamicUIComponents.endIndex += dynamicCountDiff;

        for (DynamicUIComponent component : dynamicUIComponents.data) {
            if (component.renderables.startIndex > this.renderables.endIndex) {
                component.renderables.startIndex += renderablesCountDiff;
                component.renderables.endIndex += renderablesCountDiff;
            }
            if (component.tooltips.startIndex > this.tooltips.endIndex) {
                component.tooltips.startIndex += tooltipsCountDiff;
                component.tooltips.endIndex += tooltipsCountDiff;
            }
            if (component.eventListeners.startIndex > this.eventListeners.endIndex) {
                component.eventListeners.startIndex += eventListenersCountDiff;
                component.eventListeners.endIndex += eventListenersCountDiff;
            }
            if (component.dynamicUIComponents.startIndex > this.dynamicUIComponents.endIndex) {
                component.dynamicUIComponents.startIndex += dynamicCountDiff;
                component.dynamicUIComponents.endIndex += dynamicCountDiff;
            }
        }
    }

    public final void finalizeDynamic(BuildContext context) {
        if (renderables.endIndex == -1) renderables.endIndex = context.renderables().size();
        if (tooltips.endIndex == -1) tooltips.endIndex = context.tooltips().size();
        if (dynamicUIComponents.endIndex == -1) dynamicUIComponents.endIndex = context.dynamicUIComponents().size();
        if (eventListeners.endIndex == -1) eventListeners.endIndex = context.eventListeners().size();

        if (renderables.data == null) renderables.data = context.renderables();
        if (tooltips.data == null) tooltips.data = context.tooltips();
        if (eventListeners.data == null) eventListeners.data = context.eventListeners();
        if (dynamicUIComponents.data == null) dynamicUIComponents.data = context.dynamicUIComponents();
    }

    public final void buildDynamic(BuildContext context, Layout layout, Theme theme) {
        this.layout = layout;
        this.theme = theme;

        if (renderables == null) renderables = new DynamicUIComponent.DynamicChange<>();
        if (tooltips == null) tooltips = new DynamicUIComponent.DynamicChange<>();
        if (dynamicUIComponents == null) dynamicUIComponents = new DynamicUIComponent.DynamicChange<>();
        if (eventListeners == null) eventListeners = new DynamicUIComponent.DynamicChange<>();

        if (renderables.startIndex == -1) renderables.startIndex = context.renderables().size();
        if (tooltips.startIndex == -1) tooltips.startIndex = context.tooltips().size();
        if (dynamicUIComponents.startIndex == -1) dynamicUIComponents.startIndex = context.dynamicUIComponents().size();
        if (eventListeners.startIndex == -1) eventListeners.startIndex = context.eventListeners().size();
    }

    public final static class DynamicChange<T> {
        public int startIndex = -1;
        public int endIndex = -1;
        public List<T> data;

        public int update(List<T> replacement) {
            data.subList(startIndex, endIndex).clear();
            data.addAll(startIndex, replacement);
            return replacement.size() - (endIndex - startIndex);
        }
    }
}
