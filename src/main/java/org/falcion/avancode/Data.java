package org.falcion.avancode;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Data {

    public static final TMap<String, Data> PLAYERS_DATA = (TMap<String, Data>) new THashMap();

    private static final Expression EXPRESSION = (new ExpressionBuilder(Avancode.config.getExpression())).variables(new String[]{"playerKills", "mobKills", "playerDeaths", "playingTime", "walkingDistance", "playerJumps", "playerMoney"}).build();

    private String playerName;

    private int playerKills;
    private int mobKills;

    private int playerDeaths;

    private long playingTime;

    private float walkingDistance;

    private int playerJumps;

    private int playerMoney;

    private long playerPoints;

    public Data(String name) {
        this.playerName = name;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getPlayerKills() {
        return this.playerKills;
    }

    public void setPlayerKills(int kills) {
        this.playerKills = kills;
        calculatePoints();
    }

    public int getMobKills() {
        return this.mobKills;
    }

    public void setMobKills(int kills) {
        this.mobKills = kills;
        calculatePoints();
    }

    public int getPlayerDeaths() {
        return this.playerDeaths;
    }

    public void setPlayerDeaths(int deaths) {
        this.playerDeaths = deaths;
        calculatePoints();
    }

    public long getPlayingTime() {
        return this.playingTime;
    }

    public void setPlayingTime(long time) {
        this.playingTime = time;
        calculatePoints();
    }

    public float getWalkingDistance() {
        return this.walkingDistance;
    }

    public void setWalkingDistance(float distance) {
        this.walkingDistance = distance;
        calculatePoints();
    }

    public int getPlayerJumps() {
        return this.playerJumps;
    }

    public void setPlayerJumps(int jumps) {
        this.playerJumps = jumps;
        calculatePoints();
    }

    public int getPlayerMoney() {
        return this.playerMoney;
    }

    public void setPlayerMoney(int money) {
        this.playerMoney = money;
        calculatePoints();
    }

    public long getPlayerPoints() {
        return this.playerPoints;
    }

    public void calculatePoints() {
        EXPRESSION.setVariable("playerKills", this.playerKills);
        EXPRESSION.setVariable("mobKills", this.mobKills);
        EXPRESSION.setVariable("playerDeaths", this.playerDeaths);
        EXPRESSION.setVariable("playingTime", this.playingTime);
        EXPRESSION.setVariable("walkingDistance", this.walkingDistance);
        EXPRESSION.setVariable("playerJumps", this.playerJumps);
        EXPRESSION.setVariable("playerMoney", this.playerMoney);

        this.playerPoints = Math.round(EXPRESSION.evaluate());
    }

    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("playerName", this.playerName);
        nbt.setInteger("playerKills", this.playerKills);
        nbt.setInteger("mobKills", this.mobKills);
        nbt.setInteger("playerDeaths", this.playerDeaths);
        nbt.setLong("playingTime", this.playingTime);
        nbt.setFloat("walkingDistance", this.walkingDistance);
        nbt.setInteger("playerJumps", this.playerJumps);
        nbt.setLong("playerPoints", this.playerPoints);
        return nbt;
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        this.playerName = nbt.getString("playerName");
        this.playerKills = nbt.getInteger("playerKills");
        this.mobKills = nbt.getInteger("mobKills");
        this.playerDeaths = nbt.getInteger("playerDeaths");
        this.playingTime = nbt.getLong("playingTime");
        this.walkingDistance = nbt.getFloat("walkingDistance");
        this.playerJumps = nbt.getInteger("playerJumps");
        this.playerPoints = nbt.getLong("playerPoints");
    }

    public static Data getPlayerData(String playerName) {
        Data data = (Data) PLAYERS_DATA.get(playerName);

        if (data == null)
            PLAYERS_DATA.put(playerName, data = new Data(playerName));

        return data;
    }

    public boolean equals(Object object) {

        if(!(object instanceof Data))
            return false;

        Data data = (Data) object;

        return this.playerName.equals(((Data) object).getPlayerName());
    }

    public int hashCode() {
        return this.playerName.hashCode();
    }

    public String toString() {
        return this.playerName;
    }
}
