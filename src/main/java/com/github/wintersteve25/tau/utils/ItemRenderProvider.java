package com.github.wintersteve25.tau.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
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
        renderItemStackInGui(graphics.pose(), itemStack, x, y, width, height);
    }

    private static void renderItemStackInGui(PoseStack poseStack, ItemStack stackToRender, int x, int y, int width, int height) {
        poseStack.pushPose();
        poseStack.mulPoseMatrix(poseStack.last().pose());
        RenderSystem.enableDepthTest();
        renderItemModelIntoGUI(stackToRender, x, y, width, height);
        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    //modified from renderItemModelIntoGUI in ItemRenderer class in vanilla
    private static void renderItemModelIntoGUI(ItemStack stack, int x, int y, float xScale, float yScale) {

        Minecraft minecraft = Minecraft.getInstance();
        ItemRenderer renderer = minecraft.getItemRenderer();
        BakedModel bakedmodel = renderer.getModel(stack, null, null, 0);

        minecraft.textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((float) x, (float) y, 100.0F);
        posestack.translate(8.0F, 8.0F, 0.0F);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(16.0F, 16.0F, 16.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        renderer.render(stack, ItemDisplayContext.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
}
