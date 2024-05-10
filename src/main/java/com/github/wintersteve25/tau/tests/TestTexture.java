package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.Center;
import com.github.wintersteve25.tau.components.Texture;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

public class TestTexture implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Center(new Texture.Builder(InventoryScreen.INVENTORY_LOCATION)
                .withUvSize(new SimpleVec2i(176, 166))
                .build());
    }
}
