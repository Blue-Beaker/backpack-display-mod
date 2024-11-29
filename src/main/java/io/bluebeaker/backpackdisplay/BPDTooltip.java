package io.bluebeaker.backpackdisplay;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import io.bluebeaker.backpackdisplay.api.IDisplaySection;
import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BPDTooltip {
    static Minecraft client = Minecraft.getMinecraft();
    static FontRenderer fontRenderer = client.fontRenderer;
    private static int screenWidth = 1;
    private static int screenHeight = 1;
    private static int mouseX = 1;
    
    @SubscribeEvent
    public static void pre(RenderTooltipEvent.Pre event) {
        screenWidth = event.getScreenWidth();
        screenHeight = event.getScreenHeight();
        mouseX = event.getX();
        // BackpackDisplayMod.logInfo(event.getStack() + ":" + screenWidth + "," + screenHeight);
    }

    @SubscribeEvent
    public static void render(RenderTooltipEvent.PostText event) {
        if (BPDConfig.needs_keybind != BPDConfig.KeybindType.NOT_NEEDED) {
            if (BPDConfig.needs_keybind == BPDConfig.KeybindType.PRESSED
                    ^ Keyboard.isKeyDown(Keybind.keyShowContents.getKeyCode())) {
                return;
            }
        }

        ItemStack stack = event.getStack();

        //Workaround for AE2 GUIs which event.getStack() always returns 1xtile.air
        if(stack.isEmpty() && client.currentScreen instanceof GuiContainer){
            Slot slot=((GuiContainer)client.currentScreen).getSlotUnderMouse();
            if(slot!=null)
            stack=slot.getStack();
        }
        
        // BackpackDisplayMod.logInfo(stack + ":" + screenWidth + "," + screenHeight + "," + event.getWidth()
        //         + "," + event.getHeight());

        renderBPDTooltipFromItemStack(stack, event.getX(), event.getY(), event.getWidth(), event.getHeight());
    }
    
    public static void renderBPDTooltipFromItemStack(ItemStack stack, int x, int y, int w, int h) {
        List<IDisplaySection> sections = SectionsManager.sections;
        for(IDisplaySection section:sections){
            section.update(stack);
        }
        renderTooltip(x, y, w, h);
    }

    private static void renderTooltip(int x, int y, int w, int h) {
        List<IDisplaySection> sections = SectionsManager.sections;

        /**Height of this tooltip */
        int height=0;
        /**Width of this tooltip */
        int width=0;

        int availaleSections = 0;
        
        //Get total size
        for(IDisplaySection section:sections){
            if(section.isAvailable()){
                availaleSections+=1;
                height+=section.getHeight();
                width=Math.max(section.getWidth(), width);
            }
        }

        //Cancel when no sections available for item
        if(availaleSections==0)
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
        if (drawX + width + 4 > screenWidth || x + width < mouseX) {
            drawX = x + w - width;
        }

        // Set colors and positions
        int backgroundColor = BPDConfigHelper.backgroundColor;
        int borderColorStart = BPDConfigHelper.borderColorStart;
        int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | borderColorStart & 0xFF000000;
        // Draw background
        drawBackground(drawX, drawY, width,
                height, backgroundColor, borderColorStart, borderColorEnd);

        //Get total size
        for(IDisplaySection section:sections){
            if(section.isAvailable()){
                section.render(drawX, drawY);
            }
        }
    }

    /**
     * Render item stack for the tooltip
     * 
     * @param stack Itemstack to render
     * @param x
     * @param y
     */
    public static void renderItemStack(ItemStack stack, int x, int y) {
        client.getRenderItem().renderItemIntoGUI(stack, x, y);
        String numRep = null;
        if (stack.getCount() > 1)
            numRep = getNumRepresentation(stack.getCount());
        if (fontRenderer.getStringWidth(numRep) > 16) {
            float scale = BPDConfig.label_scale;
            drawLabelCorneredScaled(x, y, numRep, scale);
            client.getRenderItem().renderItemOverlayIntoGUI(client.fontRenderer, stack, x, y, "");
        } else {
            client.getRenderItem().renderItemOverlayIntoGUI(client.fontRenderer, stack, x, y, numRep);
        }
    }

    /**
     * Draw a text in the Corner of slot
     * 
     * @param x
     * @param y
     * @param text
     */
    public static void drawLabelCorneredScaled(int x, int y, String text, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        fontRenderer.drawStringWithShadow(text, (float) ((x + 15) / scale - fontRenderer.getStringWidth(text) + 2),
                (float) ((y + 6 + fontRenderer.FONT_HEIGHT) / scale - fontRenderer.FONT_HEIGHT + 3), 16777215);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableBlend();
        GlStateManager.popMatrix();
    }

    /**
     * Draw a text in the middle of slot
     * 
     * @param x
     * @param y
     * @param text
     */
    public static void drawLabelCentered(int x, int y, String text) {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        fontRenderer.drawStringWithShadow(text, (float) (x + 9 - fontRenderer.getStringWidth(text) / 2),
                (float) (y + 9 - fontRenderer.FONT_HEIGHT / 2), 16777215);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableBlend();
    }

    public static void drawBackground(int x, int y, int width, int height, int bgColor, int borderColorStart,
            int borderColorEnd) {
        final int zLevel = 300;
        GuiUtils.drawGradientRect(zLevel, x - 3, y - 4, x + width + 3, y - 3, bgColor, bgColor);
        GuiUtils.drawGradientRect(zLevel, x - 3, y + height + 3, x + width + 3, y + height + 4, bgColor, bgColor);
        GuiUtils.drawGradientRect(zLevel, x - 3, y - 3, x + width + 3, y + height + 3, bgColor, bgColor);
        GuiUtils.drawGradientRect(zLevel, x - 4, y - 3, x - 3, y + height + 3, bgColor, bgColor);
        GuiUtils.drawGradientRect(zLevel, x + width + 3, y - 3, x + width + 4, y + height + 3, bgColor, bgColor);
        GuiUtils.drawGradientRect(zLevel, x - 3, y - 3 + 1, x - 3 + 1, y + height + 3 - 1, borderColorStart,
                borderColorEnd);
        GuiUtils.drawGradientRect(zLevel, x + width + 2, y - 3 + 1, x + width + 3, y + height + 3 - 1, borderColorStart,
                borderColorEnd);
        GuiUtils.drawGradientRect(zLevel, x - 3, y - 3, x + width + 3, y - 3 + 1, borderColorStart, borderColorStart);
        GuiUtils.drawGradientRect(zLevel, x - 3, y + height + 2, x + width + 3, y + height + 3, borderColorEnd,
                borderColorEnd);
    }

    public static String getNumRepresentation(int number) {
        if (Math.log10(number) < BPDConfig.full_digits)
            return String.valueOf(number);
        if (number >= 1000000000) {
            return String.format("%.1fG", number / 100000000 / 10.0);
        } else if (number >= 100000000) {
            return String.format("%dM", number / 1000000);
        } else if (number >= 1000000) {
            return String.format("%.1fM", number / 100000 / 10.0);
        } else if (number >= 100000) {
            return String.format("%dk", number / 1000);
        } else if (number >= 1000) {
            return String.format("%.1fk", number / 100 / 10.0);
        } else {
            return String.valueOf(number);
        }
    }

    public static List<IDisplaySlotEntry> getRenderRules(ItemStack stack) {
        Item item = stack.getItem();
        List<IDisplaySlotEntry> entries = BPDRegistry.registry.get(item);
        return entries;
    }
}
