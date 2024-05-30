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
    SoundManager SOUND_MANAGER = Minecraft.getInstance().getSoundManager();

    UIComponent build(Layout layout, Theme theme);

    default void playSound(SoundEvent soundEvent) {
        playSound(soundEvent, 0.25f);
    }

    default void playSound(SoundEvent soundEvent, float volume) {
        playSound(soundEvent, volume, 1);
    }

    default void playSound(SoundEvent sound, float volume, float pitch) {
        SOUND_MANAGER.play(SimpleSoundInstance.forUI(sound, pitch, volume));
    }
}
