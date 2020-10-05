package org.falcion.avancode;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.falcion.avancode.utils.Lore;

@Mod(modid = Lore.MODID, name = Lore.MODNAME, version = Lore.VERSION)
public class Main {

    @Instance
    public static Main instance;

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
