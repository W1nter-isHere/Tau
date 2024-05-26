package com.github.wintersteve25.tau.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class ItemRenderProvider implements RenderProvider {

    private final ItemStack itemStack;

    public ItemRenderProvider(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemRenderProvider(Item item) {
        this(new ItemStack(item));
    }

    public ItemRenderProvider(Block block) {
        this(new ItemStack(block.asItem()));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, int x, int y, int width, int height) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        graphics.renderItem(itemStack, x, y);
        poseStack.popPose();
    }
}
