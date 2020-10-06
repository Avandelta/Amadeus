package org.falcion.avancode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.falcion.avancode.network.IPacketHandler;
import org.falcion.avancode.network.NetworkManager;
import org.falcion.avancode.network.PacketHandler;

import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Mod(
        modid = Avancode.MODID,
        name = Avancode.MODNAME,
        version = Avancode.VERSION,
        acceptableRemoteVersions = "*"
)
public class Avancode {

    @Instance
    public static Avancode instanceMod;

    public static final String MODID = "avancode";
    public static final String MODNAME = "Avancode";
    public static final String VERSION = "30.8.0102";

    public static final Logger LOGGER = LogManager.getLogManager().getLogger(MODNAME);

    public static final Gson GSON = (new GsonBuilder()).create();

    public static final boolean SERVER_COMPILATION = false;

    public static Configuration config;

    @SideOnly(Side.SERVER)
    public static Map<String, Long> listWinnersTime;

    @SideOnly(Side.SERVER)
    public static Database database;
    public static ExtendedData extendedData;

    public static NetworkManager network;

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
        LOGGER.info("Loading.");
        LOGGER.info("Creating network system.");

        network = NetworkManager.registerPacket((IPacketHandler) new PacketHandler)
    }
}
