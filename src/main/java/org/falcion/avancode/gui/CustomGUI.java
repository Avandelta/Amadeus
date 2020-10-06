package org.falcion.avancode.gui;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.falcion.avancode.Avancode;
import org.falcion.avancode.Configuration;
import org.falcion.avancode.gui.element.*;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CustomGUI extends GuiScreen {
    public static Map<String, List<Triple<Integer, String, String>>> tableValues = Maps.newHashMap();

    public static Map<String, List<Pair<String, Integer>>> tableWinners = Maps.newHashMap();

    public static Map<String, Triple<Integer, String, String>> currentPlayer = Maps.newHashMap();

    private CustomButton closeButton;
    private CustomButton[] buttonTab;

    private ButtonGroup tabGroup;

    private ScrollingList listTop;
    private ScrollingList listWinners;

    private ScrollingPane paneDescription;

    private TextField searchField;

    private Configuration.Table[] tables;

    private boolean isInitialized;

    public boolean searching;

    private int searchDelay;

    private String[] progress = new String[] { ".", "..", "..." };

    public CustomGUI() {
        this.tables = Avancode.config.getTables();
    }

    public void initialize() {
        this.buttonList.clear();
        this.buttonList.add(this.closeButton = (new CustomButton(0, I18n.format("gui.top.close", new Object[0]))).setButtonColor(color(183, 34, 62)).setHoverButtonColor(color(170, 41, 65)));

        this.tabGroup = new ButtonGroup();

        this.buttonTab = new CustomButton[this.tables.length];

        for (int i = 0; i < this.buttonTab.length; i++)
            this.buttonList.add(this.buttonTab[i] = (new CustomButton(1 + i, this.tables[i].getNameTab())).setButtonGroup(this.tabGroup).setButtonColor(color(27, 34, 52)).setHoverButtonColor(color(110, 85, 255)).setSelectedButtonColor(color(94, 85, 255)));

        this.tabGroup.setSelection((ButtonGroup.Item)this.buttonTab[0], true);

        this.listTop = (new ScrollingList(this, 20)).setSliderColor(color(54, 50, 144)).setSliderWidth(6).setDrawHoverColor(false).setDrawSelectedColor(false);
        this.listTop.setElements(tableValues.get(getCurrentTable().getDatabaseTable()));

        this.listWinners = (new GuiScrollingListLastWinners(this, 20)).setSliderColor(color(54, 50, 144)).setSliderWidth(6).setDrawHoverColor(false).setDrawSelectedColor(false);
        this.listWinners.setElements(tableWinners.getOrDefault(getCurrentTable().getDatabaseTable(), Collections.EMPTY_LIST));

        this

                .paneDescription = (new ScrollingPane(this) {
            public void drawScreen(int mouseX, int mouseY, float ticks) {
                CustomGUI.drawRectangle(this.x, this.y, this.width, this.height, CustomGUI.color(19, 25, 42));
                super.drawScreen(mouseX, mouseY, ticks);
            }
        }).setSliderColor(color(54, 50, 144)).setSliderWidth(6);

        this.paneDescription.setText(getCurrentTable().getTableDesc());

        this.searchField = new TextField(this.fontRenderer, 0, 0, 100, 16) {
            public void drawBackground() {
                CustomGUI.drawRectangle(this.xPosition, this.yPosition, this.width, this.height, CustomGUI.color(15, 21, 35));
            }
        };

        this.searchField.setMaxStringLength(64);
        this.searchField.setMnemonicText(I18n.format("gui.top.textField.search", new Object[0]));
    }

    public void resize() {

        this.closeButton.setPosition(this.width - 100, 7);
        this.closeButton.setSize(60, 16);

        int space = 4;
        int count = this.tables.length;

        int startX = 40;
        int endX = this.width - 40;

        int buttonWidth = MathHelper.abs(((endX - startX) / count)) - space + 1;

        for (int i = 0; i < this.buttonTab.length; i++) {
            this.buttonTab[i].setPosition(startX + i * (buttonWidth + space), 40);
            this.buttonTab[i].setSize(buttonWidth, 20);
        }

        this.listTop.setPosition(startX, 105);
        this.listTop.setSize(endX - startX - this.listTop.getSliderWidth(), this.height - 194);

        this.paneDescription.setPosition(startX, this.height - 78);
        this.paneDescription.setSize((!getCurrentTable().isShowWinners() || this.listWinners.getElements().isEmpty()) ? this.listTop.getWidth() : ((endX - startX) / 2 - 5), 83);

        this.listWinners.setPosition(this.paneDescription.getX() + this.paneDescription.getWidth() + 15, this.height - 70);
        this.listWinners.setSize((endX - startX) / 2 - 15, 53);

        this.searchField.xPosition = this.listTop.getX() + this.listTop.getWidth() - this.searchField.width - 2;
        this.searchField.yPosition = this.listTop.getY() - 39;
    }

    public void func_73866_w_() {
        if (!this.isInitialized) {
            initialize();
            this.isInitialized = true;
        }
        resize();
    }

    public void func_146280_a(Minecraft client, int w, int h) {
        this.mc = client;
        this.fontRenderer = client.fontRenderer;
        this.width = w;
        this.height = h;

        if (!MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.InitGuiEvent.Pre(this, this.buttonList)))
            func_73866_w_();

        MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.InitGuiEvent.Post(this, this.buttonList));
    }

    public void func_146284_a(CustomButton button) {
        if (button.id == this.closeButton.id) {
            this.buttonList.clear();
        } else if (button.width >= 1 && button.height <= this.buttonTab.length) {
            if (getCurrentTable().isEnableSummarize())
                this.paneDescription.setText(getCurrentTable().getTableDesc());

            this.listTop.setScrollOffset(0);

            if (!this.searchField.getText().isEmpty()) {
                this.searching = true;
                this.searchDelay = Avancode.config.getDelay();
                this.listTop.setScrollOffset(0);
                this.listTop.clear();
            } else {
                this.listTop.setElements(tableValues.get(getCurrentTable().getDatabaseTable()));
            }
            if (getCurrentTable().isShowWinners() && getCurrentTable().isEnableSummarize()) {
                this.listWinners.setScrollOffset(0);

                List<Pair<String, Integer>> elements = tableWinners.get(getCurrentTable().getDatabaseTable());

                this.listWinners.setElements(new ArrayList<>(elements));
            }
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float ticks) {
        drawRectangle(0, 0, this.width, this.height, color(16, 21, 35));
        drawRectangle(0, 0, this.width, 30, color(19, 25, 42));

        GL11.glEnable(3042);
        OpenGlHelper.glFramebufferRenderbuffer(770, 771, 1, 0);

        String tableName = getCurrentTable().getNameTable();

        List<String> list1 = this.mc.renderEngine.(tableName, this.field_146294_l - 185 - 40);
        int yPos = this.field_146297_k.field_71466_p.func_78267_b(tableName, this.field_146294_l - 185 - 40);
        for (int i = 0; i < list1.size(); i++)
            drawStringWithScale(this.field_146289_q, list1.get(i), 40.0F, (0 - yPos / 2 + i * this.field_146297_k.field_71466_p.field_78288_b), 1.3F, color(41, 178, 98));
        if (getCurrentTable().isEnableSummarizing())
            func_73731_b(this.field_146289_q, I18n.func_135052_a("gui.top.label.info", new Object[0]), 40, this.field_146295_m - 88, -1);
        this.listTop.drawScreen(mouseX, mouseY, ticks);
        if (getCurrentTable().isShowLastWinners() && !this.listLastWinners.getElements().isEmpty() && getCurrentTable().isEnableSummarizing()) {
            func_73731_b(this.field_146289_q, I18n.func_135052_a("gui.top.label.lastWinners", new Object[0]), this.field_146294_l / 2 + 11, this.field_146295_m - 88, -1);
            this.listLastWinners.drawScreen(mouseX, mouseY, ticks);
        }
        if (getCurrentTable().isEnableSummarizing())
            this.paneDescription.drawScreen(mouseX, mouseY, ticks);
        this.textFieldSearch.drawTextBox();
        if (this.searching && this.searchDelay > 0) {
            String msg = I18n.func_135052_a(", new Object[0]) + this.progress[this.searchDelay / 6 % this.progress.length];
                    drawStringWithScale(this.field_146289_q, msg, ((this.listTop.getX() + this.listTop.getWidth()) / 2 - this.field_146289_q.func_78256_a(msg) / 2), ((this.listTop.getY() + this.listTop.getHeight()) / 2), 2.0F, color(200, 0, 0));
        } else if (!this.searching && this.listTop.getElements().isEmpty() && !this.textFieldSearch.getText().isEmpty()) {
            String msg = I18n.func_135052_a("gui.top.label.searchNotFound", new Object[0]);
            drawStringWithScale(this.field_146289_q, msg, ((this.listTop.getX() + this.listTop.getWidth()) / 2 - this.field_146289_q.func_78256_a(msg) / 2), ((this.listTop.getY() + this.listTop.getHeight()) / 2), 2.0F, color(200, 0, 0));
        }
        GL11.glDisable(3042);
        super.func_73863_a(mouseX, mouseY, ticks);
    }

    public void func_146281_b() {
        this.isInitialized = false;
    }

    public void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        this.listTop.mouseClicked(mouseX, mouseY, mouseButton);
        if (getCurrentTable().isShowLastWinners() && !this.listLastWinners.getElements().isEmpty() && getCurrentTable().isEnableSummarizing())
            this.listLastWinners.mouseClicked(mouseX, mouseY, mouseButton);
        this.textFieldSearch.mouseClicked(mouseX, mouseY, mouseButton);
        this.paneDescription.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void func_73869_a(char c, int i) {
        super.func_73869_a(c, i);
        if (this.textFieldSearch.textboxKeyTyped(c, i))
            if (!this.textFieldSearch.getText().isEmpty()) {
                this.searching = true;
                this.searchDelay = MyStatisticMod.config.getSearchDelay();
                this.listTop.setScrollOffset(0);
                this.listTop.clear();
            } else {
                this.searchDelay = 0;
            }
    }

    public void func_146286_b(int mouseX, int mouseY, int mouseButton) {
        super.func_146286_b(mouseX, mouseY, mouseButton);
        this.paneDescription.mouseReleased(mouseX, mouseY, mouseButton);
        this.listTop.mouseReleased(mouseX, mouseY, mouseButton);
        this.listLastWinners.mouseReleased(mouseX, mouseY, mouseButton);
    }

    public void func_146273_a(int mouseX, int mouseY, int mouseButton, long lastMouseEvent) {
        this.paneDescription.mouseClickMove(mouseX, mouseY, mouseButton);
        this.listTop.mouseClickMove(mouseX, mouseY, mouseButton);
        this.listLastWinners.mouseClickMove(mouseX, mouseY, mouseButton);
    }

    public void func_146274_d() {
        super.func_146274_d();
        this.listTop.handleMouseInput();
        if (getCurrentTable().isShowLastWinners() && !this.listLastWinners.getElements().isEmpty() && getCurrentTable().isEnableSummarizing())
            this.listLastWinners.handleMouseInput();
        if (getCurrentTable().isEnableSummarizing())
            this.paneDescription.handleMouseInput();
    }

    public void func_73876_c() {
        this.listTop.updateScreen();
        if (getCurrentTable().isShowLastWinners() && !this.listLastWinners.getElements().isEmpty() && getCurrentTable().isEnableSummarizing())
            this.listLastWinners.updateScreen();
        this.paneDescription.setSize((!getCurrentTable().isShowLastWinners() || this.listLastWinners.getElements().isEmpty()) ? this.listTop.getWidth() : ((this.field_146294_l - 40 - 40) / 2 - 5), 74);
        this.listLastWinners.setPosition(this.paneDescription.getX() + this.paneDescription.getWidth() + 15, this.field_146295_m - 57);
        this.listTop.setSize(this.field_146294_l - 40 - 40 - this.listTop.getSliderWidth(), getCurrentTable().isEnableSummarizing() ? (this.field_146295_m - 194) : (this.field_146295_m - 115));
        this.textFieldSearch.updateCursorCounter();
        if (this.searching && --this.searchDelay <= 0)
            search();
    }

    public void search() {
        if (!this.textFieldSearch.getText().isEmpty()) {
            FMLProxyPacket packet = MyStatisticMod.network.createPacket((byte)2, (Object[])new String[] { getCurrentTable().getDatabaseTable(), this.textFieldSearch.getText() });
            MyStatisticMod.network.sendToServer(packet);
        } else {
            this.listTop.setElements(tableValues.get(getCurrentTable().getDatabaseTable()));
        }
    }

    public boolean func_73868_f() {
        return false;
    }

    public void filterRows() {
        search();
    }

    public static void fillSearch(List<Triple<Integer, String, String>> values) {
        Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71462_r instanceof GuiScreenTop) {
            GuiScreenTop gui = (GuiScreenTop)mc.field_71462_r;
            gui.listTop.setElements(values);
            gui.searching = false;
            gui.searchDelay = 0;
        }
    }

    public static void fillTables(Map<String, List<Triple<Integer, String, String>>> values) {
        tableValues = values;
        Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71462_r instanceof GuiScreenTop) {
            GuiScreenTop gui = (GuiScreenTop)mc.field_71462_r;
            if (!gui.searching)
                gui.filterRows();
        }
    }

    public static void fillLastWinners(Map<String, List<Pair<String, Integer>>> values) {
        tableLastWinners = values;
        Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71462_r instanceof GuiScreenTop) {
            GuiScreenTop gui = (GuiScreenTop)mc.field_71462_r;
            if (gui.getCurrentTable().isShowLastWinners() && gui.getCurrentTable().isEnableSummarizing()) {
                List<Pair<String, Integer>> elements = tableLastWinners.get(gui.getCurrentTable().getDatabaseTable());
                gui.listLastWinners.setElements(new ArrayList<>(elements));
            }
        }
    }

    public static void fillCurrentPlayer(Map<String, Triple<Integer, String, String>> values) {
        currentPlayer = values;
    }

    public Configuration.Table getCurrentTable() {
        return this.tables[this.tabGroup.getSelectionIndex()];
    }

    public static int color(int r, int g, int b) {
        return color(r, g, b, 255);
    }

    public static int color(int r, int g, int b, int a) {
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF) << 0;
    }

    public static void drawStringWithScale(FontRenderer font, String line, float x, float y, float scale, int color) {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x / scale, y / scale + font.field_78288_b * scale, 0.0F);
        font.func_78276_b(line, 0, 0, color);
        GL11.glPopMatrix();
    }

    public static void drawRectangle(double x, double y, double width, double height, int color) {
        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.field_78398_a;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glColor4f(r, g, b, a);
        tessellator.func_78382_b();
        tessellator.func_78377_a(x, y, 0.0D);
        tessellator.func_78377_a(x, y + height, 0.0D);
        tessellator.func_78377_a(x + width, y + height, 0.0D);
        tessellator.func_78377_a(x + width, y, 0.0D);
        tessellator.func_78381_a();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }

    public class GuiScrollingListTop extends GuiScrollingList<Triple<Integer, String, String>> {
        public GuiScrollingListTop(GuiScreen screen, int listEntryHeight) {
            super(screen, listEntryHeight);
        }

        public void drawScreen(int mouseX, int mouseY, float ticks) {
            int space = 1;
            String[] headers = { "gui.top.column.index", "gui.top.column.playerName", "gui.top.column." + GuiScreenTop.this.getCurrentTable().getDatabaseTable() + ".points" };
            GuiScreenTop.drawRectangle(this.x, (this.y - 42), (this.width / 6), (this.entryHeight * 2), GuiScreenTop.color(27, 34, 52));
            GuiScreenTop.drawRectangle((this.x + this.width / 6 + space), (this.y - 42), (this.width / 3), (this.entryHeight * 2), GuiScreenTop.color(27, 34, 52));
            GuiScreenTop.drawRectangle((this.x + this.width / 6 + this.width / 3 + space * 2), (this.y - 42), (this.width / 2), (this.entryHeight * 2), GuiScreenTop.color(27, 34, 52));
            func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a(headers[0], new Object[0]), this.x + 10, this.y - 42 + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, GuiScreenTop.color(98, 105, 121));
            func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a(headers[1], new Object[0]), this.x + 10 + this.width / 6 + space, this.y - 42 + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, GuiScreenTop.color(98, 105, 121));
            func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a(headers[2], new Object[0]), this.x + 10 + this.width / 6 + this.width / 3 + space * 2, this.y - 42 + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, GuiScreenTop.color(98, 105, 121));
            Triple<Integer, String, String> currPlr = GuiScreenTop.currentPlayer.get(GuiScreenTop.this.getCurrentTable().getDatabaseTable());
            if (currPlr != null) {
                func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a(String.valueOf(currPlr.getLeft()), new Object[0]), this.x + 10, this.y - 21 + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, GuiScreenTop.color(98, 105, 121));
                func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a((String)currPlr.getMiddle(), new Object[0]), this.x + 10 + this.width / 6 + space, this.y - 21 + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, GuiScreenTop.color(98, 105, 121));
                func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a((String)currPlr.getRight(), new Object[0]), this.x + 10 + this.width / 6 + this.width / 3 + space * 2, this.y - 21 + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, GuiScreenTop.color(98, 105, 121));
            }
            super.drawScreen(mouseX, mouseY, ticks);
        }

        public void drawEntry(Triple<Integer, String, String> entry, int index, int x, int y, boolean hovered) {
            int space = 1;
            int[] colorTable = GuiScreenTop.this.getCurrentTable().getTopLineColor();
            int color = hovered ? GuiScreenTop.color(21, 28, 46) : ((index < colorTable.length) ? colorTable[index] : GuiScreenTop.color(19, 25, 42));
            GuiScreenTop.drawRectangle(x, y, (this.width / 6), this.entryHeight, color);
            GuiScreenTop.drawRectangle((x + this.width / 6 + space), y, (this.width / 3), this.entryHeight, color);
            GuiScreenTop.drawRectangle((x + this.width / 6 + this.width / 3 + space * 2), y, (this.width / 2), this.entryHeight, color);
            func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a("gui.top.column1", new Object[] { entry.getLeft() }), x + 10, y + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, -1);
            func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a("gui.top.column2", new Object[] { entry.getMiddle() }), x + 10 + this.width / 6 + space, y + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, -1);
            func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a("gui.top.column3", new Object[] { entry.getRight() }), x + 10 + this.width / 6 + this.width / 3 + space * 2, y + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, -1);
        }
    }

    public class GuiScrollingListLastWinners extends ScrollingList<Pair<String, String>> {
        public GuiScrollingListLastWinners(GuiScreen screen, int listEntryHeight) {
            super(screen, listEntryHeight);
        }

        public void drawScreen(int mouseX, int mouseY, float ticks) {
            int space = 1;
            String[] headers = { "gui.top.column.index", "gui.top.column.playerName", "gui.top.column.reward" };
            CustomGUI.drawRectangle(this.x, (this.y - 21), (this.width / 5), this.entryHeight, CustomGUI.color(27, 34, 52));
            CustomGUI.drawRectangle((this.x + this.width / 5 + space), (this.y - 21), (this.width / 2), this.entryHeight, CustomGUI.color(27, 34, 52));
            CustomGUI.drawRectangle((this.x + this.width / 5 + this.width / 2 + space * 2), (this.y - 21), this.width / 3.35D, this.entryHeight, GuiScreenTop.color(27, 34, 52));
            func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a(headers[0], new Object[0]), this.x + 5, this.y - 21 + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, GuiScreenTop.color(98, 105, 121));
            func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a(headers[1], new Object[0]), this.x + 5 + this.width / 5 + space, this.y - 21 + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, GuiScreenTop.color(98, 105, 121));
            func_73731_b(this.parent.field_146297_k.field_71466_p, I18n.func_135052_a(headers[2], new Object[0]), this.x + 5 + this.width / 5 + this.width / 2 + space * 2, this.y - 21 + this.parent.field_146297_k.field_71466_p.field_78288_b / 2 + 2, GuiScreenTop.color(98, 105, 121));
            super.drawScreen(mouseX, mouseY, ticks);
        }

        public void drawEntry(Pair<String, String> entry, int index, int x, int y, boolean hovered) {
            int space = 1;
            int color = hovered ? CustomGUI.color(21, 28, 46) : CustomGUI.color(19, 25, 42);
            CustomGUI.drawRectangle(x, y, (this.width / 5), this.entryHeight, color);
            CustomGUI.drawRectangle((x + this.width / 5 + space), y, (this.width / 2), this.entryHeight, color);
            CustomGUI.drawRectangle((x + this.width / 5 + this.width / 2 + space * 2), y, this.width / 3.35D, this.entryHeight, color);
            drawString(this.screen.mc.fontRenderer, I18n.format("gui.top.lastWinners.column1", new Object[] { String.valueOf(index + 1) }), x + 5, y + this.screen.mc.currentScreen.height / 2 + 2, -1);
            drawString(this.screen.mc.fontRenderer, I18n.format("gui.top.lastWinners.column2", new Object[] { entry.getLeft() }), x + 5 + this.width / 5 + space, y + this.screen.mc.currentScreen.height / 2 + 2, -1);
            drawString(this.screen.mc.fontRenderer, I18n.format("gui.top.lastWinners.column3", new Object[] { entry.getRight() }), x + 5 + this.width / 5 + this.width / 2 + space * 2, y + this.screen.mc.currentScreen.height / 2 + 2, -1);
        }
    }
}
