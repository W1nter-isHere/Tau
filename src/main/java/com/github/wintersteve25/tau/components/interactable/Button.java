package com.github.wintersteve25.tau.components.interactable;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Axis;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.ClientSoundHelper;
import com.github.wintersteve25.tau.utils.InteractableState;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.function.Consumer;

public final class Button implements PrimitiveUIComponent, GuiEventListener {

    private final Consumer<Integer> onPress;
    private final UIComponent child;

    private int width;
    private int height;
    private int x;
    private int y;

    private boolean focus;

    public Button(Consumer<Integer> onPress, UIComponent child) {
        this.onPress = onPress;
        this.child = child;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext buildContext) {
        width = layout.getWidth();
        height = layout.getHeight();
        x = layout.getPosition(Axis.HORIZONTAL, width);
        y = layout.getPosition(Axis.VERTICAL, height);

        buildContext.renderables().add((graphics, pMouseX, pMouseY, pPartialTicks) -> theme.drawButton(graphics, x, y, width, height, pPartialTicks, pMouseX, pMouseY, this.getInteractableState(pMouseX, pMouseY)));
        UIBuilder.build(layout, theme, child, buildContext);

        return layout.getSize();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (onPress != null && isHovered((int) pMouseX, (int) pMouseY)) {
            onPress.accept(pButton);
            ClientSoundHelper.playButtonClick();
            return true;
        }

        return false;
    }

    @Override
    public void setFocused(boolean pFocused) {
        focus = pFocused;
    }

    @Override
    public boolean isFocused() {
        return focus;
    }

    private boolean isHovered(int pMouseX, int pMouseY) {
        return pMouseX > x && pMouseX < x + width && pMouseY > y && pMouseY < y + height;
    }

    private InteractableState getInteractableState(int pMouseX, int pMouseY) {
        if (onPress == null) {
            return InteractableState.DISABLED;
        } else if (isHovered(pMouseX, pMouseY)) {
            return InteractableState.HOVERED;
        }

        return InteractableState.IDLE;
    }

    public static final class Builder {
        private Consumer<Integer> onPress;

        public Builder() {
        }

        public Builder withOnPress(Consumer<Integer> onPress) {
            this.onPress = onPress;
            return this;
        }

        public Button build(UIComponent child) {
            return new Button(onPress, child);
        }
    }
}
