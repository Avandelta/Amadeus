package deltamine.ru;

import deltamine.ru.handlers.RegistryHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;

@Mod(modid = Lore.ID, name = Lore.NAME, version = Lore.VERSION)
public class Core {
    public static File config;
    @Instance
    public static Core instance;

    org.apache.logging.log4j.Logger logger;

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        logger.debug("Catching PID");

        String processFullname = ManagementFactory.getRuntimeMXBean().getName();

        logger.info("Minecraft PID: " + processFullname.split("@")[0]);

        RegistryHandler.preInitRegistries(event);
    }

    @EventHandler
    public void init(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public void onConnect(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;

        logger.info(String.format("Session UUID [%s]: %s", LocalDateTime.now(), player.getUniqueID()));
    }
}
