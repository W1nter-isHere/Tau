package com.github.wintersteve25.tau.components.interactable;

import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.components.utils.WidgetWrapper;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;

import java.util.function.Consumer;

public final class Slider implements UIComponent {

    private final Component prefix;
    private final Component suffix;
    private final float stepSize;
    private final int decimalPlaces;
    private final double value;
    private final double minimum;
    private final double maximum;
    private final Runnable onPress;
    private final Consumer<Double> onValueChanged;
    private final boolean drawString;

    public Slider(Component prefix, Component suffix, float stepSize, int decimalPlaces, double value, double minimum,
                  double maximum, Runnable onPress, Consumer<Double> onValueChanged, boolean drawString) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.stepSize = stepSize;
        this.decimalPlaces = decimalPlaces;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.onPress = onPress;
        this.onValueChanged = onValueChanged;
        this.drawString = drawString;
    }

    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new WidgetWrapper(new SliderWidget(prefix, suffix, stepSize, decimalPlaces, minimum, maximum, value, onPress, onValueChanged, theme, drawString));
    }

    private static final class SliderWidget extends ExtendedSlider {
        private final Runnable onPress;
        private final Consumer<Double> onValueChange;
        private final Theme theme;

        public SliderWidget(Component prefix, Component suffix, float stepSize, int decimalAmounts, double minVal, double maxVal, double currentVal, Runnable onPress, Consumer<Double> onValueChange, Theme theme, boolean drawString) {
            super(0, 0, 0, 0, prefix, suffix, minVal, maxVal, currentVal, stepSize, decimalAmounts, drawString);
            this.onPress = onPress;
            this.onValueChange = onValueChange;
            this.theme = theme;
        }

        @Override
        public void onClick(double mouseX, double mouseY) {
            super.onClick(mouseX, mouseY);
            if (onPress != null) onPress.run();
        }

        @Override
        protected void applyValue() {
            if (onValueChange != null) onValueChange.accept(getValue());
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            Minecraft minecraft = Minecraft.getInstance();
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            theme.drawSlider(
                    this.getSprite(),
                    this.getHandleSprite(),
                    value,
                    guiGraphics,
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight(),
                    partialTick,
                    mouseX, 
                    mouseY,
                    isHoveredOrFocused()
            );
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            int i = this.active ? 16777215 : 10526880;
            this.renderScrollingString(guiGraphics, minecraft.font, 2, i | Mth.ceil(this.alpha * 255.0F) << 24);
        }
    }

    public static final class Builder implements UIComponent {
        private Component prefix;
        private Component suffix;
        private float stepSize = 0.1f;
        private int decimalPlaces = 2;
        private double value;
        private double minimum = 0;
        private double maximum = 1;
        private Runnable onPress;
        private Consumer<Double> onValueChanged;
        private boolean drawString = true;

        public Builder() {
        }

        public Builder withPrefix(Component prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder withSuffix(Component suffix) {
            this.suffix = suffix;
            return this;
        }

        public Builder withDecimalPlaces(int decimalPlaces) {
            this.decimalPlaces = decimalPlaces;
            return this;
        }

        public Builder withStepSize(float stepSize) {
            this.stepSize = stepSize;
            return this;
        }

        public Builder withValue(double value) {
            this.value = value;
            return this;
        }

        public Builder withMinimum(double minimum) {
            this.minimum = minimum;
            return this;
        }

        public Builder withMaximum(double maximum) {
            this.maximum = maximum;
            return this;
        }

        public Builder withOnPress(Runnable onPress) {
            this.onPress = onPress;
            return this;
        }
        
        public Builder dontDrawString() {
            drawString = false;
            return this;
        }

        public Builder withOnValueChanged(Consumer<Double> onValueChanged) {
            this.onValueChanged = onValueChanged;
            return this;
        }

        public Slider build() {
            return new Slider(
                    prefix == null ? Component.empty() : prefix,
                    suffix == null ? Component.empty() : suffix,
                    stepSize,
                    decimalPlaces,
                    value,
                    minimum,
                    maximum,
                    onPress,
                    onValueChanged,
                    drawString
            );
        }

        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return build();
        }
    }
}
