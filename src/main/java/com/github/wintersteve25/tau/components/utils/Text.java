package com.github.wintersteve25.tau.components.utils;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.components.render.Render;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.RenderProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.utils.Color;
import com.github.wintersteve25.tau.layout.Axis;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.network.chat.FormattedText;

import java.util.List;

public final class Text implements PrimitiveUIComponent, RenderProvider {

    private static int ellipsisWidth = 0;
    private static final String ellipsisText = "...";

    private final FormattedText text;
    private final OverflowBehaviour overflowBehaviour;
    private Color color;

    public Text(FormattedText text, Color color, OverflowBehaviour overflowBehaviour) {
        this.text = text;
        this.color = color;
        this.overflowBehaviour = overflowBehaviour;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        color = color == null ? theme.getTextColor() : color;

        Font fontRenderer = Minecraft.getInstance().font;
        int width = fontRenderer.width(text);
        ellipsisWidth = fontRenderer.width(ellipsisText);

        boolean willOverflow = width > layout.getWidth();
        if (willOverflow) {
            width = layout.getWidth();
        }

        int height = willOverflow && overflowBehaviour == OverflowBehaviour.WRAP ?
                fontRenderer.wordWrapHeight(text.getString(), width) :
                8; // constant for line height in minecraft

        int x = layout.getPosition(Axis.HORIZONTAL, width);
        int y = layout.getPosition(Axis.VERTICAL, height);

        int finalWidth = width;
        if (overflowBehaviour != OverflowBehaviour.CLIP) {
            context.renderables().add((graphics, pMouseX, pMouseY, pPartialTicks) -> render(graphics, pMouseX, pMouseY, pPartialTicks, x, y, finalWidth, height));
        } else {
            UIBuilder.build(
                    new Layout(width, height, x, y),
                    theme,
                    new Clip.Builder().build(new Render(this)),
                    context
            );
        }

        return new SimpleVec2i(width, height);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, int x, int y, int width, int height) {
        Font font = Minecraft.getInstance().font;

        switch (overflowBehaviour) {
            case WRAP -> graphics.drawWordWrap(font, text, x, y, width, color.getAARRGGBB());
            case ELLIPSIS -> graphics.drawString(font, font.substrByWidth(text, width - ellipsisWidth).getString() + ellipsisText, x, y, color.getAARRGGBB(), true);
            default -> graphics.drawString(font, text.getString(), x, y, color.getAARRGGBB(), true);
        }
    }

    public static final class Builder implements UIComponent {
        private final Component text;
        private Color color;
        private OverflowBehaviour overflowBehaviour;

        public Builder(Component text) {
            this.text = text;
            this.color = Color.WHITE;
        }

        public Builder(String text) {
            this.text = Component.literal(text);
        }

        public Builder withColor(Color color) {
            this.color = color;
            return this;
        }

        public Builder withOverflowBehaviour(OverflowBehaviour overflowBehaviour) {
            this.overflowBehaviour = overflowBehaviour;
            return this;
        }

        public Text build() {
            return new Text(
                    text,
                    color,
                    overflowBehaviour == null ? OverflowBehaviour.OVERFLOW : overflowBehaviour
            );
        }

        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return build();
        }
    }

    public enum OverflowBehaviour {
        OVERFLOW,
        WRAP,
        CLIP,
        ELLIPSIS
    }
}
