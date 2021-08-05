package io.regulate;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.lang.management.ManagementFactory;

@Mod(modid = "regulate-ci", name = "RegulateCI", version = "1.2-b")
public class Regulate {
    @Instance
    public static Regulate instance;

    static org.apache.logging.log4j.Logger Logger;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        Logger = event.getModLog();

        String processName = ManagementFactory.getRuntimeMXBean().getName();

        Logger.info("Minecraft PID: " + processName.split("@")[0]);
    }
}
