package org.falcion.avandelta.item.tool;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemHoe;
import org.falcion.avandelta.Main;
import org.falcion.avandelta.init.Items;
import org.falcion.avandelta.utils.CheckModel;

public class ProtoniteHoe extends ItemHoe implements CheckModel {

    public ProtoniteHoe(String name, ToolMaterial material) {

        super(material);
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
