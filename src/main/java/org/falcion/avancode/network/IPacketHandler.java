package org.falcion.avancode.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPacketHandler {

    @SideOnly(Side.CLIENT)
    void handleClient(PacketBuffer packetBuffer, byte paramByte, Minecraft minecraft, WorldClient worldClient, EntityPlayerSP playerSP);

    void handleServer(PacketBuffer packetBuffer, byte paramByte, WorldServer worldServer, EntityPlayerMP playerMP);

    String getChannel();
}
