package io.bluebeaker.backpackdisplay;

import java.util.ArrayList;
import java.util.List;

import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BPDTooltip {
    static Minecraft client = Minecraft.getMinecraft();
    static FontRenderer fontRenderer = client.fontRenderer;

    @SubscribeEvent
    public static void render(RenderTooltipEvent.PostText event) {
        ItemStack stack = event.getStack();
        if (stack.isEmpty())
            return;
        List<IDisplaySlotEntry> entries = getRenderRules(stack);
        if (entries == null)
            return;
        List<ItemStack> items = new ArrayList<ItemStack>();
        for (IDisplaySlotEntry rule : entries) {
            if (rule.isItemMatches(stack)) {
                items.addAll(rule.getItemsFromContainer(stack));
            }
        }
        renderBPDTooltip(items, event.getX(), event.getY());
    }

    private static void renderBPDTooltip(List<ItemStack> items, int x, int y) {
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        int count = 0;
        int maxCount = BPDConfig.tooltipWidth * BPDConfig.tooltipHeight;
        int totalCount = items.size();
        // Cancel rendering when container is empty
        if (totalCount == 0)
            return;

        int totalWidth = Math.min(items.size(), BPDConfig.tooltipWidth);
        // Draw label for overflowed items
        if (totalCount > maxCount) {
            totalCount = maxCount - 1;
        }
        int totalHeight = Math.min((totalCount - 1) / BPDConfig.tooltipWidth, BPDConfig.tooltipHeight) + 1;

        int backgroundColor = BPDConfig.backgroundColor;
        int borderColorStart = BPDConfig.borderColorStart;
        int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | borderColorStart & 0xFF000000;

        drawBackground(x + BPDConfig.offset_x, y + BPDConfig.offset_y - (totalHeight) * 18, totalWidth * 18,
                totalHeight * 18, backgroundColor, borderColorStart, borderColorEnd);
        GlStateManager.translate(0.0F, 0.0F, 512.0F);
        if (items.size() > totalCount) {
            drawLabelCentered(x + BPDConfig.offset_x + (BPDConfig.tooltipWidth - 1) * 18, y - 18 + BPDConfig.offset_y,
                    "+" + getNumRepresentation(items.size() - totalCount));
        }
        // Render every item
        for (int i = 0; i < totalCount; i++) {
            ItemStack stack2 = items.get(i);
            int slotX = count % BPDConfig.tooltipWidth;
            int slotY = (count / BPDConfig.tooltipWidth) - totalHeight;
            renderItemStack(stack2, x + BPDConfig.offset_x + (slotX) * 18, y + BPDConfig.offset_y + (slotY) * 18);
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
                (float) ((y + 6 +fontRenderer.FONT_HEIGHT) / scale-fontRenderer.FONT_HEIGHT + 3), 16777215);
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
