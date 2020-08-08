package org.falcion.avandelta.init;

import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

import org.falcion.avandelta.item.ItemBase;
import org.falcion.avandelta.item.tool.*;

import java.util.ArrayList;
import java.util.List;

public class Items {

    public static final List<Item> itemList = new ArrayList<Item>();

    public static final Item auxiteIngot = new ItemBase("auxite_ingot");
    public static final Item protoniteIngot = new ItemBase("protonite_ingot");
    public static final Item gravititeIngot = new ItemBase("gravitite_ingot");

    /*
        [Auxite]
        Harvest: 5,
        Uses: 2031,
        Efficiency: 10,
        Damage: 10,
        Enchatibility: 5
     */
    public static final ToolMaterial auxiteMaterial
                                = EnumHelper.addToolMaterial("auxite", 5, 2031, 10.0f, 10.0f, 5);

    public static final ItemSword auxiteSword = new AuxiteSword("auxite_sword", auxiteMaterial);
    public static final ItemPickaxe auxitePickaxe = new AuxitePickaxe("auxite_pickaxe", auxiteMaterial);
    public static final ItemSpade auxiteShovel = new AuxiteShovel("auxite_shovel", auxiteMaterial);
    public static final ItemAxe auxiteAxe = new AuxiteAxe("auxite_axe", auxiteMaterial);
    public static final ItemHoe auxiteHoe = new AuxiteHoe("auxite_hoe", auxiteMaterial);

    /*
        [Protonite]
        Harvest: 10,
        Uses: 4032,
        Efficiency: 20,
        Damage: 15,
        Enchatibility: 5
     */
    public static final ToolMaterial protoniteMaterial
                                = EnumHelper.addToolMaterial("protonite", 10, 4032, 20.0f, 15.0f, 5);

    public static final ItemSword protoniteSword = new ProtoniteSword("protonite_sword", protoniteMaterial);
    public static final ItemPickaxe protonitePickaxe = new ProtonitePickaxe("protonite_pickaxe", protoniteMaterial);
    public static final ItemSpade protoniteShovel = new ProtoniteShovel("protonite_shovel", protoniteMaterial);
    public static final ItemAxe protoniteAxe = new ProtoniteAxe("protonite_axe", protoniteMaterial);
    public static final ItemHoe protoniteHoe = new ProtoniteHoe("protonite_hoe", protoniteMaterial);

    /*
        [Gravitite]
        Harvest: 15,
        Uses: 8064,
        Efficiency: 40,
        Damage: 20,
        Enchatibility: 10
     */
    public static final ToolMaterial gravititeMaterial
                                = EnumHelper.addToolMaterial("gravitite", 15, 8064, 40.0f, 20.0f, 10);

    public static final ItemSword gravititeSword = new GravititeSword("gravitite_sword", gravititeMaterial);
    public static final ItemPickaxe gravititePickaxe = new GravititePickaxe("gravitite_pickaxe", gravititeMaterial);
    public static final ItemSpade gravititeShovel = new GravititeShovel("gravitite_shovel", gravititeMaterial);
    public static final ItemAxe gravititeAxe = new GravititeAxe("gravitite_axe", gravititeMaterial);
    public static final ItemHoe gravititeHoe = new GravititeHoe("gravitite_hoe", gravititeMaterial);
}
