package com.github.wintersteve25.tau.menu;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.mojang.blaze3d.vertex.PoseStack;
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

    public TauContainerScreen(TauContainerMenu pMenu, Inventory pPlayerInventory, UIMenu uiMenu, boolean renderBackground, Theme theme) {
        super(pMenu, pPlayerInventory, Component.empty());
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

        components.clear();
        tooltips.clear();
        dynamicUIComponents.clear();

        UIBuilder.build(layout, theme, uiMenu.build(layout, theme, getMenu()), new BuildContext(components, tooltips, dynamicUIComponents, (List<GuiEventListener>) children(), new ArrayList<>()));

        built = true;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        if (renderBackground) {
            renderTransparentBackground(graphics);
        }
        
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(leftPos, topPos, 0);

        for (Renderable component : components) {
            component.render(graphics, pMouseX, pMouseY, pPartialTick);
        }

        for (Renderable tooltip : tooltips) {
            tooltip.render(graphics, pMouseX, pMouseY, pPartialTick);
        }

        poseStack.popPose();
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
