package com.github.wintersteve25.tau.components.base;

import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

import java.util.List;

/**
 * A UI Component with access to lower level content. For more can be used for more complicated components that need to be customly rendered
 */
public interface PrimitiveUIComponent extends UIComponent {
    SimpleVec2i build(Layout layout, Theme theme, List<Renderable> renderables, List<Renderable> tooltips, List<DynamicUIComponent> dynamicUIComponents, List<GuiEventListener> eventListeners);

    @Override
    default UIComponent build(Layout layout, Theme theme) {
        return null;
    }
}
