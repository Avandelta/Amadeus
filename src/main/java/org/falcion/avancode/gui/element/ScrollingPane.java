package org.falcion.avancode.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.falcion.avancode.gui.element.ScrollingList.glScissor;

public class ScrollingPane extends Gui {

    public static final float DEFAULT_SCROLL_SPEED = 10.0F;

    protected final GuiScreen screen;

    protected final Minecraft minecraft;

    protected int x;
    protected int y;

    protected int width;
    protected int height;

    protected int scrollOffset;

    protected float scrollSpeed;

    protected int mouseX;
    protected int mouseY;

    protected int sliderColor;
    protected int sliderBackgroundColor;

    protected int sliderOffset;

    protected int sliderWidth;

    protected boolean drawSliderBackground;

    private boolean dragging;

    private int dragged;

    private int mouseYOffset;

    protected String text;

    public ScrollingPane(GuiScreen screen) {
        this(screen, 0, 0, 100, 50);
    }

    public ScrollingPane(GuiScreen screen, int posX, int posY, int listWidth, int listHeight) {
        this.screen = screen;
        this.minecraft = this.screen.mc;
        this.width = listWidth;
        this.height = listHeight;
        this.x = posX;
        this.y = posY;
        this.scrollSpeed = 10.0F;
        this.sliderColor = (new Color(0, 0, 0, 125)).getRGB();
        this.sliderBackgroundColor = (new Color(14, 18, 30)).getRGB();
        this.sliderOffset = 1;
        this.sliderWidth = 3;
        this.drawSliderBackground = true;
        this.text = "";
    }

    public int getSize() {
        return this.minecraft.fontRenderer.getWordWrappedHeight(this.text, this.width - 7) - 9;
    }

    public int getContentSize() {
        return getSize() + 2;
    }

    public void handleMouse() {
        if(isMouseOver()) {

            int delta = Mouse.getDWheel();

            if(delta != 0) {

                if(delta > 0) {
                    delta = -1;
                } else if(delta < 0) {
                    delta = 1;
                }

                int maxScrollOffset = Math.max(0, getContentSize() - this.height);
                this.scrollOffset = (int) Math.max(Math.min(this.scrollOffset + delta * this.scrollSpeed, maxScrollOffset), 0.0F);
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float ticks) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        GL11.glEnable(3089);
        glScissor(this.x, this.y, this.width, this.height);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -this.scrollOffset, 0.0F);

        this.minecraft.fontRenderer.drawSplitString(this.text, this.x + 5, this.y + 1, this.width - 7, -1);

        GL11.glPopMatrix();
        GL11.glDisable(3089);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);

        int start = getContentSize() - this.height;

        if (start > 0) {

            int length = this.height * this.height / getContentSize();

            if (length < 8)
                length = 8;

            if (length > this.height - 8)
                length = this.height - 8;

            int end = this.scrollOffset * (this.height - length) / start + this.y;

            if (end < this.y)
                end = this.y;

            int scrollBarXStart = this.x + this.width + this.sliderOffset;
            int scrollBarXEnd = scrollBarXStart + this.sliderWidth;

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (this.drawSliderBackground)
                drawRect(scrollBarXStart, this.y, scrollBarXEnd, this.y + this.height, this.sliderBackgroundColor);

            drawRect(scrollBarXStart, end + length, scrollBarXEnd, end, this.sliderColor);
        }

        GL11.glEnable(3008);
        GL11.glDisable(3042);
    }

    public void mouseClickMove(int mouseX, int mouseY, int button) {
        if (this.dragging) {

            this.scrollOffset += (mouseY - this.mouseYOffset) * getContentSize() / this.height;

            if (this.scrollOffset > getContentSize() - this.height)
                this.scrollOffset = getContentSize() - this.height;

            if (this.scrollOffset < 0)
                this.scrollOffset = 0;

            this.mouseYOffset = mouseY;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        int start = getContentSize() - this.height;

        if (start > 0) {

            int scrollBarXStart = this.x + this.width + this.sliderOffset;
            int scrollBarXEnd = scrollBarXStart + this.sliderWidth;

            int length = this.height * this.height / getContentSize();

            if (length < 8)
                length = 8;

            if (length > this.height - 8)
                length = this.height - 8;

            int end = this.scrollOffset * (this.height - length) / start + this.y;

            if (end < this.y)
                end = this.y;

            if (mouseX > scrollBarXStart && mouseY >= end && mouseX < scrollBarXEnd && mouseY < end + length) {
                this.dragging = true;
                this.mouseYOffset = mouseY;
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int button) {
        this.dragging = false;
    }

    public void cleanUp() {
        this.scrollOffset = 0;
        this.text = "";
    }

    public boolean isMouseOver() {
        return (this.mouseX >= this.x && this.mouseX < this.x + this.width && this.mouseY >= this.y && this.mouseY < this.y + this.height);
    }

    public String getText() {
        return this.text;
    }

    public void setText(String lines) {
        cleanUp();
        this.text = lines;
    }

    public void setText(String... lines) {
        cleanUp();
        for (String line : lines)
            this.text += line + "\n";
    }

    public float getScrollSpeed() {
        return this.scrollSpeed;
    }

    public ScrollingPane setScrollSpeed(float speed) {
        this.scrollSpeed = speed;
        return this;
    }

    public int getSliderColor() {
        return this.sliderColor;
    }

    public ScrollingPane setSliderColor(int color) {
        this.sliderColor = color;
        return this;
    }

    public boolean isDrawSliderBackground() {
        return this.drawSliderBackground;
    }

    public ScrollingPane setDrawSliderBackground(boolean draw) {
        this.drawSliderBackground = draw;
        return this;
    }

    public int getSliderBackgroundColor() {
        return this.sliderBackgroundColor;
    }

    public ScrollingPane setSliderBackgroundColor(int color) {
        this.sliderBackgroundColor = color;
        return this;
    }

    public int getSliderOffset() {
        return this.sliderOffset;
    }

    public ScrollingPane setSliderOffset(int offset) {
        this.sliderOffset = offset;
        return this;
    }

    public int getSliderWidth() {
        return this.sliderWidth;
    }

    public ScrollingPane setSliderWidth(int width) {
        this.sliderWidth = width;
        return this;
    }

    public int getScrollOffset() {
        return this.scrollOffset;
    }

    public void setScrollOffset(int offset) {
        this.scrollOffset = offset;
    }

    public GuiScreen getParent() {
        return this.screen;
    }

    public void setPosition(int xPos, int yPos) {
        this.x = xPos;
        this.y = yPos;
    }

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;

        if (this.scrollOffset > getContentSize())
            this.scrollOffset = getContentSize();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }

    @SideOnly(Side.CLIENT)
    public static void glScissor(int x, int y, int width, int height) {
        Minecraft minecraft = Minecraft.getMinecraft();
        ScaledResolution resolution = new ScaledResolution(minecraft);
        int scale = resolution.getScaleFactor();
        int scissorWidth = width * scale;
        int scissorHeight = height * scale;
        int scissorX = x * scale;
        int scissorY = minecraft.displayHeight - scissorHeight - y * scale;
        GL11.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
    }
}
