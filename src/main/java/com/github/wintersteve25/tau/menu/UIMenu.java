package com.github.wintersteve25.tau.menu;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.MinecraftTheme;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public interface UIMenu {
    UIComponent build(Layout layout, Theme theme, TauContainerMenu containerMenu);

    SimpleVec2i getSize();

    default int getTopPos(Layout layout, int width, int height) {
        return (height - layout.getHeight()) / 2;
    }

    default int getLeftPos(Layout layout, int width, int height) {
        return (width - layout.getWidth()) / 2;
    }

    default void tick(TauContainerMenu menu) {
    }

    default void addDataSlots(TauContainerMenu menu) {
    }

    default ItemStack quickMoveStack(TauContainerMenu menu, Player player, int index) {
        return null;
    }

    default boolean stillValid(TauContainerMenu menu, Player player) {
        return true;
    }

    default boolean shouldRenderBackground() {
        return true;
    }

    default Theme getTheme() {
        return MinecraftTheme.INSTANCE;
    }

    default DeferredHolder<MenuType<?>, MenuType<TauContainerMenu>> registerMenuType(DeferredRegister<MenuType<?>> register, TauMenuHolder menu, String name, FeatureFlagSet featureFlagSet) {
        return register.register(name, () -> IMenuTypeExtension.create((cid, inv, data) -> newMenu(menu, inv, cid, data.readBlockPos())));
    }

    default void registerScreen(RegisterMenuScreensEvent event, MenuType<TauContainerMenu> menuType) {
        event.register(menuType, new MenuScreens.ScreenConstructor<TauContainerMenu, TauContainerUI>() {
            @Override
            public TauContainerUI create(TauContainerMenu pMenu, Inventory pInventory, Component pTitle) {
                return new TauContainerUI(pMenu, pInventory, UIMenu.this, UIMenu.this.shouldRenderBackground(), UIMenu.this.getTheme());
            }
        });
    }

    default TauContainerMenu newMenu(TauMenuHolder menuHolder, Inventory playerInv, int containerId, BlockPos pos) {
        SimpleVec2i size = getSize();
        Layout layout = new Layout(size.x, size.y);
        List<MenuSlot<?>> s = new ArrayList<>();

        TauContainerMenu menu = new TauContainerMenu(menuHolder, playerInv, containerId, pos);
        UIComponent uiComponent = this.build(layout, getTheme(), menu);
        UIBuilder.build(layout, getTheme(), uiComponent, new BuildContext(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), s));

        for (MenuSlot<?> slot : s) {
            slot.handler().setupSync(menu, playerInv, slot.pos().x, slot.pos().y);
        }

        addDataSlots(menu);
        return menu;
    }
}
