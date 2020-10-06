package org.falcion.avancode.network;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.falcion.avancode.Avancode;
import org.falcion.avancode.Configuration;
import org.falcion.avancode.gui.GuiScreenTop;

import java.util.List;
import java.util.Map;

public class PacketHandler implements IPacketHandler {

    public static final byte SYNC_DATABASE = 0;
    public static final byte SYNC_CONFIG = 1;
    public static final byte SEARCH = 2;

    @SideOnly(Side.CLIENT)
    public void handleClient(PacketBuffer buf, byte id, Minecraft minecraft, WorldClient world, EntityPlayerSP player) {

        if (id == 0) {
            Map<String, List<Triple<Integer, String, String>>> tableValues = Maps.newHashMap();
            Map<String, List<Pair<String, Integer>>> tableLastWinners = Maps.newHashMap();
            Map<String, Triple<Integer, String, String>> currentPlayer = Maps.newHashMap();

            NBTTagCompound nbt = ByteBufUtils.readTag((ByteBuf)buf);

            for (String key : nbt.getKeySet()) {

                if (key.endsWith("_lw")) {

                    List<Pair<String, Integer>> list = Lists.newArrayList();
                    NBTTagList nBTTagList = nbt.getTagList(key, 10);
                    key = key.substring(0, key.length() - 3);

                    for (int j = 0; j < nBTTagList.tagCount(); j++) {

                        NBTTagCompound lastWinnersData = nBTTagList.getCompoundTagAt(j);
                        String playerName = lastWinnersData.getString("playerName");

                        int value = lastWinnersData.getInteger("value");

                        list.add(Pair.of(playerName, Integer.valueOf(value)));
                    }

                    tableLastWinners.put(key, list);
                }

                if (key.endsWith("_cp")) {

                    Triple<Integer, String, String> triple = null;
                    NBTTagList nBTTagList = nbt.getTagList(key, 10);
                    key = key.substring(0, key.length() - 3);

                    for (int j = 0; j < nBTTagList.tagCount(); j++) {

                        NBTTagCompound tableData = nBTTagList.getCompoundTagAt(j);
                        Map<String, Object> tags = (Map<String, Object>) ObfuscationReflectionHelper.getPrivateValue(NBTTagCompound.class, tableData, new String[] { "tagMap", "field_74784_a" });

                        int rank = tableData.getInteger("rank");

                        String playerName = tableData.getString("playerName");
                        NBTBase tagValue = (NBTBase)tags.get("value");
                        String value = tagValue.toString();

                        if (tagValue instanceof NBTTagLong) {

                            long longValue = ((NBTTagLong)tagValue).getLong();

                            if (key.equals("time_top")) {

                                long seconds = Math.abs(longValue) / 1000L;
                                long minutes = seconds / 60L;
                                long hours = minutes / 60L;
                                long days = hours / 24L;

                                seconds %= 60L;
                                minutes %= 60L;
                                hours %= 24L;

                                String m = String.valueOf(minutes);
                                String h = String.valueOf(hours);
                                String d = String.valueOf(days);

                                value = d + " дн. " + h + " ч. " + m + " мин.";

                            } else {
                                value = String.valueOf(longValue);
                            }

                        } else if (tagValue instanceof NBTTagFloat) {

                            float floatValue = ((NBTTagFloat)tagValue).getFloat();
                            value = String.valueOf(floatValue);
                        }

                        triple = Triple.of(Integer.valueOf(rank), playerName, value);
                    }

                    if (triple != null)
                        currentPlayer.put(key, triple);
                    continue;
                }

                List<Triple<Integer, String, String>> rows = Lists.newArrayList();
                NBTTagList listTables = nbt.getTagList(key, 10);

                for (int i = 0; i < listTables.tagCount(); i++) {

                    NBTTagCompound tableData = listTables.getCompoundTagAt(i);
                    Map<String, Object> tags = (Map<String, Object>)ObfuscationReflectionHelper.getPrivateValue(NBTTagCompound.class, tableData, new String[] { "tagMap", "field_74784_a" });

                    int rank = tableData.getInteger("rank");
                    String playerName = tableData.getString("playerName");
                    NBTBase tagValue = (NBTBase)tags.get("value");
                    String value = tagValue.toString();

                    if (tagValue instanceof NBTTagLong) {

                        long longValue = ((NBTTagLong)tagValue).getLong();

                        if (key.equals("time_top")) {

                            long seconds = Math.abs(longValue) / 1000L;
                            long minutes = seconds / 60L;
                            long hours = minutes / 60L;
                            long days = hours / 24L;
                            seconds %= 60L;
                            minutes %= 60L;
                            hours %= 24L;
                            String m = String.valueOf(minutes);
                            String h = String.valueOf(hours);
                            String d = String.valueOf(days);
                            value = d + " дн. " + h + " ч. " + m + " мин.";

                        } else {
                            value = String.valueOf(longValue);
                        }

                    } else if (tagValue instanceof NBTTagFloat) {
                        float floatValue = ((NBTTagFloat)tagValue).getFloat();
                        value = String.valueOf(floatValue);
                    }

                    rows.add(Triple.of(Integer.valueOf(rank), playerName, value));
                }

                tableValues.put(key, rows);
            }

            GuiScreenTop.fillTables(tableValues);
            GuiScreenTop.fillLastWinners(tableLastWinners);
            GuiScreenTop.fillCurrentPlayer(currentPlayer);

        } else if (id == 1) {

            String json = ByteBufUtils.readUTF8String((ByteBuf)buf);
            Avancode.config = (Configuration) Avancode.GSON.fromJson(json, Configuration.class);

        } else if (id == 2) {

            List<Pair<String, String>> tableValues = Lists.newArrayList();
            String table = ByteBufUtils.readUTF8String((ByteBuf)buf);
            NBTTagCompound nbt = ByteBufUtils.readTag((ByteBuf)buf);
            List<Triple<Integer, String, String>> rows = Lists.newArrayList();
            NBTTagList listTables = nbt.getTagList("search", 10);

            for (int i = 0; i < listTables.tagCount(); i++) {

                NBTTagCompound tableData = listTables.getCompoundTagAt(i);
                Map<String, Object> tags = (Map<String, Object>)ObfuscationReflectionHelper.getPrivateValue(NBTTagCompound.class, tableData, new String[] { "tagMap", "field_74784_a" });

                int rank = tableData.getInteger("rank");

                String playerName = tableData.getString("playerName");
                NBTBase tagValue = (NBTBase)tags.get("value");
                String value = tagValue.toString();

                if (tagValue instanceof NBTTagLong) {

                    long longValue = ((NBTTagLong)tagValue).getLong();

                    if (table.equals("time_top")) {

                        long seconds = Math.abs(longValue) / 1000L;
                        long minutes = seconds / 60L;
                        long hours = minutes / 60L;
                        long days = hours / 24L;
                        seconds %= 60L;
                        minutes %= 60L;
                        hours %= 24L;
                        String m = String.valueOf(minutes);
                        String h = String.valueOf(hours);
                        String d = String.valueOf(days);
                        value = d + " дн. " + h + " ч. " + m + " мин.";

                    } else {
                        value = String.valueOf(longValue);
                    }

                } else if (tagValue instanceof NBTTagFloat) {
                    float floatValue = ((NBTTagFloat)tagValue).getFloat();
                    value = String.valueOf(floatValue);
                }

                rows.add(Triple.of(Integer.valueOf(rank), playerName, value));
            }

            GuiScreenTop.fillSearch(rows);
        }
    }
}
