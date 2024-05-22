package com.github.wintersteve25.tau.components.render;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.Theme;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import com.github.wintersteve25.tau.utils.Transformation;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class Transform implements PrimitiveUIComponent, ContainerEventHandler {

    private final UIComponent child;
    private final Iterable<Transformation> transformations;
    private final List<GuiEventListener> childrenEventListeners;

    private boolean dragging;
    private GuiEventListener focused;

    public Transform(UIComponent child, Transformation... transformations) {
        this(child, Arrays.asList(transformations));
    }

    public Transform(UIComponent child, Iterable<Transformation> transformations) {
        this.child = child;
        this.transformations = transformations;
        this.childrenEventListeners = new ArrayList<>();
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {

        List<Renderable> children = new ArrayList<>();
        List<SimpleVec2i> slots = new ArrayList<>();
        
        childrenEventListeners.clear();
        BuildContext innerContext = new BuildContext(children, context.tooltips(), context.dynamicUIComponents(), childrenEventListeners, slots);

        SimpleVec2i size = UIBuilder.build(layout, theme, child, innerContext);

        context.renderables().add((graphics, pMouseX, pMouseY, pPartialTicks) -> {
            SimpleVec2i mousePos = new SimpleVec2i(pMouseX, pMouseY);
            PoseStack poseStack = graphics.pose();
            poseStack.pushPose();

            for (Transformation transformation : transformations) {
                transformation.transform(poseStack);
                transformation.transformPoint(mousePos);
            }

            for (Renderable renderable : children) {
                renderable.render(graphics, mousePos.x, mousePos.y, pPartialTicks);
            }

            poseStack.popPose();
        });
        
        for (SimpleVec2i slot : slots) {
            for (Transformation transformation : transformations) {
                transformation.transformPoint(slot);
            }
            
            context.slots().add(slot);
        }

        return size;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return childrenEventListeners;
    }

    @Override
    public boolean isDragging() {
        return dragging;
    }

    @Override
    public void setDragging(boolean pIsDragging) {
        dragging = pIsDragging;
    }

    @Nullable
    @Override
    public GuiEventListener getFocused() {
        return focused;
    }

    @Override
    public void setFocused(@Nullable GuiEventListener pFocused) {
        focused = pFocused;
    }

    @Override
    public Optional<GuiEventListener> getChildAt(double pMouseX, double pMouseY) {
        Vector2d mousePos = new Vector2d(pMouseX, pMouseY);

        for (Transformation transformation : transformations) {
            transformation.transformPoint(mousePos);
        }

        return ContainerEventHandler.super.getChildAt(mousePos.x, mousePos.y);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        Vector2d mousePos = new Vector2d(pMouseX, pMouseY);

        for (Transformation transformation : transformations) {
            transformation.transformPoint(mousePos);
        }

        return ContainerEventHandler.super.mouseClicked(mousePos.x, mousePos.y, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        Vector2d mousePos = new Vector2d(pMouseX, pMouseY);

        for (Transformation transformation : transformations) {
            transformation.transformPoint(mousePos);
        }

        return ContainerEventHandler.super.mouseReleased(mousePos.x, mousePos.y, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        Vector2d mousePos = new Vector2d(pMouseX, pMouseY);

        for (Transformation transformation : transformations) {
            transformation.transformPoint(mousePos);
        }

        return ContainerEventHandler.super.mouseDragged(mousePos.x, mousePos.y, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
        Vector2d mousePos = new Vector2d(pMouseX, pMouseY);

        for (Transformation transformation : transformations) {
            transformation.transformPoint(mousePos);
        }

        return ContainerEventHandler.super.mouseScrolled(mousePos.x, mousePos.y, pScrollX, pScrollY);
    }
}
