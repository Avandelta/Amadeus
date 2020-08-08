package org.falcion.avandelta.utils.handler;

import org.falcion.avandelta.init.Items;
import org.falcion.avandelta.utils.CheckModel;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {

        event.getRegistry().registerAll(Items.itemList.toArray(new Item[0]));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event) {

        for(Item item : Items.itemList) {

            if(item instanceof CheckModel) {

                ((CheckModel)item).registerModels();
            }
        }
    }
}
