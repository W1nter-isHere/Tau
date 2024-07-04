package com.github.wintersteve25.tau.menu.handlers;

import com.github.wintersteve25.tau.menu.TauContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ItemSlotHandler implements ISlotHandler {

    private final IItemHandler inventory;
    private final int index;

    public ItemSlotHandler(IItemHandler inventory, int index) {
        this.inventory = inventory;
        this.index = index;
    }

    @Override
    public void setupSync(TauContainerMenu menu, Inventory playerInv, int x, int y) {
        menu.addSlot(new SlotItemHandler(inventory, index, x, y));
    }
}
