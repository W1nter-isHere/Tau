package com.github.wintersteve25.tau.components.inventory;

import com.github.wintersteve25.tau.build.BuildContext;
import com.github.wintersteve25.tau.components.base.PrimitiveUIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.SimpleVec2i;
import net.minecraft.world.inventory.Slot;

public class PlayerInventory implements PrimitiveUIComponent {

    private static final SimpleVec2i SIZE = new SimpleVec2i(162, 76); // 4 rows with 1 hotbar

    @Override
    public SimpleVec2i build(Layout layout, Theme theme, BuildContext context) {

        SimpleVec2i pos = layout.getPosition(SIZE);

        context.renderables().add((pGuiGraphics, pMouseX, pMouseY, pPartialTick) -> {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                    theme.drawSlot(pGuiGraphics, pos.x + j * 18, pos.y + i * 18);
                }
            }

            for (int i = 0; i < 9; i++) {
                theme.drawSlot(pGuiGraphics, pos.x + i * 18, pos.y + 58);
            }
        });

        return SIZE;
    }
}
