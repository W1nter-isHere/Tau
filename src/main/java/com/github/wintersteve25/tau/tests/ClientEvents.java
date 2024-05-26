package com.github.wintersteve25.tau.tests;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;
import com.github.wintersteve25.tau.Tau;
import com.github.wintersteve25.tau.renderer.ScreenUIRenderer;

@EventBusSubscriber(modid = Tau.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class ClientEvents {
    @SubscribeEvent
    public static void onKeyDown(InputEvent.Key evet) {
        if (evet.getKey() == GLFW.GLFW_KEY_COMMA) {
            Minecraft.getInstance().setScreen(new ScreenUIRenderer(new TestAll(), true));
        }
    }
}
