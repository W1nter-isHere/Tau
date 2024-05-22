package com.github.wintersteve25.tau.renderer;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.MinecraftTheme;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MenuUIRenderer extends AbstractContainerScreen<MenuUIRenderer.Menu> {

    private final UIComponent uiComponent;
    private final List<Renderable> components;
    private final List<Renderable> tooltips;
    private final List<DynamicUIComponent> dynamicUIComponents;
    private final boolean renderBackground;
    private final Theme theme;
    private boolean built;

    public MenuUIRenderer(Menu pMenu, Inventory pPlayerInventory, UIComponent uiComponent, boolean renderBackground, Theme theme) {
        super(pMenu, pPlayerInventory, Component.empty());
        this.uiComponent = uiComponent;
        this.renderBackground = renderBackground;
        this.theme = theme;
        this.components = new ArrayList<>();
        this.tooltips = new ArrayList<>();
        this.dynamicUIComponents = new ArrayList<>();
    }

    public MenuUIRenderer(Menu pMenu, Inventory pPlayerInventory, UIComponent uiComponent, boolean renderBackground) {
        this(pMenu, pPlayerInventory, uiComponent, renderBackground, MinecraftTheme.INSTANCE);
    }

    public MenuUIRenderer(Menu pMenu, Inventory pPlayerInventory, UIComponent uiComponent) {
        this(pMenu, pPlayerInventory, uiComponent,true);
    }

    @Override
    protected void init() {
        Layout layout = new Layout(width, height);

        components.clear();
        tooltips.clear();
        dynamicUIComponents.clear();
        UIBuilder.build(layout, theme, uiComponent, new BuildContext(components, tooltips, dynamicUIComponents, (List<GuiEventListener>) children()));

        built = true;
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTicks) {
        if (renderBackground) {
            this.renderBackground(graphics, pMouseX, pMouseY, pPartialTicks);
        }

        for (Renderable component : components) {
            component.render(graphics, pMouseX, pMouseY, pPartialTicks);
        }

        for (Renderable tooltip : tooltips) {
            tooltip.render(graphics, pMouseX, pMouseY, pPartialTicks);
        }
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    }

    @Override
    public void containerTick() {
        if (!built) return;
        UIBuilder.rebuildAndTickDynamicUIComponents(dynamicUIComponents);
    }

    @Override
    public void onClose() {
        for (DynamicUIComponent dynamicUIComponent : dynamicUIComponents) {
            dynamicUIComponent.destroy();
        }

        super.onClose();
    }

    public static class Menu extends AbstractContainerMenu {
        protected Menu(@Nullable MenuType<?> pMenuType, int pContainerId) {
            super(pMenuType, pContainerId);
        }

        @Override
        public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
            return null;
        }

        @Override
        public boolean stillValid(Player pPlayer) {
            return false;
        }
    }
}
