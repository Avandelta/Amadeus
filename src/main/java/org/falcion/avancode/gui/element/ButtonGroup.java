package org.falcion.avancode.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ButtonGroup {

    protected List<Item> buttons = new ArrayList<>();

    protected Item selection;

    public void Add(Item item) {

        if(item == null || this.buttons.contains(item))
            return;

        this.buttons.add(item);

        if(item.isSelected()) {

            if(this.selection == null) {
                this.selection = item;
            } else {
                item.setSelected(false);
            }
        }

        item.setGroup(null);
    }

    public void Deselect() {

        for(Item item : this.buttons) {
            item.setSelected(false);
        }
    }

    public void clearSelection() {

        if(this.selection != null) {

            Item oldSelection = this.selection;
            this.selection = null;

            oldSelection.setSelected(false);
        }
    }

    public Item getSelection() {
        return this.selection;
    }

    public List<Item> getButtons() {
        return this.buttons;
    }

    public void setSelection(Item item, boolean isSelected) {

        if(isSelected && item != null && item != this.selection) {

            Item oldSelection = this.selection;
            this.selection = item;

            if(oldSelection != null) {
                oldSelection.setSelected(false);
            }

            item.setSelected(true);
        }
    }

    public boolean isSelected(Item item) {
        return (item == this.selection);
    }

    public int getSelectionIndex() {

        if(this.selection == null)
            return -1;

        return this.buttons.indexOf(this.selection);
    }

    public static interface Item {

        boolean isSelected();

        void setSelected(boolean paramBoolean);

        ButtonGroup getGroup();

        void setGroup(ButtonGroup paramGroup);
    }
}
