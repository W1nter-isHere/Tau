package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.utils.Container;
import com.github.wintersteve25.tau.components.utils.Sized;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.Size;
import net.minecraft.client.gui.components.events.GuiEventListener;

public class TestDynamic extends DynamicUIComponent implements GuiEventListener {
    private boolean clicked;

    @Override
    public UIComponent build(Layout layout, Theme theme) {
        clicked = !clicked;

        if (clicked) {
            return new Container.Builder();
        }

        return new Center(new Sized(
            Size.staticSize(200, 200),
            new Container.Builder()
        ));
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        rebuild();
        return GuiEventListener.super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public void setFocused(boolean pFocused) {
    }

    @Override
    public boolean isFocused() {
        return false;
    }
}
