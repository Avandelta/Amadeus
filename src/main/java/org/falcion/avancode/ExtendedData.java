package org.falcion.avancode;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class ExtendedData {

    private final File file;

    public LocalDate clearPlayingTime = LocalDate.of(1999, 4, 6);

    public LocalDate lastSummarize = LocalDate.of(1999, 4, 6);

    public ExtendedData(File fileContext) {
        this.file = fileContext;
    }

    public void Save() {
        try (FileOutputStream out = new FileOutputStream(this.file)) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setLong("lastClearPlayingTime", this.clearPlayingTime.toEpochDay());
            tag.setLong("lastSummarizing", this.lastSummarize.toEpochDay());
            CompressedStreamTools.writeCompressed(tag, out);
        } catch (IOException exception) {
            Avancode.LOGGER.info("Failed to save statistics extended data: " + exception.toString());
        }
    }

    public void Load() {
        if (!this.file.exists())
            return;
        try (FileInputStream in = new FileInputStream(this.file)) {
            NBTTagCompound tag = CompressedStreamTools.readCompressed(in);
            this.clearPlayingTime = LocalDate.ofEpochDay(tag.getLong("lastClearPlayingTime"));
            this.lastSummarize = LocalDate.ofEpochDay(tag.getLong("lastSummarizing"));
        } catch (IOException exception) {
            Avancode.LOGGER.info("Failed to load statistics extended data: " + exception.toString());
        }
    }
}