package org.falcion.avancode;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.logging.Logger;

@Mod(
        modid = Avancode.MODID,
        name = Avancode.MODNAME,
        version = Avancode.VERSION,
        acceptedMinecraftVersions = Avancode.ACCEPTEDVERSIONS
)

public class Avancode {

    @Instance
    public static Avancode modInstance;

    public static final String MODID = "avancode";
    public static final String MODNAME = "Avancode";
    public static final String VERSION = "30.8.0102";
    public static final String ACCEPTEDVERSIONS = "[1.12.2]";

    public static final Logger LOGGER = Logger.getLogger(MODNAME);

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
        LOGGER.info("Preinitialization event.");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initialization event.");
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
        LOGGER.info("Postinitialization event.");
    }
}
