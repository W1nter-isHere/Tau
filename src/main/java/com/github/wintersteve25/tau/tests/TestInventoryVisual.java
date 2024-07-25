package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.components.inventory.PlayerInventory;
import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.utils.Container;
import com.github.wintersteve25.tau.components.utils.Padding;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.FlexSizeBehaviour;
import com.github.wintersteve25.tau.utils.Pad;
import com.github.wintersteve25.tau.utils.Variable;

public class TestInventoryVisual implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Center(new Container.Builder()
                .withSizeBehaviour(FlexSizeBehaviour.MIN)
                .withChild(new Padding(new Pad.Builder().all(10).build(), new PlayerInventory(new Variable<>(true)))));
    }
}
