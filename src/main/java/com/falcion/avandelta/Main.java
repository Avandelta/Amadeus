package com.falcion.avandelta;

import com.falcion.avandelta.utils.Lore;
import com.falcion.avandelta.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Lore.MODID, name = Lore.MODNAME, version = Lore.VERSION)
public class Main {

    @Instance
    public static Main instance;

    @SidedProxy(clientSide = Lore.CLIENT_PROXY_CLASS, serverSide = Lore.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public static void PreInit(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {

    }

    @EventHandler
    public static void Postinit(FMLPostInitializationEvent event) {

    }
}
