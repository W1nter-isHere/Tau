package com.github.wintersteve25.tau.components.base;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
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
    private DynamicChange<SimpleVec2i> slots;

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
        List<SimpleVec2i> replacementSlots = new ArrayList<>();

        UIBuilder.build(layout, theme, this, new BuildContext(replacementRenderables, replacementTooltips, replacementDynamicUIComponents, replacementEventListeners, replacementSlots));

        replacementRenderables = new ArrayList<>(replacementRenderables);
        replacementTooltips = new ArrayList<>(replacementTooltips);
        replacementDynamicUIComponents = new ArrayList<>(replacementDynamicUIComponents);
        replacementEventListeners = new ArrayList<>(replacementEventListeners);
        replacementSlots = new ArrayList<>(replacementSlots);

        int renderablesCountDiff = renderables.update(replacementRenderables);
        int tooltipsCountDiff = tooltips.update(replacementTooltips);
        int eventListenersCountDiff = eventListeners.update(replacementEventListeners);
        int dynamicCountDiff = dynamicUIComponents.update(replacementDynamicUIComponents);
        int slotsCountDiff = slots.update(replacementSlots);

        this.renderables.endIndex += renderablesCountDiff;
        this.tooltips.endIndex += tooltipsCountDiff;
        this.eventListeners.endIndex += eventListenersCountDiff;
        this.dynamicUIComponents.endIndex += dynamicCountDiff;
        this.slots.endIndex += slotsCountDiff;

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
            if (component.slots.startIndex > this.slots.endIndex) {
                component.slots.startIndex += slotsCountDiff;
                component.slots.endIndex += slotsCountDiff;
            }
        }
    }

    public final void finalizeDynamic(BuildContext context) {
        if (renderables.endIndex == -1) renderables.endIndex = context.renderables().size();
        if (tooltips.endIndex == -1) tooltips.endIndex = context.tooltips().size();
        if (dynamicUIComponents.endIndex == -1) dynamicUIComponents.endIndex = context.dynamicUIComponents().size();
        if (eventListeners.endIndex == -1) eventListeners.endIndex = context.eventListeners().size();
        if (slots.endIndex == -1) slots.endIndex = context.slots().size();

        if (renderables.data == null) renderables.data = context.renderables();
        if (tooltips.data == null) tooltips.data = context.tooltips();
        if (eventListeners.data == null) eventListeners.data = context.eventListeners();
        if (dynamicUIComponents.data == null) dynamicUIComponents.data = context.dynamicUIComponents();
        if (slots.data == null) slots.data = context.slots();
    }

    public final void buildDynamic(BuildContext context, Layout layout, Theme theme) {
        this.layout = layout;
        this.theme = theme;

        if (renderables == null) renderables = new DynamicUIComponent.DynamicChange<>();
        if (tooltips == null) tooltips = new DynamicUIComponent.DynamicChange<>();
        if (dynamicUIComponents == null) dynamicUIComponents = new DynamicUIComponent.DynamicChange<>();
        if (eventListeners == null) eventListeners = new DynamicUIComponent.DynamicChange<>();
        if (slots == null) slots = new DynamicUIComponent.DynamicChange<>();

        if (renderables.startIndex == -1) renderables.startIndex = context.renderables().size();
        if (tooltips.startIndex == -1) tooltips.startIndex = context.tooltips().size();
        if (dynamicUIComponents.startIndex == -1) dynamicUIComponents.startIndex = context.dynamicUIComponents().size();
        if (eventListeners.startIndex == -1) eventListeners.startIndex = context.eventListeners().size();
        if (slots.startIndex == -1) slots.startIndex = context.slots().size();
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
