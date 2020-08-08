package org.falcion.avandelta.item.tool;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;
import org.falcion.avandelta.Main;
import org.falcion.avandelta.init.Items;
import org.falcion.avandelta.utils.CheckModel;

public class GravititeSword extends ItemSword implements CheckModel {

    public GravititeSword(String name, ToolMaterial material) {

        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);

        Items.itemList.add(this);
    }

    @Override
    public void registerModels() {

        Main.clientProxy.registerItemRenderer(this, 0, "inventory");
    }
}
