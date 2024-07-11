package com.github.wintersteve25.tau.components.base;

import com.github.wintersteve25.tau.theme.Theme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import com.github.wintersteve25.tau.layout.Layout;

/**
 * The base of all UI Components
 */
public interface UIComponent {
    UIComponent build(Layout layout, Theme theme);
}
