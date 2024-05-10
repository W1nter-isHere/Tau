package com.github.wintersteve25.tau.components;

import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;

import java.util.List;

public final class Spacer implements PrimitiveUIComponent {

    private final SimpleVec2i size;

    public Spacer(SimpleVec2i size) {
        this.size = size;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, List<Renderable> renderables, List<Renderable> tooltips, List<DynamicUIComponent> dynamicUIComponents, List<GuiEventListener> eventListeners) {
        return size;
    }
}
