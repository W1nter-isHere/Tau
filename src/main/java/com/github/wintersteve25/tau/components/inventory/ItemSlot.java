package com.github.wintersteve25.tau.components.inventory;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.menu.MenuSlot;
import com.github.wintersteve25.tau.menu.handlers.ItemSlotHandler;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public class ItemSlot implements PrimitiveUIComponent {

    // hard coded texture length for a slot in minecraft
    private static final SimpleVec2i SIZE = new SimpleVec2i(18, 18);
    private final ItemSlotHandler slotHandler;

    public ItemSlot(ItemSlotHandler slotHandler) {
        this.slotHandler = slotHandler;
    }

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {
        SimpleVec2i pos = layout.getPosition(SIZE);

        context.renderables().add((pGuiGraphics, pMouseX, pMouseY, pPartialTick) -> theme.drawSlot(pGuiGraphics, pos.x, pos.y));
        context.slots().add(new MenuSlot<>(pos, slotHandler));

        return SIZE;
    }
}
