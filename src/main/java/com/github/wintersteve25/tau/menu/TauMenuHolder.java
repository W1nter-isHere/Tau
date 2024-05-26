package com.github.wintersteve25.tau.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TauMenuHolder {

    private final TauMenu menu;
    private DeferredHolder<MenuType<?>, MenuType<TauContainerMenu>> inner;

    public TauMenuHolder(DeferredRegister<MenuType<?>> register, TauMenu menu, String name, FeatureFlagSet featureFlagSet) {
        this.menu = menu;
        setup(menu, register, name, featureFlagSet);
    }

    private void setup(TauMenu menu, DeferredRegister<MenuType<?>> register, String name, FeatureFlagSet set) {
        inner = menu.registerMenuType(register, this, name, set);
    }

    public MenuType<TauContainerMenu> get() {
        return inner.get();
    }

    public TauContainerMenu newInstance(Inventory playerInv, int containerId) {
        return menu.newMenu(this, playerInv, containerId);
    }
}
