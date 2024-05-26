package com.github.wintersteve25.tau.menu;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.MinecraftTheme;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public interface TauMenu {
    UIComponent build(Layout layout, Theme theme);

    SimpleVec2i getSize();

    default boolean shouldRenderBackground() {
        return true;
    }

    default Theme getTheme() {
        return MinecraftTheme.INSTANCE;
    }

    default DeferredHolder<MenuType<?>, MenuType<TauContainerMenu>> registerMenuType(DeferredRegister<MenuType<?>> register, TauMenuHolder menu, String name, FeatureFlagSet featureFlagSet) {
        return register.register(name, () -> new MenuType<>((cid, player) -> newMenu(menu, player, cid), featureFlagSet));
    }

    default void registerScreen(RegisterMenuScreensEvent event, MenuType<TauContainerMenu> menuType) {
        event.register(menuType, new MenuScreens.ScreenConstructor<TauContainerMenu, TauContainerUI>() {
            @Override
            public TauContainerUI create(TauContainerMenu pMenu, Inventory pInventory, Component pTitle) {
                return new TauContainerUI(pMenu, pInventory, TauMenu.this, TauMenu.this.shouldRenderBackground(), TauMenu.this.getTheme());
            }
        });
    }

    default TauContainerMenu newMenu(TauMenuHolder menuHolder, Inventory playerInv, int containerId) {
        SimpleVec2i size = getSize();
        Layout layout = new Layout(size.x, size.y);
        List<MenuSlot<?>> s = new ArrayList<>();

        UIComponent uiComponent = this.build(layout, getTheme());
        UIBuilder.build(layout, getTheme(), uiComponent, new BuildContext(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), s));

        return new TauContainerMenu(menuHolder.get(), playerInv, containerId) {
            @Override
            public void setupSlots(Inventory playerInventory) {
                for (MenuSlot<?> slot : s) {
                    slot.handler().setupSync(this, playerInventory, slot.pos().x, slot.pos().y);
                }
            }
        };
    }
}
