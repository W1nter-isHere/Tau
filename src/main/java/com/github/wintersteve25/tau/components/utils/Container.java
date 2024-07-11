package com.github.wintersteve25.tau.components.utils;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.FlexSizeBehaviour;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Axis;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public final class Container implements PrimitiveUIComponent {

    private final UIComponent child;
    private final FlexSizeBehaviour sizeBehaviour;
    private final boolean drawBackground;

    public Container(UIComponent child, boolean drawBackground, FlexSizeBehaviour sizeBehaviour) {
        this.child = child;
        this.drawBackground = drawBackground;
        this.sizeBehaviour = sizeBehaviour;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        if (child == null && !drawBackground) {
            return layout.getSize();
        }

        SimpleVec2i size = layout.getSize();
        BuildContext innerContext = new BuildContext();

        if (child != null) {
            if (sizeBehaviour == FlexSizeBehaviour.MIN) {
                size = UIBuilder.build(layout, theme, child, innerContext);
            } else {
                UIBuilder.build(layout, theme, child, innerContext);
            }
        }

        if (drawBackground) {
            int width = size.x;
            int height = size.y;
            int x = layout.getPosition(Axis.HORIZONTAL, width);
            int y = layout.getPosition(Axis.VERTICAL, height);
            context.renderables().add((graphics, pMouseX, pMouseY, pPartialTicks) -> theme.drawContainer(graphics, x, y, width, height, pPartialTicks, pMouseX, pMouseY));
        }

        context.addAll(innerContext);
        return size;
    }


    public static final class Builder implements UIComponent {
        private UIComponent child;
        private boolean drawBackground = true;
        private FlexSizeBehaviour sizeBehaviour;

        public Builder() {
        }

        public Builder withChild(UIComponent child) {
            this.child = child;
            return this;
        }

        public Builder noBackground() {
            this.drawBackground = false;
            return this;
        }

        public Builder withSizeBehaviour(FlexSizeBehaviour sizeBehaviour) {
            this.sizeBehaviour = sizeBehaviour;
            return this;
        }

        public Container build() {
            return new Container(child, drawBackground, sizeBehaviour == null ? FlexSizeBehaviour.MAX : sizeBehaviour);
        }

        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return build();
        }
    }
}
