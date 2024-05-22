package com.github.wintersteve25.tau.build;

import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.ArrayList;
import java.util.List;

public class UIBuilder {
    /**
     * @param layout The layout of this ui component. Used to position children components
     * @param uiComponent The ui component to build into a list of renderables
     * @param context A build context containing the underlying renderables and such
     * @return The size of the component
     */
    public static SimpleVec2i build(Layout layout, Theme theme, UIComponent uiComponent, BuildContext context) {
        return build(layout, theme, uiComponent, context, SimpleVec2i.zero());
    }

    // the size is the accumulated size of this component branch
    private static SimpleVec2i build(Layout layout, Theme theme, UIComponent uiComponent, BuildContext context, SimpleVec2i size) {
        if (uiComponent instanceof DynamicUIComponent dynamicUIComponent) {
            dynamicUIComponent.buildDynamic(context, layout.copy(), theme);
            context.dynamicUIComponents().add(dynamicUIComponent);
        }

        if (uiComponent instanceof GuiEventListener) {
            context.eventListeners().add((GuiEventListener) uiComponent);
        }

        if (uiComponent instanceof PrimitiveUIComponent primitiveUIComponent) {
            size.add(primitiveUIComponent.build(layout, theme, context));
        }

        UIComponent next = uiComponent.build(layout, theme);

        if (next == null) {
            finishDynamicUIComponent(uiComponent, context);
            return size;
        }

        SimpleVec2i resultSize = build(layout, theme, next, context, size);
        finishDynamicUIComponent(uiComponent, context);
        return resultSize;
    }

    private static void finishDynamicUIComponent(UIComponent uiComponent, BuildContext context) {
        if (uiComponent instanceof DynamicUIComponent dynamicUIComponent) {
            dynamicUIComponent.finalizeDynamic(context);
        }
    }

    /**
     * Rebuilds all dynamic components that requires rebuilding
     * @param dynamicUIComponents All dynamic components
     */
    public static void rebuildAndTickDynamicUIComponents(List<DynamicUIComponent> dynamicUIComponents) {
        for (DynamicUIComponent dynamicUIComponent : dynamicUIComponents) {
            dynamicUIComponent.tick();
        }

        for (DynamicUIComponent component : new ArrayList<>(dynamicUIComponents)) {
            if (component.dirty) component.rebuildImmediately();
            component.dirty = false;
        }
    }
}
