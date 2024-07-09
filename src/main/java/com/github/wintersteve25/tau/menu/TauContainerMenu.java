package com.github.wintersteve25.tau.menu;

import com.github.wintersteve25.tau.Tau;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class TauContainerMenu extends AbstractContainerMenu {

    public final Level level;
    public final BlockPos pos;

    private final Map<String, IndexedDataSlot> dataSlots;
    private final TauMenuHolder holder;

    public TauContainerMenu(TauMenuHolder holder, Inventory playerInventory, int pContainerId, BlockPos pos) {
        super(holder.get(), pContainerId);

        this.level = playerInventory.player.level();
        this.pos = pos;
        this.dataSlots = new HashMap<>();
        this.holder = holder;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return holder.getMenu().quickMoveStack(this, pPlayer, pIndex);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return holder.getMenu().stillValid(this, pPlayer);
    }

    public @NotNull Slot addSlot(@NotNull Slot pSlot) {
        return super.addSlot(pSlot);
    }

    public void addDataSlot(String name, DataSlot slot) {
        if (dataSlots.containsKey(name)) {
            Tau.LOGGER.error("Duplicated data slot key: " + name);
            return;
        }

        dataSlots.put(name, new IndexedDataSlot(dataSlots.size(), slot));
        addDataSlot(slot);
    }

    public Optional<Supplier<Integer>> getGetterForDataSlot(String name) {
        if (!dataSlots.containsKey(name)) {
            Tau.LOGGER.error("Unknown data slot key: " + name);
            return Optional.empty();
        }

        return Optional.of(dataSlots.get(name).dataSlot()::get);
    }

    @Override
    public boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return super.moveItemStackTo(stack, startIndex, endIndex, reverseDirection);
    }
    
    public BlockEntity getBlockEntity() {
        return level.getBlockEntity(pos);
    }

    public <T extends BlockEntity> Optional<T> getBlockEntity(Class<T> t) {
        BlockEntity e = level.getBlockEntity(pos);
        if (t.isInstance(e)) {
            return Optional.of(t.cast(e));
        }
        
        return Optional.empty();
    }

    private record IndexedDataSlot(int index, DataSlot dataSlot) {}
}
