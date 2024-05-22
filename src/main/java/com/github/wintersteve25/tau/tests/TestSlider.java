package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.utils.Sized;
import com.github.wintersteve25.tau.components.interactable.Slider;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.Size;

public class TestSlider implements UIComponent {

    private double value;

    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Center(
            new Sized(
                Size.staticSize(200, 20),
                new Slider.Builder()
                    .withValue(value)
                    .withOnValueChanged((value) -> this.value = value)
            )
        );
    }
}
