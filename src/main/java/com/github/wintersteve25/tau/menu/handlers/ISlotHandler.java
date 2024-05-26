package com.github.wintersteve25.tau.menu.handlers;

import com.github.wintersteve25.tau.menu.TauContainerMenu;
import net.minecraft.world.entity.player.Inventory;

public interface ISlotHandler {
    void setupSync(TauContainerMenu menu, Inventory playerInv, int x, int y);
}
