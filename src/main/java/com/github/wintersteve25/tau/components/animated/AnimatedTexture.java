package com.github.wintersteve25.tau.components.animated;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import com.github.wintersteve25.tau.utils.Size;
import com.github.wintersteve25.tau.utils.Variable;
import net.minecraft.resources.ResourceLocation;

public class AnimatedTexture implements PrimitiveUIComponent {

    private final ResourceLocation textureLocation;
    private final SimpleVec2i textureSize;
    private final Size size;
    private final Variable<SimpleVec2i> uvSize;
    private final Variable<SimpleVec2i> uvPos;

    public AnimatedTexture(ResourceLocation textureLocation, SimpleVec2i textureSize, Size size, Variable<SimpleVec2i> uvSize, Variable<SimpleVec2i> uvPos) {
        this.textureLocation = textureLocation;
        this.textureSize = textureSize;
        this.size = size;
        this.uvSize = uvSize;
        this.uvPos = uvPos;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        SimpleVec2i actualSize = size.get(layout.getSize());
        SimpleVec2i position = layout.getPosition(actualSize);

        context.renderables().add((graphics, pMouseX, pMouseY, pPartialTicks) -> {
            graphics.blit(textureLocation, position.x, position.y, actualSize.x, actualSize.y, uvPos.getValue().x, uvPos.getValue().y, uvSize.getValue().x, uvSize.getValue().y,
                    textureSize.x,
                    textureSize.y);
        });

        return actualSize;
    }

    public static final class Builder implements UIComponent {

        private final ResourceLocation textureLocation;
        private final Variable<SimpleVec2i> uvSize;
        private final Variable<SimpleVec2i> uvPos;
        private final Size size;
        private SimpleVec2i textureSize;

        public Builder(ResourceLocation textureLocation, Size size, Variable<SimpleVec2i> uvSize, Variable<SimpleVec2i> uvPos) {
            this.textureLocation = textureLocation;
            this.uvSize = uvSize;
            this.uvPos = uvPos;
            this.size = size;
        }

        public Builder withTextureSize(SimpleVec2i textureSize) {
            this.textureSize = textureSize;
            return this;
        }

        public UIComponent build() {
            textureSize = textureSize == null ? new SimpleVec2i(256, 256) : textureSize;
            return new AnimatedTexture(textureLocation, textureSize, size, uvSize, uvPos);
        }

        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return build();
        }
    }
}
