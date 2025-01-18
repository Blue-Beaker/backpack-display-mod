package io.bluebeaker.backpackdisplay;

import io.bluebeaker.backpackdisplay.api.IDisplaySection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BPDTooltip {

    private static int screenWidth;
    private static int mouseX;

    private static ItemStack lastStack = ItemStack.EMPTY;

    private static Minecraft client = Minecraft.getInstance();

    public static boolean beforeRender(){
        if (ConfigProvider.getConfig().keybindRequirement != BPDConfig.KeybindType.NOT_NEEDED) {
            if (ConfigProvider.getConfig().keybindRequirement == BPDConfig.KeybindType.PRESSED
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
        if(stack == null || ItemStack.isSameItemSameTags(stack, lastStack)){
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

        // Height of this tooltip
        int height = 0;
        // Width of this tooltip
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
        int drawX = x + ConfigProvider.getConfig().appearance.offset_x;
        int drawY = y + ConfigProvider.getConfig().appearance.offset_y - height;
        // Move down when top out of screen
        if (drawY < 4) {
            drawY = y + h + 8;
        }
        // Align to right end of tooltip when right out of screen, or tooltip is at left
        // of mouse
        if(drawX + width + 4 > screenWidth){
            drawX = screenWidth - width - 4;
        } else if (x + width < mouseX) {
            drawX = x + w - width;
        }

        guiGraphics.pose().pushPose();
        // Set colors and positions
        int backgroundColor = BPDConfigHelper.backgroundColor;
        int borderColorStart = BPDConfigHelper.borderColorStart;
        int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | borderColorStart & 0xFF000000;
        // Draw background
        drawBackground(guiGraphics, drawX, drawY, width,
                height, backgroundColor, borderColorStart, borderColorEnd);

        guiGraphics.pose().translate(0,0,399);
        // Draw every display sections
        for (IDisplaySection section : sections) {
            if (section.isAvailable()) {
                section.render(guiGraphics,drawX, drawY);
                drawY = drawY + section.getHeight();
            }
        }
        guiGraphics.pose().popPose();
    }

    /** Draws background for this tooltip */
    public static void drawBackground(GuiGraphics guiGraphics, int x, int y, int width, int height, int bgColor, int borderColorStart,
                                      int borderColorEnd) {
        int bgX = x - 3;
        int bgY = y - 3;
        int bgW = width + 6;
        int bgH = height + 6;
        int z = 400;

        guiGraphics.fill(bgX, bgY - 1, bgX + bgW, bgY , z, bgColor);
        guiGraphics.fill(bgX, bgY + bgH, bgX + bgW, bgY + bgH + 1, z, bgColor);
        guiGraphics.fill(bgX, bgY, bgX + bgW, bgY + bgH, z, bgColor);
        guiGraphics.fill(bgX - 1, bgY, bgX , bgY + bgH, z, bgColor);
        guiGraphics.fill(bgX + bgW, bgY, bgX + bgW + 1, bgY + bgH, z, bgColor);

        guiGraphics.fillGradient(bgX, bgY + 1, bgX + 1, bgY + 1 + bgH - 2, z, borderColorStart, borderColorEnd);
        guiGraphics.fillGradient(bgX + bgW - 1, bgY + 1, bgX + bgW , bgY + 1 + bgH - 2, z, borderColorStart, borderColorEnd);
        guiGraphics.fill(bgX, bgY , bgX + bgW, bgY  + 1, z, borderColorStart);
        guiGraphics.fill(bgX, bgY  + bgH - 1, bgX + bgW, bgY  + bgH , z, borderColorEnd);

    }

    public static void updateParams(int screenWidth, int mouseX) {
        BPDTooltip.screenWidth = screenWidth;
        BPDTooltip.mouseX = mouseX;
    }
}
