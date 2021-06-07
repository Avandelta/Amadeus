package deltamine.ru;

import deltamine.ru.handlers.RegistryHandler;
import deltamine.ru.proxy.CommonProxy;
import deltamine.ru.util.ConfigHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.management.ManagementFactory;

@Mod(modid = Lore.ID, name = Lore.NAME, version = Lore.VERSION)
public class Neptune {
    @Mod.Instance
    public static Neptune instance;

    public static File config;

    @SidedProxy(clientSide = Lore.CLIENT, serverSide = Lore.SERVER)
    public static CommonProxy proxy;

    static org.apache.logging.log4j.Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        String processFName = ManagementFactory.getRuntimeMXBean().getName();

        RegistryHandler.preInitRegistries(event);

        if(ConfigHandler.isCommonSide == false) {
            logger.info("Minecraft PID: " + processFName.split("@")[0]);
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event) {

    }

    public static Logger getLogger() {
        return logger;
    }
}
