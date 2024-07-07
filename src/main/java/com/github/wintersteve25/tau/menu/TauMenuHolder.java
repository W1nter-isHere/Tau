package com.github.wintersteve25.tau.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;

public class TauMenuHolder {

    private final UIMenu menu;
    private DeferredHolder<MenuType<?>, MenuType<TauContainerMenu>> inner;

    public TauMenuHolder(DeferredRegister<MenuType<?>> register, UIMenu menu, String name, FeatureFlagSet featureFlagSet) {
        this.menu = menu;
        setup(menu, register, name, featureFlagSet);
    }

    private void setup(UIMenu menu, DeferredRegister<MenuType<?>> register, String name, FeatureFlagSet set) {
        inner = menu.registerMenuType(register, this, name, set);
    }

    public MenuType<TauContainerMenu> get() {
        return inner.get();
    }

    public UIMenu getMenu() {
        return menu;
    }

    public void registerScreen(RegisterMenuScreensEvent event) {
        menu.registerScreen(event, inner.get());
    }

    public void openMenu(ServerPlayer player, BlockPos pos) {
        player.openMenu(new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.empty();
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
                return menu.newMenu(TauMenuHolder.this, pPlayerInventory, pContainerId, pos);
            }
        }, buf -> {
            buf.writeBlockPos(pos);
        });
    }
}
