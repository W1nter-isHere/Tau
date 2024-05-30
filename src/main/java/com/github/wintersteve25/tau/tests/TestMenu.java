package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.components.inventory.ItemSlot;
import com.github.wintersteve25.tau.components.utils.Container;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.menu.TauContainerMenu;
import com.github.wintersteve25.tau.menu.UIMenu;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public class TestMenu implements UIMenu {

    @Override
    public UIComponent build(Layout layout, Theme theme, TauContainerMenu containerMenu) {
//        return new Container.Builder()
//                .withChild(new ItemSlot(containerMenu.slots));
        return null;
    }

    @Override
    public SimpleVec2i getSize() {
        return new SimpleVec2i(400, 400);
    }
}
