package com.github.wintersteve25.tau.components.interactable;

import com.github.wintersteve25.tau.components.utils.WidgetWrapper;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class TextField implements UIComponent {

    private final Component message;
    private final Component hintText;
    private final Consumer<String> onChange;
    private final Predicate<String> validator;
    private final BiFunction<String, Integer, FormattedCharSequence> formatter;

    public TextField(Component message, Consumer<String> onChange, Predicate<String> validator, Component hintText,
                     BiFunction<String, Integer, FormattedCharSequence> formatter) {
        this.message = message;
        this.onChange = onChange;
        this.validator = validator;
        this.hintText = hintText;
        this.formatter = formatter;
    }

    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new WidgetWrapper(new TextFieldWidget(message, hintText, onChange, validator, formatter));
    }

    private static final class TextFieldWidget extends net.minecraft.client.gui.components.EditBox {
        public TextFieldWidget(Component message, Component hintText, Consumer<String> onChange, Predicate<String> validator, BiFunction<String, Integer, FormattedCharSequence> formatter) {
            super(Minecraft.getInstance().font, 0, 0, 0, 0, message);
            if (validator != null) setFilter(validator);
            if (hintText != null) setSuggestion(hintText.getString());
            if (formatter != null) setFormatter(formatter);

            setResponder(text -> {
                if (onChange != null) {
                    onChange.accept(text);
                }

                if (!text.isEmpty()) {
                    setSuggestion("");
                } else {
                    if (hintText != null) setSuggestion(hintText.getString());
                }
            });
        }
    }

    public static final class Builder implements UIComponent {
        private Component message;
        private Component hintText;
        private Consumer<String> onChange;
        private Predicate<String> validator;
        private BiFunction<String, Integer, FormattedCharSequence> formatter;

        public Builder() {
        }

        public Builder withMessage(Component message) {
            this.message = message;
            return this;
        }

        public Builder withHintText(Component hintText) {
            this.hintText = hintText;
            return this;
        }

        public Builder withOnChange(Consumer<String> onChange) {
            this.onChange = onChange;
            return this;
        }

        public Builder withValidator(Predicate<String> validator) {
            this.validator = validator;
            return this;
        }

        public Builder withFormatter(BiFunction<String, Integer, FormattedCharSequence> formatter) {
            this.formatter = formatter;
            return this;
        }

        public TextField build() {
            return new TextField(message == null ? Component.empty() : message, onChange, validator, hintText, formatter);
        }

        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return build();
        }
    }
}
