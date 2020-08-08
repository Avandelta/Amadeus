package com.falcion.avandelta.item;

import com.falcion.avandelta.Main;
import com.falcion.avandelta.init.Items;
import com.falcion.avandelta.utils.CheckModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import javax.naming.directory.ModificationItem;

public class ItemBase extends Item implements CheckModel {

    public ItemBase(String name) {

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MATERIALS);

        Items.itemList.add(this);
    }

    @Override
    public void registerModels() {

        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
