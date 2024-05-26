package com.github.wintersteve25.tau;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Tau.MOD_ID)
public class Tau {
    public static final String MOD_ID = "tau";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public Tau(IEventBus bus) {
    }
}
