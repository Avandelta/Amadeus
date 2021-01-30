package net.deltamine.ru;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.lang.management.ManagementFactory;

@Mod(modid = PID.ID, name = PID.NAME, version = PID.VERSION)
public class PID {

    public static final String ID = "pid";
    public static final String NAME = "PID-Logger";
    public static final String VERSION = "0.2.4-beta";

    org.apache.logging.log4j.Logger LOGGER;

    @Instance
    public static PID instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();

        String pFullname = ManagementFactory.getRuntimeMXBean().getName();

        getProcessId(pFullname);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        
    }

    EventHandler
    public void PostInit(FMLPreInitializationEvent event) {

    }

    private String getProcessId(String processFullname) {
        if(!processFullname.contains("@"))
            LOGGER.error("Exception: error with reading PID!");

        String[] pArray = processFullname.split("@");

        LOGGER.info("Minecraft PID: " + pArray[1]);
    }
}
