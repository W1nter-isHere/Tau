package com.github.wintersteve25.tau.menu.handlers;

import com.github.wintersteve25.tau.menu.TauContainerMenu;
import com.github.wintersteve25.tau.utils.Variable;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ItemSlotHandler implements ISlotHandler {

    private final IItemHandler inventory;
    private final int index;
    private final Variable<Boolean> enabled;

    public ItemSlotHandler(IItemHandler inventory, int index, Variable<Boolean> enabled) {
        this.inventory = inventory;
        this.index = index;
        this.enabled = enabled;
    }

    @Override
    public void setupSync(TauContainerMenu menu, Inventory playerInv, int x, int y) {
        menu.addSlot(new DisablableSlot(inventory, index, x + 1, y + 1, enabled));
    }
    
    private static class DisablableSlot extends SlotItemHandler {
        private final Variable<Boolean> enabled;
        
        public DisablableSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Variable<Boolean> enabled) {
            super(itemHandler, index, xPosition, yPosition);
            this.enabled = enabled;
        }

        @Override
        public boolean isActive() {
            return enabled.getValue();
        }
    }
}
