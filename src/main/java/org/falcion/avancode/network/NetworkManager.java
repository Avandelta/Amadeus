package org.falcion.avancode.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class NetworkManager {

    private FMLEventChannel channel;

    private IPacketHandler packetHandler;

    private NetworkManager(IPacketHandler handlerPacket) {
        this.packetHandler = packetHandler;
        this.channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(handlerPacket.getChannel());
        this.channel.register(this);
    }

    public static NetworkManager registerPacket(IPacketHandler packetHandler) {
        return new NetworkManager(packetHandler);
    }

    @SubscribeEvent
    public void onServer(FMLNetworkEvent.ServerCustomPacketEvent event) {

        if(event.getPacket().channel().equals(this.packetHandler.getChannel())) {

            ByteBuf buf = event.getPacket().payload();
            NetHandlerPlayServer serverHandler = (NetHandlerPlayServer) event.getHandler();

            EntityPlayerMP playerMP = serverHandler.player;
            this.packetHandler.handleServer(new PacketBuffer(buf), buf.readByte(), (WorldServer)playerMP.world, playerMP);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClient(FMLNetworkEvent.ClientCustomPacketEvent event) {

        if(event.getPacket().channel().equals(this.packetHandler.getChannel())) {

            ByteBuf buf = event.getPacket().payload();

            Minecraft minecraft = Minecraft.getMinecraft();
            EntityPlayerSP playerSP = minecraft.player;

            this.packetHandler.handleClient(new PacketBuffer(buf), buf.readByte(), minecraft, (WorldClient)playerSP.world, playerSP);
        }
    }

    public void sendServer(FMLProxyPacket packet) {
        this.channel.sendToServer(packet);
    }

    public void sendAll(FMLProxyPacket packet) {
        this.channel.sendToAll(packet);
    }

    public void sentTo(FMLProxyPacket packet, EntityPlayerMP player) {
        this.channel.sendTo(packet, player);
    }

    public void sendToAllAround(FMLProxyPacket packet, double x, double y, double z, double range, int dimension) {
        this.channel.sendToAllAround(packet, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
    }

    public void sendToDimension(FMLProxyPacket packet, int id) {
        this.channel.sendToDimension(packet, id);
    }

    public <T> FMLProxyPacket createPacket(byte id, T... data) {
        return createPacket(Unpooled.buffer(32), id, data);
    }

    public <T> FMLProxyPacket createPacket(ByteBuf buf, byte id) {
        buf.writeByte(id);
        return new FMLProxyPacket((PacketBuffer) buf, this.packetHandler.getChannel());
    }

    public <T> FMLProxyPacket createPacket(ByteBuf buf, byte id, T... data) {
        buf.writeByte(id);
        for (T value : data)
            writeData(buf, value);
        return new FMLProxyPacket((PacketBuffer) buf, this.packetHandler.getChannel());
    }

    public static UUID readUUID(ByteBuf buf) {
        return new UUID(buf.readLong(), buf.readLong());
    }

    private <T> void writeData(ByteBuf buf, T value) {

        if (value instanceof Byte) {
            buf.writeByte(((Byte)value).byteValue());
        } else if (value instanceof Boolean) {
            buf.writeBoolean(((Boolean)value).booleanValue());
        } else if (value instanceof Short) {
            buf.writeShort(((Short)value).shortValue());
        } else if (value instanceof Integer) {
            buf.writeInt(((Integer)value).intValue());
        } else if (value instanceof Float) {
            buf.writeFloat(((Float)value).floatValue());
        } else if (value instanceof Double) {
            buf.writeDouble(((Double)value).doubleValue());
        } else if (value instanceof Long) {
            buf.writeLong(((Long)value).longValue());
        } else if (value instanceof Character) {
            buf.writeChar(((Character)value).charValue());
        } else if (value instanceof String) {
            ByteBufUtils.writeUTF8String(buf, (String)value);
        } else if (value instanceof boolean[]) {
            boolean[] array = (boolean[])value;
            buf.writeInt(array.length);
            for (boolean i : array)
                buf.writeBoolean(i);
        } else if (value instanceof short[]) {
            short[] array = (short[])value;
            buf.writeInt(array.length);
            for (short i : array)
                buf.writeShort(i);
        } else if (value instanceof int[]) {
            int[] array = (int[])value;
            buf.writeInt(array.length);
            for (int i : array)
                buf.writeInt(i);
        } else if (value instanceof long[]) {
            long[] array = (long[])value;
            buf.writeInt(array.length);
            for (long i : array)
                buf.writeLong(i);
        } else if (value instanceof float[]) {
            float[] array = (float[])value;
            buf.writeInt(array.length);
            for (float i : array)
                buf.writeFloat(i);
        } else if (value instanceof double[]) {
            double[] array = (double[])value;
            buf.writeInt(array.length);
            for (double i : array)
                buf.writeDouble(i);
        } else if (value instanceof char[]) {
            char[] array = (char[])value;
            buf.writeInt(array.length);
            for (char i : array)
                buf.writeChar(i);
        } else if (value instanceof String[]) {
            String[] array = (String[])value;
            buf.writeInt(array.length);
            for (String i : array)
                ByteBufUtils.writeUTF8String(buf, i);
        } else if (value instanceof Map.Entry) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>)value;
            writeData(buf, entry.getKey());
            writeData(buf, entry.getValue());
        } else if (value instanceof ItemStack) {
            ByteBufUtils.writeItemStack(buf, (ItemStack)value);
        } else if (value instanceof NBTTagCompound) {
            ByteBufUtils.writeTag(buf, (NBTTagCompound)value);
        } else if (value instanceof UUID) {
            UUID uuid = (UUID)value;
            buf.writeLong(uuid.getMostSignificantBits());
            buf.writeLong(uuid.getLeastSignificantBits());
        } else if (value instanceof Collection) {
            Collection<?> collection = (Collection)value;
            Iterator<?> iterator = collection.iterator();
            buf.writeInt(collection.size());
            while (iterator.hasNext())
                writeData(buf, iterator.next());
        } else if (value instanceof Map) {
            Map<?, ?> map = (Map<?, ?>)value;
            buf.writeInt(map.size());
            for (Map.Entry<?, ?> e : map.entrySet()) {
                writeData(buf, e.getKey());
                writeData(buf, e.getValue());
            }
        }
    }

    private <T> void writeArray(ByteBuf buf, T[] array) {
        buf.writeInt(array.length);
        for (T value : array)
            writeData(buf, value);
    }
}
