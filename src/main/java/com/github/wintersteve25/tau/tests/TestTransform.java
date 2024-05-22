package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.interactable.Button;
import com.github.wintersteve25.tau.components.utils.Sized;
import com.github.wintersteve25.tau.components.utils.Text;
import com.github.wintersteve25.tau.components.render.Transform;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.Size;
import com.github.wintersteve25.tau.utils.Transformation;
import org.joml.Vector3f;

public class TestTransform implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Center(
                new Transform(
                        new Sized(Size.staticSize(20, 20),
                                new Button.Builder()
                                        .withOnPress((btn) -> System.out.println("Ho"))
                                        .build(new Text.Builder("Halo"))),
                        Transformation.translate(new Vector3f(0, 30, 1))
                ));
    }
}
