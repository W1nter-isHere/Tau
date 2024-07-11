package com.github.wintersteve25.tau.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class ClientSoundHelper {
    private static final SoundManager SOUND_MANAGER = Minecraft.getInstance().getSoundManager();
    
    public static void playButtonClick() {
        ClientSoundHelper.playSound(SoundEvents.UI_BUTTON_CLICK.value());
    }

    public static void playSound(SoundEvent soundEvent) {
        playSound(soundEvent, 0.25f);
    }

    public static void playSound(SoundEvent soundEvent, float volume) {
        playSound(soundEvent, volume, 1);
    }

    public static void playSound(SoundEvent sound, float volume, float pitch) {
        SOUND_MANAGER.play(SimpleSoundInstance.forUI(sound, pitch, volume));
    }
}
