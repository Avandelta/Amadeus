package org.falcion.avancode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraftforge.fml.common.FMLCommonHandler;
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

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
    }
}
