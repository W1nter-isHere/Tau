package com.github.wintersteve25.tau.components.layout;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.FlexSizeBehaviour;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.build.UIBuilder;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class Stack implements PrimitiveUIComponent {
    private final Iterable<UIComponent> children;
    private FlexSizeBehaviour sizeBehaviour;

    public Stack(FlexSizeBehaviour sizeBehaviour, Iterable<UIComponent> children) {
        this.sizeBehaviour = sizeBehaviour;
        this.children = children;
    }

    public Stack(FlexSizeBehaviour sizeBehaviour, UIComponent... children) {
        this.sizeBehaviour = sizeBehaviour;
        this.children = Arrays.stream(children).collect(Collectors.toList());
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        if (sizeBehaviour == FlexSizeBehaviour.MAX) {
            for (UIComponent child : children) {
                UIBuilder.build(layout, theme, child, context);
            }

            return layout.getSize();
        }
        
        BuildContext childContext = new BuildContext();
        SimpleVec2i size = SimpleVec2i.zero();
        
        for (UIComponent child : children) {
            SimpleVec2i cSize = UIBuilder.build(layout, theme, child, childContext);
            if (cSize.x > size.x) {
                size.x = cSize.x;
            }
            
            if (cSize.y > size.y) {
                size.y = cSize.y;
            }
        }
        
        context.addAll(childContext);
        return size;
    }
    
    public static class Builder {
        private Iterable<UIComponent> children;
        private FlexSizeBehaviour sizeBehaviour;
        
        public Builder() {
        }
        
        public Builder withSizeBehaviour(FlexSizeBehaviour sizeBehaviour) {
            this.sizeBehaviour = sizeBehaviour;
            return this;
        }
        
        public Stack build(UIComponent... children) {
            return new Stack(sizeBehaviour == null ? FlexSizeBehaviour.MAX : sizeBehaviour, children);
        }
    }
}
