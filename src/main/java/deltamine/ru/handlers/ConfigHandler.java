package deltamine.ru.handlers;

import deltamine.ru.Core;
import deltamine.ru.Lore;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ConfigHandler {
    public static Configuration config;

    public static boolean isCommonSide = false;

    public static void init(File file) {
        config = new Configuration(file);

        String category;

        category = "Proxy";
        config.addCustomCategoryComment(category, "Set up boolean value if this mod runs on a server.");
        isCommonSide = config.getBoolean("isCommon", category, false, "If your server has this mod on client and server, type in true to change the work of mod for server.");

        config.save();
    }

    public static void registerConfig(FMLPreInitializationEvent event) {
        Core.config = new File(event.getModConfigurationDirectory() + "/" + Lore.ID);
        Core.config.mkdirs();
        init(new File(Core.config.getPath(), Lore.ID + ".cfg"));
    }
}
