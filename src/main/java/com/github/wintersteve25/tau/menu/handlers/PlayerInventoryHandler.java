package com.github.wintersteve25.tau.menu.handlers;

import com.github.wintersteve25.tau.menu.TauContainerMenu;
import com.github.wintersteve25.tau.utils.Variable;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class PlayerInventoryHandler implements ISlotHandler {
    
    private final Variable<Boolean> enabled;

    public PlayerInventoryHandler(Variable<Boolean> enabled) {
        this.enabled = enabled;
    }

    @Override
    public void setupSync(TauContainerMenu menu, Inventory playerInv, int x, int y) {
        x += 1;
        y += 1;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                menu.addSlot(new DisablableSlot(playerInv, j + i * 9 + 9, x + j * 18, y + i * 18, enabled));
            }
        }

        for (int i = 0; i < 9; i++) {
            // skip the space of the first 3 rows and some gap
            menu.addSlot(new DisablableSlot(playerInv, i, x + i * 18, y + 58, enabled));
        }
    }

    private static class DisablableSlot extends Slot {

        private final Variable<Boolean> enabled;
        
        public DisablableSlot(Container container, int slot, int x, int y, Variable<Boolean> enabled) {
            super(container, slot, x, y);
            this.enabled = enabled;
        }

        @Override
        public boolean isActive() {
            return enabled.getValue();
        }
    }
}
