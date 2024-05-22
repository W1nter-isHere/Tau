package com.github.wintersteve25.tau.components.interactable;

import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.components.utils.WidgetWrapper;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.network.chat.Component;
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

    public Slider(Component prefix, Component suffix, float stepSize, int decimalPlaces, double value, double minimum,
                  double maximum, Runnable onPress, Consumer<Double> onValueChanged) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.stepSize = stepSize;
        this.decimalPlaces = decimalPlaces;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.onPress = onPress;
        this.onValueChanged = onValueChanged;
    }

    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new WidgetWrapper(new SliderWidget(prefix, suffix, stepSize, decimalPlaces, minimum, maximum, value, onPress, onValueChanged));
    }

    private static final class SliderWidget extends ExtendedSlider {
        private final Runnable onPress;
        private final Consumer<Double> onValueChange;

        public SliderWidget(Component prefix, Component suffix, float stepSize, int decimalAmounts, double minVal, double maxVal, double currentVal, Runnable onPress, Consumer<Double> onValueChange) {
            super(0, 0, 0, 0, prefix, suffix, minVal, maxVal, currentVal, stepSize, decimalAmounts, true);
            this.onPress = onPress;
            this.onValueChange = onValueChange;
        }

        @Override
        public void onClick(double mouseX, double mouseY) {
            if (onPress != null) onPress.run();
        }

        @Override
        protected void applyValue() {
            if (onValueChange != null) onValueChange.accept(getValue());
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
                    onValueChanged
            );
        }

        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return build();
        }
    }
}
