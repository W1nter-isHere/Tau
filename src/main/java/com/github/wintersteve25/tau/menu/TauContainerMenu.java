package com.github.wintersteve25.tau.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TauContainerMenu extends AbstractContainerMenu implements ContainerListener {

    public final CompoundTag data;
    public final Level level;
    public final BlockPos pos;

    public TauContainerMenu(@Nullable MenuType<?> pMenuType, Inventory playerInventory, int pContainerId, BlockPos pos) {
        super(pMenuType, pContainerId);

        this.data = new CompoundTag();
        this.level = playerInventory.player.level();
        this.pos = pos;

        addSlotListener(this);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void slotChanged(AbstractContainerMenu pContainerToSend, int pDataSlotIndex, ItemStack pStack) {
    }

    @Override
    public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue) {
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
