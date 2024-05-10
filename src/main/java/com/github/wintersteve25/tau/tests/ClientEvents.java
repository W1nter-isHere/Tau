package com.github.wintersteve25.tau.tests;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;
import com.github.wintersteve25.tau.Tau;
import com.github.wintersteve25.tau.renderer.ScreenUIRenderer;

@Mod.EventBusSubscriber(modid = Tau.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {
    @SubscribeEvent
    public static void onKeyDown(InputEvent.Key evet) {
        if (evet.getKey() == GLFW.GLFW_KEY_COMMA) {
            Minecraft.getInstance().setScreen(new ScreenUIRenderer(new TestAll(), true));
        }
    }
}
