package com.github.wintersteve25.tau.menu;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.util.ArrayList;
import java.util.List;

public class TauMenuHelper {
    public static void registerMenuScreen(RegisterMenuScreensEvent event, TauMenuHolder holder) {
        event.register(holder.get(), new MenuScreens.ScreenConstructor<TauContainerMenu, TauContainerScreen>() {
            @Override
            public TauContainerScreen create(TauContainerMenu pMenu, Inventory pInventory, Component pTitle) {
                return holder.getMenu().createScreen(pMenu, pInventory);
            }
        });
    }
    
    public static List<MenuSlot<?>> buildContainerOnClient(UIMenu uiMenu, Layout layout, Theme theme, TauContainerMenu menu) {
        UIComponent uiComponent = uiMenu.build(layout, theme, menu);
        List<MenuSlot<?>> slots = new ArrayList<>();
        UIBuilder.build(layout, theme, uiComponent, new BuildContext(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), slots));
        return slots;
    }
}
