package io.bluebeaker.backpackdisplay;

import io.bluebeaker.backpackdisplay.api.IDisplaySection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import java.util.List;

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
        // BackpackDisplayMod.logInfo(event.getStack() + ":" + screenWidth + "," +
        // screenHeight);
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
        // Workaround for AE2 GUIs which event.getStack() always returns 1xtile.air
        if (stack.isEmpty() && client.currentScreen instanceof GuiContainer) {
            Slot slot = ((GuiContainer) client.currentScreen).getSlotUnderMouse();
            if (slot != null)
                stack = slot.getStack();
        }

        // BackpackDisplayMod.logInfo(stack + ":" + screenWidth + "," + screenHeight +
        // "," + event.getWidth()
        // + "," + event.getHeight());

        renderBPDTooltipFromItemStack(stack, event.getX(), event.getY(), event.getWidth(), event.getHeight());
    }

    public static void renderBPDTooltipFromItemStack(@Nonnull ItemStack stack, int x, int y, int w, int h) {
        List<IDisplaySection> sections = SectionsManager.getSections();
        for (IDisplaySection section : sections) {
            section.update(stack);
        }
        renderTooltip(x, y, w, h);
    }

    private static void renderTooltip(int x, int y, int w, int h) {
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
        int drawX = x + BPDConfig.offset_x;
        int drawY = y + BPDConfig.offset_y - height;
        // Move down when top out of screen
        if (drawY < 4) {
            drawY = y + h + 8;
        }
        // Align to right end of tooltip when right out of screen, or tooltip is at left
        // of mouse
        if(drawX + width + 4 > screenWidth){
            drawX = screenWidth - 4 - width;
        } else if (x + width < mouseX) {
            drawX = x + w - width;
        }

        // Set colors and positions
        int backgroundColor = BPDConfigHelper.backgroundColor;
        int borderColorStart = BPDConfigHelper.borderColorStart;
        int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | borderColorStart & 0xFF000000;
        // Draw background
        drawBackground(drawX, drawY, width,
                height, backgroundColor, borderColorStart, borderColorEnd);

        // Draw every display sections
        for (IDisplaySection section : sections) {
            if (section.isAvailable()) {
                section.render(drawX, drawY);
                drawY = drawY + section.getHeight();
            }
        }
    }

    /** Draws background for this tooltip */
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

}
