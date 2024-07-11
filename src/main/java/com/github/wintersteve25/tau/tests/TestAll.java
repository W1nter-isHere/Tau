package com.github.wintersteve25.tau.tests;

import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.components.layout.Align;
import com.github.wintersteve25.tau.components.layout.Center;
import com.github.wintersteve25.tau.components.layout.Stack;
import com.github.wintersteve25.tau.components.interactable.Button;
import com.github.wintersteve25.tau.components.interactable.ListView;
import com.github.wintersteve25.tau.components.utils.Sized;
import com.github.wintersteve25.tau.components.utils.Text;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.layout.LayoutSetting;
import com.github.wintersteve25.tau.renderer.ScreenUIRenderer;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.FlexSizeBehaviour;
import com.github.wintersteve25.tau.utils.Size;
import net.minecraft.client.Minecraft;

public class TestAll implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new ListView.Builder()
                .withSpacing(2)
                .build(
                        new TestButton(new TestAlign()),
                        new TestButton(new com.github.wintersteve25.tau.tests.TestButton()),
                        new TestButton(new TestCenter()),
                        new TestButton(new TestClip()),
                        new TestButton(new TestColumn()),
                        new TestButton(new TestContainer()),
                        new TestButton(new TestDynamic()),
                        new TestButton(new TestListView()),
                        new TestButton(new TestPadding()),
                        new TestButton(new TestPositioned()),
                        new TestButton(new TestRender()),
                        new TestButton(new TestRenderable()),
                        new TestButton(new TestRow()),
                        new TestButton(new TestSized()),
                        new TestButton(new TestSlider()),
                        new TestButton(new TestStack()),
                        new TestButton(new TestText()),
                        new TestButton(new TestTextField()),
                        new TestButton(new TestTexture()),
                        new TestButton(new TestTooltip()),
                        new TestButton(new TestTransform()),
                        new TestButton(new TestWidgetWrapper()),
                        new TestButton(new TestInventoryVisual())
                );
    }

    private static final class TestButton implements UIComponent {

        private final UIComponent screen;

        private TestButton(UIComponent screen) {
            this.screen = screen;
        }

        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return new Sized(
                    Size.staticSize(200, 20),
                    new Button.Builder()
                            .withOnPress((button) -> Minecraft.getInstance().setScreen(new ScreenUIRenderer(new TestScreen(screen), true)))
                            .build(new Center(new Text.Builder(screen.getClass().getSimpleName())))
            );
        }
    }

    private static final class TestScreen implements UIComponent {

        private final UIComponent screen;

        private TestScreen(UIComponent screen) {
            this.screen = screen;
        }

        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return new Stack(
                    FlexSizeBehaviour.MAX,
                    screen,
                    new Align.Builder()
                            .withHorizontal(LayoutSetting.START)
                            .withVertical(LayoutSetting.START)
                            .build(new Sized(
                                    Size.staticSize(60, 20),
                                    new Button.Builder()
                                            .withOnPress((button) -> Minecraft.getInstance().setScreen(new ScreenUIRenderer(new TestAll(), true)))
                                            .build(new Center(new Text.Builder("Back"))))
                            )
            );
        }
    }
}
