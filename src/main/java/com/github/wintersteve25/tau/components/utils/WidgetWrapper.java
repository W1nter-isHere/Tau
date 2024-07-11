package com.github.wintersteve25.tau.components.utils;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.gui.components.AbstractWidget;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.layout.Axis;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public final class WidgetWrapper implements PrimitiveUIComponent {

    private final AbstractWidget child;

    public WidgetWrapper(AbstractWidget child) {
        this.child = child;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        child.setWidth(layout.getWidth());
        child.setHeight(layout.getHeight());
        child.setX(layout.getPosition(Axis.HORIZONTAL, child.getWidth()));
        child.setY(layout.getPosition(Axis.VERTICAL, child.getHeight()));

        context.renderables().add(child);
        context.eventListeners().add(child);

        return layout.getSize();
    }
}
