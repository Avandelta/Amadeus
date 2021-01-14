package org.falcion.mortem;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.lang.management.ManagementFactory;

@Mod(modid = Lore.ID, name = Lore.NAME, version = Lore.VERSION)
public class Core {

    @Instance
    public static Core instance;

    org.apache.logging.log4j.Logger LOGGER;

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();

        LOGGER.debug("Catching Minecraft PID.");

        String pName = ManagementFactory.getRuntimeMXBean().getName();

        String pID = pName.split("@")[0];

        LOGGER.info("Minecraft PID: " + pID);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) {

    }
}
