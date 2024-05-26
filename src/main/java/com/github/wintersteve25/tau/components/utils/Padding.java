package com.github.wintersteve25.tau.components.utils;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Axis;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.utils.Pad;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

import java.util.List;

public final class Padding implements PrimitiveUIComponent {

    private final Pad pad;
    private final UIComponent child;

    public Padding(Pad pad, UIComponent child) {
        this.pad = pad;
        this.child = child;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        if (pad == null) {
            return UIBuilder.build(layout, theme, child, context);
        }

        if (pad.left == 0 || pad.right == 0) {
            layout.pushOffset(Axis.HORIZONTAL, pad.left - pad.right);
        } else {
            layout.pushOffset(Axis.HORIZONTAL, pad.left);
            layout.pushSizeMod(Axis.HORIZONTAL, - pad.right - pad.left);
        }

        if (pad.top == 0 || pad.bottom == 0) {
            layout.pushOffset(Axis.VERTICAL, pad.top - pad.bottom);
        } else {
            layout.pushOffset(Axis.VERTICAL, pad.top);
            layout.pushSizeMod(Axis.VERTICAL, - pad.bottom - pad.top);
        }

        SimpleVec2i size = UIBuilder.build(layout, theme, child, context);

        layout.popOffset(Axis.VERTICAL);
        layout.popOffset(Axis.HORIZONTAL);

        if (pad.left != 0 && pad.right != 0) {
            layout.popSizeMod(Axis.HORIZONTAL);
        }

        if (pad.top != 0 && pad.bottom != 0) {
            layout.popSizeMod(Axis.VERTICAL);
        }

        return size.addNew(pad.getSize());
    }
}
