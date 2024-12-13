package com.github.wintersteve25.tau.menu;

import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.menu.handlers.ISlotHandler;
import com.github.wintersteve25.tau.theme.MinecraftTheme;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public abstract class UIMenu {
    
    private boolean needsRebuild;
    
    public abstract UIComponent build(Layout layout, Theme theme, TauContainerMenu containerMenu);

    public abstract SimpleVec2i getSize();
    
    public abstract Component getTitle();

    public void onClose() {
    }
    
    protected void rebuild() {
        needsRebuild = true;
    }
    
    public boolean isNeedsRebuild() {
        return needsRebuild;
    }
    
    public int getTopPos(Layout layout, int width, int height) {
        return (height - layout.getHeight()) / 2;
    }

    public int getLeftPos(Layout layout, int width, int height) {
        return (width - layout.getWidth()) / 2;
    }

    public void tick(TauContainerMenu menu) {
    }

    protected void addDataSlots(TauContainerMenu menu) {
    }

    public List<? extends ISlotHandler> getSlots(TauContainerMenu menu) {
        return List.of();
    }

    public ItemStack quickMoveStack(TauContainerMenu menu, Player player, int index) {
        return null;
    }

    public boolean stillValid(TauContainerMenu menu, Player player) {
        return true;
    }

    public boolean shouldRenderBackground() {
        return true;
    }

    public Theme getTheme() {
        return MinecraftTheme.INSTANCE;
    }
    
    public TauContainerScreen createScreen(TauContainerMenu menu, Inventory inv, Component title) {
        return new TauContainerScreen(menu, inv, UIMenu.this, UIMenu.this.shouldRenderBackground(), UIMenu.this.getTheme(), title);
    }
    
    protected TauContainerMenu createMenu(TauMenuHolder menuHolder, Inventory playerInv, int containerId, BlockPos pos) {
        return new TauContainerMenu(menuHolder, playerInv, containerId, pos);
    }

    public DeferredHolder<MenuType<?>, MenuType<TauContainerMenu>> registerMenuType(DeferredRegister<MenuType<?>> register, TauMenuHolder menu, String name, FeatureFlagSet featureFlagSet) {
        return register.register(name, () -> IMenuTypeExtension.create((cid, inv, data) -> newMenu(menu, inv, cid, data.readBlockPos())));
    }

    public TauContainerMenu newMenu(TauMenuHolder menuHolder, Inventory playerInv, int containerId, BlockPos pos) {
        SimpleVec2i size = getSize();
        Layout layout = new Layout(size.x, size.y);
        List<? extends MenuSlot<? extends ISlotHandler>> s;

        TauContainerMenu menu = createMenu(menuHolder, playerInv, containerId, pos);
        if (menu.level.isClientSide()) {
            s = TauMenuHelper.buildContainerOnClient(this, layout, getTheme(), menu);
        } else {
            s = getSlots(menu).stream().map(sl -> new MenuSlot<>(SimpleVec2i.zero(), sl)).toList();
        }

        for (MenuSlot<?> slot : s) {
            slot.handler().setupSync(menu, playerInv, slot.pos().x, slot.pos().y);
        }

        addDataSlots(menu);
        return menu;
    }
}
