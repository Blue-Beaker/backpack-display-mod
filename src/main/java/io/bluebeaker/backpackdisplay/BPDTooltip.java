package io.bluebeaker.backpackdisplay;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import io.bluebeaker.backpackdisplay.crafttweaker.CTIntegration;
import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BPDTooltip {
    static Minecraft client = Minecraft.getMinecraft();
    static FontRenderer fontRenderer = client.fontRenderer;
    static int screenWidth = 1;
    static int screenHeight = 1;
    static int mouseX = 1;

    @SubscribeEvent
    public static void pre(RenderTooltipEvent.Pre event) {
        screenWidth = event.getScreenWidth();
        screenHeight = event.getScreenHeight();
        mouseX = event.getX();
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
        List<ItemStack> items = getDisplayedItemsForItem(stack);
        if (items == null || items.size() == 0)
            return;
        renderBPDTooltip(items, event);
    }

    private static @Nullable List<ItemStack> getDisplayedItemsForItem(ItemStack stack) {
        if (stack.isEmpty())
            return null;
        List<ItemStack> items = new ArrayList<ItemStack>();

        if (Loader.isModLoaded("crafttweaker")) {
            try {
                items.addAll(CTIntegration.getItemsForCT(stack));
            } catch (Exception e) {
                BackpackDisplayMod.getLogger().error("Exception when getting display items from crafttweaker: ", e);
            }
        }

        List<IDisplaySlotEntry> entries = getRenderRules(stack);
        if (entries != null) {
            for (IDisplaySlotEntry rule : entries) {
                if (rule.isItemMatches(stack)) {
                    items.addAll(rule.getItemsFromContainer(stack));
                }
            }
        }
        if (items.size() > 0)
            return items;
        else
            return null;
    }

    private static void renderBPDTooltip(List<ItemStack> items, RenderTooltipEvent.PostText event) {
        int count = 0;
        int maxCount = BPDConfig.tooltipWidth * BPDConfig.tooltipHeight;
        int totalCount = items.size();
        // Cancel rendering when container is empty
        if (totalCount == 0)
            return;

        // Get width of tooltip
        int totalWidth = Math.min(items.size(), BPDConfig.tooltipWidth);
        // Draw label for overflowed items that takes a slot
        if (totalCount > maxCount) {
            totalCount = maxCount - 1;
        }
        // Get height of tooltip
        int totalHeight = Math.min((totalCount - 1) / BPDConfig.tooltipWidth, BPDConfig.tooltipHeight) + 1;

        int pixelWidth = totalWidth * 18;
        // int pixelHeight=totalHeight*18;

        // Set colors and positions
        int backgroundColor = BPDConfigHelper.backgroundColor;
        int borderColorStart = BPDConfigHelper.borderColorStart;
        int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | borderColorStart & 0xFF000000;

        int x = event.getX();
        int y = event.getY();

        // Upper left corner of first item to draw
        int drawX = x + BPDConfig.offset_x;
        int drawY = y + BPDConfig.offset_y - (totalHeight) * 18;
        // Move down when top out of screen
        if (drawY < 4) {
            drawY = event.getY() + event.getHeight() + 8;
        }
        // Align to right end of tooltip when right out of screen, or tooltip is at left
        // of mouse
        if (drawX + pixelWidth + 4 > screenWidth || event.getX() + event.getWidth() < mouseX) {
            drawX = event.getX() + event.getWidth() - pixelWidth;
        }

        // Draw background and extra item count label
        drawBackground(drawX, drawY, totalWidth * 18,
                totalHeight * 18, backgroundColor, borderColorStart, borderColorEnd);

        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.translate(0.0F, 0.0F, 512.0F);

        if (items.size() > totalCount) {
            drawLabelCentered(drawX + (BPDConfig.tooltipWidth - 1) * 18, drawY + (totalHeight - 1) * 18,
                    "+" + getNumRepresentation(items.size() - totalCount));
        }

        // Render every item
        for (int i = 0; i < totalCount; i++) {
            ItemStack stack2 = items.get(i);
            int slotX = count % BPDConfig.tooltipWidth;
            int slotY = count / BPDConfig.tooltipWidth;
            renderItemStack(stack2, drawX + (slotX) * 18, drawY + (slotY) * 18);
            count++;
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
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
