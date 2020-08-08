package org.falcion.avandelta.item.tool;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import org.falcion.avandelta.Main;
import org.falcion.avandelta.init.Items;
import org.falcion.avandelta.utils.CheckModel;

public class GravititeAxe extends ItemAxe implements CheckModel {

    public GravititeAxe(String name, ToolMaterial material) {

        super(material, 10.0F, -3.2F);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.TOOLS);

        Items.itemList.add(this);
    }

    @Override
    public void registerModels() {

        Main.clientProxy.registerItemRenderer(this, 0, "inventory");
    }
}
