package com.github.wintersteve25.tau.build;

import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.ArrayList;
import java.util.List;

public record BuildContext(
        List<Renderable> renderables,
        List<Renderable> tooltips,
        List<DynamicUIComponent> dynamicUIComponents,
        List<GuiEventListener> eventListeners,
        List<SimpleVec2i> slots
) {
    public BuildContext() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public void addAll(BuildContext context) {
        this.renderables.addAll(context.renderables);
        this.tooltips.addAll(context.tooltips);
        this.dynamicUIComponents.addAll(context.dynamicUIComponents);
        this.eventListeners.addAll(context.eventListeners);
        this.slots.addAll(context.slots);
    }
}
