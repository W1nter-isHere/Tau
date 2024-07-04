package com.github.wintersteve25.tau.menu;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class TauMenuHolder {

    private final UIMenu menu;
    private RegistryObject<MenuType<TauContainerMenu>> inner;

    public TauMenuHolder(DeferredRegister<MenuType<?>> register, UIMenu menu, String name) {
        this.menu = menu;
        setup(menu, register, name);
    }

    private void setup(UIMenu menu, DeferredRegister<MenuType<?>> register, String name) {
        inner = menu.registerMenuType(register, this, name);
    }

    public MenuType<TauContainerMenu> get() {
        return inner.get();
    }

    public UIMenu getMenu() {
        return menu;
    }

    public void registerScreen(FMLClientSetupEvent event) {
        menu.registerScreen(event, inner.get());
    }

    public void openMenu(ServerPlayer player, BlockPos pos) {
        NetworkHooks.openScreen(player, new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.empty();
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
                return menu.newMenu(TauMenuHolder.this, pPlayerInventory, pContainerId, pos);
            }
        }, data -> data.writeBlockPos(pos));
    }
}
