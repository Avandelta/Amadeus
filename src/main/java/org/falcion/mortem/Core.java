package org.falcion.mortem;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.lang.management.ManagementFactory;

@Mod(modid = Lore.MODID, name = Lore.MODNAME, version = Lore.VERSION)
public class Core {

    @Instance
    public static Core instance;

    org.apache.logging.log4j.Logger logger;

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        logger.info("Preinitialization event.");
        logger.info("Catching Minecraft PID.");

        String processFullname = ManagementFactory.getRuntimeMXBean().getName();

        String[] processParams = processFullname.split("0");

        logger.info("Minecraft PID: " + processParams[0]);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Initialization event.");
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
        logger.info("Postinitialization event.");
    }
}
