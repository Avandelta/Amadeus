package deltamine.ru.handlers;

import deltamine.ru.util.ConfigHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class RegistryHandler {
    public static void preInitRegistries(FMLPreInitializationEvent event) {
        ConfigHandler.registerConfig(event);
    }
}