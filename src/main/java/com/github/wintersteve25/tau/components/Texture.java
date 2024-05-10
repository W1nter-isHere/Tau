package com.github.wintersteve25.tau.components;

import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.resources.ResourceLocation;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

import java.util.List;

public final class Texture implements PrimitiveUIComponent {

    private final ResourceLocation textureLocation;
    private final SimpleVec2i textureSize;
    private final SimpleVec2i uv;
    private final SimpleVec2i uvSize;
    private final SimpleVec2i size;

    public Texture(ResourceLocation textureLocation, SimpleVec2i textureSize, SimpleVec2i uv, SimpleVec2i uvSize, SimpleVec2i size) {
        this.textureLocation = textureLocation;
        this.textureSize = textureSize;
        this.uv = uv;
        this.uvSize = uvSize;
        this.size = size;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, List<Renderable> renderables, List<Renderable> tooltips, List<DynamicUIComponent> dynamicUIComponents, List<GuiEventListener> eventListeners) {

        Minecraft minecraft = Minecraft.getInstance();
        SimpleVec2i position = layout.getPosition(uvSize);

        renderables.add((graphics, pMouseX, pMouseY, pPartialTicks) -> {
            graphics.blit(textureLocation, position.x, position.y, size.x, size.y, uv.x, uv.y, uvSize.x, uvSize.y,
                    textureSize.x,
                    textureSize.y);
        });

        return uvSize;
    }

    public static final class Builder implements UIComponent {

        private final ResourceLocation textureLocation;
        private SimpleVec2i textureSize;
        private SimpleVec2i uv;
        private SimpleVec2i uvSize;
        private SimpleVec2i size;

        public Builder(ResourceLocation textureLocation) {
            this.textureLocation = textureLocation;
        }

        public Builder withTextureSize(SimpleVec2i textureSize) {
            this.textureSize = textureSize;
            return this;
        }

        public Builder withUv(SimpleVec2i uv) {
            this.uv = uv;
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

        public Texture build() {
            textureSize = textureSize == null ? new SimpleVec2i(256, 256) : textureSize;
            uvSize = uvSize == null ? textureSize : uvSize;
            return new Texture(textureLocation, textureSize, uv == null ? SimpleVec2i.zero() : uv, uvSize, size == null ? uvSize : size);
        }

        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return build();
        }
    }
}
