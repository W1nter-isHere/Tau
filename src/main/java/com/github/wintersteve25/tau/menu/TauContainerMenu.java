package com.github.wintersteve25.tau.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class TauContainerMenu extends AbstractContainerMenu {

    public TauContainerMenu(@Nullable MenuType<?> pMenuType, Inventory playerInventory, int pContainerId) {
        super(pMenuType, pContainerId);
        setupSlots(playerInventory);
    }

    public abstract void setupSlots(Inventory playerInventory);

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }



    public @NotNull Slot addSlot(@NotNull Slot pSlot) {
        return super.addSlot(pSlot);
    }

    public @NotNull DataSlot addDataSlot(@NotNull DataSlot pIntValue) {
        return super.addDataSlot(pIntValue);
    }

    public void addDataSlots(@NotNull ContainerData pArray) {
        super.addDataSlots(pArray);
    }
}
