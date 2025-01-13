package io.bluebeaker.backpackdisplay;

import java.util.List;


import com.mojang.blaze3d.platform.InputConstants;
import io.bluebeaker.backpackdisplay.api.IDisplaySection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BPDTooltip {

    private static ItemStack lastStack;

    private static Minecraft client = Minecraft.getInstance();

    public static boolean beforeRender(){
        if (BPDConfig.needs_keybind != BPDConfig.KeybindType.NOT_NEEDED) {
            if (BPDConfig.needs_keybind == BPDConfig.KeybindType.PRESSED
                    ^ Keybind.keyShowContents.isDown()) {
                return false;
            }
        }
        return true;
    }

    public static void render(ItemStack stack, GuiGraphics guiGraphics,int x,int y,int w,int h) {

        // Workaround for AE2 GUIs which event.getStack() always returns 1xtile.air
//        if (stack.isEmpty() && client.screen instanceof AbstractContainerScreen) {
//            Slot slot = ((AbstractContainerScreen) client.screen).getSlotUnderMouse();
//            if (slot != null)
//                stack = slot.getItem();
//        }

        // BackpackDisplayMod.logInfo(stack + ":" + screenWidth + "," + screenHeight +
        // "," + event.getWidth()
        // + "," + event.getHeight());
        updateSections(stack);
        renderTooltip(stack, guiGraphics,x,y,w,h);
    }

    public static void updateSections(ItemStack stack) {
        if(ItemStack.isSameItemSameTags(stack,lastStack)){
            return;
        }
        List<IDisplaySection> sections = SectionsManager.getSections();
        for (IDisplaySection section : sections) {
            section.update(stack);
        }
        lastStack=stack.copy();
    }

    private static void renderTooltip(ItemStack stack,GuiGraphics guiGraphics, int x, int y, int w, int h) {
        List<IDisplaySection> sections = SectionsManager.getSections();

        /** Height of this tooltip */
        int height = 0;
        /** Width of this tooltip */
        int width = 0;

        int availaleSections = 0;

        // Get total size
        for (IDisplaySection section : sections) {
            if (section.isAvailable()) {
                availaleSections += 1;
                height += section.getHeight();
                width = Math.max(section.getWidth(), width);
            }
        }

        // Cancel when no sections available for item
        if (availaleSections == 0)
            return;

        // Upper left corner of first item to draw
        int drawX = x + BPDConfig.offset_x;
        int drawY = y + BPDConfig.offset_y - height;
        // Move down when top out of screen
        if (drawY < 4) {
            drawY = y + h + 8;
        }
        // Align to right end of tooltip when right out of screen, or tooltip is at left
        // of mouse
//        if (drawX + width + 4 > screenWidth || x + width < mouseX) {
//            drawX = x + w - width;
//        }

        // Set colors and positions
        int backgroundColor = BPDConfigHelper.backgroundColor;
        int borderColorStart = BPDConfigHelper.borderColorStart;
        int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | borderColorStart & 0xFF000000;
        // Draw background
        drawBackground(guiGraphics, drawX, drawY, width,
                height, backgroundColor, borderColorStart, borderColorEnd);

        // Draw every display sections
        for (IDisplaySection section : sections) {
            if (section.isAvailable()) {
                section.render(guiGraphics,drawX, drawY);
                drawY = drawY + section.getHeight();
            }
        }
    }

    /** Draws background for this tooltip */
    public static void drawBackground(GuiGraphics guiGraphics, int x, int y, int width, int height, int bgColor, int borderColorStart,
                                      int borderColorEnd) {
        final int zLevel = 300;
        guiGraphics.fillGradient(zLevel, x - 3, y - 4, x + width + 3, y - 3, bgColor, bgColor);
        guiGraphics.fillGradient(zLevel, x - 3, y + height + 3, x + width + 3, y + height + 4, bgColor, bgColor);
        guiGraphics.fillGradient(zLevel, x - 3, y - 3, x + width + 3, y + height + 3, bgColor, bgColor);
        guiGraphics.fillGradient(zLevel, x - 4, y - 3, x - 3, y + height + 3, bgColor, bgColor);
        guiGraphics.fillGradient(zLevel, x + width + 3, y - 3, x + width + 4, y + height + 3, bgColor, bgColor);
        guiGraphics.fillGradient(zLevel, x - 3, y - 3 + 1, x - 3 + 1, y + height + 3 - 1, borderColorStart,
                borderColorEnd);
        guiGraphics.fillGradient(zLevel, x + width + 2, y - 3 + 1, x + width + 3, y + height + 3 - 1, borderColorStart,
                borderColorEnd);
        guiGraphics.fillGradient(zLevel, x - 3, y - 3, x + width + 3, y - 3 + 1, borderColorStart, borderColorStart);
        guiGraphics.fillGradient(zLevel, x - 3, y + height + 2, x + width + 3, y + height + 3, borderColorEnd,
                borderColorEnd);
    }

}
