package com.github.wintersteve25.tau.components.animated;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import com.github.wintersteve25.tau.utils.Size;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class AnimatedTexture implements PrimitiveUIComponent {

    private final ResourceLocation textureLocation;
    private final SimpleVec2i textureSize;
    private final SimpleVec2i uvSize;
    private final SimpleVec2i size;
    private final Supplier<Size> uvSizeAnimator;
    private final Supplier<SimpleVec2i> uvPosAnimator;

    public AnimatedTexture(ResourceLocation textureLocation, SimpleVec2i textureSize, SimpleVec2i uvSize, SimpleVec2i size, Supplier<Size> uvSizeAnimator, Supplier<SimpleVec2i> uvPosAnimator) {
        this.textureLocation = textureLocation;
        this.textureSize = textureSize;
        this.uvSize = uvSize;
        this.size = size;
        this.uvSizeAnimator = uvSizeAnimator;
        this.uvPosAnimator = uvPosAnimator;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {

        SimpleVec2i position = layout.getPosition(uvSize);

        context.renderables().add((graphics, pMouseX, pMouseY, pPartialTicks) -> {
            SimpleVec2i newUvSize = new SimpleVec2i(uvSize.x, uvSize.y);
            newUvSize = uvSizeAnimator.get().get(newUvSize);
            SimpleVec2i newUvPos = uvPosAnimator.get();

            graphics.blit(textureLocation, position.x, position.y, size.x, size.y, newUvPos.x, newUvPos.y, newUvSize.x, newUvSize.y,
                    textureSize.x,
                    textureSize.y);
        });

        return size;
    }

    public static final class Builder implements UIComponent {

        private final ResourceLocation textureLocation;
        private final Supplier<Size> uvSizeAnimator;
        private final Supplier<SimpleVec2i> uvPosAnimator;
        private SimpleVec2i textureSize;
        private SimpleVec2i uvSize;
        private SimpleVec2i size;

        public Builder(ResourceLocation textureLocation, Supplier<Size> uvSizeAnimator, Supplier<SimpleVec2i> uvPosAnimator) {
            this.textureLocation = textureLocation;
            this.uvSizeAnimator = uvSizeAnimator;
            this.uvPosAnimator = uvPosAnimator;
        }

        public Builder withTextureSize(SimpleVec2i textureSize) {
            this.textureSize = textureSize;
            return this;
        }

        public Builder withUvSize(SimpleVec2i uvSize) {
            this.uvSize = uvSize;
            return this;
        }

        public Builder withSize(SimpleVec2i size) {
            this.size = size;
            return this;
        }

        public UIComponent build() {
            textureSize = textureSize == null ? new SimpleVec2i(256, 256) : textureSize;
            uvSize = uvSize == null ? textureSize : uvSize;
            return new AnimatedTexture(textureLocation, textureSize, uvSize, size == null ? uvSize : size, uvSizeAnimator, uvPosAnimator);
        }

        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return build();
        }
    }
}
