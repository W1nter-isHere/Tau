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

public class TauContainerUI extends AbstractContainerScreen<TauContainerMenu> implements MenuAccess<TauContainerMenu> {

    private final UIMenu menu;
    private final List<Renderable> components;
    private final List<Renderable> tooltips;
    private final List<DynamicUIComponent> dynamicUIComponents;

    private final boolean renderBackground;
    private final Theme theme;

    private boolean built;
    private int left;
    private int top;

    public TauContainerUI(TauContainerMenu pMenu, Inventory pPlayerInventory, UIMenu menu, boolean renderBackground, Theme theme) {
        super(pMenu, pPlayerInventory, Component.empty());
        this.menu = menu;
        this.renderBackground = renderBackground;
        this.theme = theme;
        this.components = new ArrayList<>();
        this.tooltips = new ArrayList<>();
        this.dynamicUIComponents = new ArrayList<>();
    }

    @Override
    protected void init() {
        Layout layout = new Layout(menu.getSize().x, menu.getSize().y);
        left = (width - layout.getWidth()) / 2;
        top = (height - layout.getHeight()) / 2;

        components.clear();
        tooltips.clear();
        dynamicUIComponents.clear();

        UIBuilder.build(layout, theme, menu.build(layout, theme, getMenu()), new BuildContext(components, tooltips, dynamicUIComponents, (List<GuiEventListener>) children(), new ArrayList<>()));

        built = true;
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTicks) {
        if (renderBackground) {
            this.renderBackground(graphics, pMouseX, pMouseY, pPartialTicks);
        }

        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(left, top, 0);

        for (Renderable component : components) {
            component.render(graphics, pMouseX, pMouseY, pPartialTicks);
        }

        for (Renderable tooltip : tooltips) {
            tooltip.render(graphics, pMouseX, pMouseY, pPartialTicks);
        }

        poseStack.popPose();
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
}
