package com.github.wintersteve25.tau.menu;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.layout.Axis;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class TauContainerScreen extends AbstractContainerScreen<TauContainerMenu> implements MenuAccess<TauContainerMenu> {

    private final UIMenu uiMenu;
    private final List<Renderable> components;
    private final List<Renderable> tooltips;
    private final List<DynamicUIComponent> dynamicUIComponents;

    private final boolean renderBackground;
    private final Theme theme;

    private boolean built;

    public TauContainerScreen(TauContainerMenu pMenu, Inventory pPlayerInventory, UIMenu uiMenu, boolean renderBackground, Theme theme, Component title) {
        super(pMenu, pPlayerInventory, title);
        this.uiMenu = uiMenu;
        this.renderBackground = renderBackground;
        this.theme = theme;
        this.components = new ArrayList<>();
        this.tooltips = new ArrayList<>();
        this.dynamicUIComponents = new ArrayList<>();
    }

    @Override
    protected void init() {
        Layout layout = new Layout(uiMenu.getSize().x, uiMenu.getSize().y);
        leftPos = uiMenu.getLeftPos(layout, width, height);
        topPos = uiMenu.getTopPos(layout, width, height);
        
        layout.pushOffset(Axis.HORIZONTAL, leftPos);
        layout.pushOffset(Axis.VERTICAL, topPos);

        components.clear();
        tooltips.clear();
        dynamicUIComponents.clear();

        UIBuilder.build(layout, theme, uiMenu.build(layout, theme, getMenu()), new BuildContext(components, tooltips, dynamicUIComponents, (List<GuiEventListener>) children(), new ArrayList<>()));
        
        layout.popOffset(Axis.HORIZONTAL);
        layout.popOffset(Axis.VERTICAL);

        built = true;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        if (renderBackground) {
            renderTransparentBackground(graphics);
        }
        
        for (Renderable component : components) {
            component.render(graphics, pMouseX, pMouseY, pPartialTick);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
        
        for (Renderable tooltip : tooltips) {
            tooltip.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public void containerTick() {
        if (!built) return;
        UIBuilder.rebuildAndTickDynamicUIComponents(dynamicUIComponents);
        uiMenu.tick(menu);
    }

    @Override
    public void onClose() {
        for (DynamicUIComponent dynamicUIComponent : dynamicUIComponents) {
            dynamicUIComponent.destroy();
        }

        super.onClose();
    }
}
