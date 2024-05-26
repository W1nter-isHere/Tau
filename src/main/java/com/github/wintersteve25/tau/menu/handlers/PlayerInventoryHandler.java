package com.github.wintersteve25.tau.menu.handlers;

import com.github.wintersteve25.tau.menu.TauContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class PlayerInventoryHandler implements ISlotHandler {
    @Override
    public void setupSync(TauContainerMenu menu, Inventory playerInv, int x, int y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                menu.addSlot(new Slot(playerInv, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            // skip the space of the first 3 rows and some gap
            menu.addSlot(new Slot(playerInv, i, x + i * 18, y + 58));
        }
    }
}
