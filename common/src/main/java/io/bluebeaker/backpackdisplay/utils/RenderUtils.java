package io.bluebeaker.backpackdisplay.utils;

import io.bluebeaker.backpackdisplay.BPDConfig;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import dev.architectury.fluid.FluidStack;

public class RenderUtils {

    static Minecraft client = Minecraft.getInstance();
    static Font font = client.font;

    /**
     * Draw a text in the Corner of slot. For count label.
     * 
     * @param x
     * @param y
     * @param text
     */
    public static void drawLabelCorneredScaled(GuiGraphics graphics, int x, int y, String text, float scale) {

        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
//        GlStateManager.disableLighting();
//        GlStateManager.disableDepth();
//        GlStateManager.disableBlend();
        graphics.drawString(font, text, (int) ((x + 15) / scale - font.width(text) + 2),
                (int) ((y + 6 + font.lineHeight) / scale - font.lineHeight + 3), 16777215);
//        GlStateManager.enableLighting();
//        GlStateManager.enableDepth();
//        GlStateManager.enableBlend();
//        GlStateManager.popMatrix();
        graphics.pose().popPose();
    }

    /**
     * Draw a text in the middle of slot. for overflow label
     * 
     * @param x
     * @param y
     * @param text
     */
    public static void drawLabelCentered(GuiGraphics graphics,int x, int y, String text) {
//        GlStateManager.disableLighting();
//        GlStateManager.disableDepth();
//        GlStateManager.disableBlend();
        graphics.drawString(font,text, (x + 9 - font.width(text) / 2),
                (y + 9 - font.lineHeight / 2), 16777215);
//        GlStateManager.enableLighting();
//        GlStateManager.enableDepth();
//        GlStateManager.enableBlend();
    }

    /**
     * Render item stack for the tooltip
     * 
     * @param stack Itemstack to render
     * @param x
     * @param y
     */
    public static void renderItemStack(GuiGraphics graphics,ItemStack stack, int x, int y) {
        graphics.renderItem(stack, x, y);
        String numRep = null;
        if (stack.getCount() > 1)
            numRep = NumberUtils.getItemCountRepresentation(stack.getCount());
        if (font.width(numRep) > 16) {
            float scale = BPDConfig.label_scale;
            drawLabelCorneredScaled(graphics,x, y, numRep, scale);
            graphics.renderItemDecorations(client.font, stack, x, y, "");
        } else {
            graphics.renderItemDecorations(client.font, stack, x, y, numRep);
        }
    }

    public static void renderFluidStack(GuiGraphics graphics,FluidStack stack, int x, int y) {
        if (stack == null || stack.getAmount() == 0)
            return;
        FluidRender.renderFluid(stack, x, y);
        String numRep = NumberUtils.getFluidCountRepresentation(stack.getAmount());
        if (font.width(numRep) > 16) {
            float scale = BPDConfig.label_scale;
            drawLabelCorneredScaled(graphics,x, y, numRep, scale);
        } else {
            drawLabelCorneredScaled(graphics,x, y, numRep, 1);
        }
    }
}
