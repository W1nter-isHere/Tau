package com.github.wintersteve25.tau.renderer;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.MinecraftTheme;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;

import java.util.ArrayList;
import java.util.List;

public class ScreenUIRenderer extends Screen {

    private final UIComponent uiComponent;
    private final List<Renderable> components;
    private final List<Renderable> tooltips;
    private final List<DynamicUIComponent> dynamicUIComponents;
    private final boolean renderBackground;
    private final Theme theme;
    private boolean built;

    public ScreenUIRenderer(UIComponent uiComponent, boolean renderBackground, Theme theme) {
        super(Component.empty());
        this.uiComponent = uiComponent;
        this.renderBackground = renderBackground;
        this.theme = theme;
        this.components = new ArrayList<>();
        this.tooltips = new ArrayList<>();
        this.dynamicUIComponents = new ArrayList<>();
    }

    public ScreenUIRenderer(UIComponent uiComponent, boolean renderBackground) {
        this(uiComponent, renderBackground, MinecraftTheme.INSTANCE);
    }

    public ScreenUIRenderer(UIComponent uiComponent) {
        this(uiComponent, true);
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
    public void tick() {
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
}
