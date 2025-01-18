package io.bluebeaker.backpackdisplay.utils;

import io.bluebeaker.backpackdisplay.BPDConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RenderUtils {

    static Minecraft client = Minecraft.getMinecraft();
    public static FontRenderer fontRenderer = client.fontRenderer;

    /**
     * Draw a text in the Corner of slot. For count label.
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
     * Draw a text in the middle of slot. for overflow label
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

    /**
     * Render item stack for the tooltip
     * 
     * @param stack Itemstack to render
     * @param x
     * @param y
     */
    public static void renderItemStack(ItemStack stack, int x, int y) {
        client.getRenderItem().renderItemIntoGUI(stack, x, y);
        String numRep = "";
        if (stack.getCount() > 1)
            numRep = NumberUtils.getItemCountRepresentation(stack.getCount());
        if (fontRenderer.getStringWidth(numRep) > 16) {
            float scale = BPDConfig.label_scale;
            drawLabelCorneredScaled(x, y, numRep, scale);
            client.getRenderItem().renderItemOverlayIntoGUI(client.fontRenderer, stack, x, y, "");
        } else {
            client.getRenderItem().renderItemOverlayIntoGUI(client.fontRenderer, stack, x, y, numRep);
        }
    }

    public static void renderFluidStack(FluidStack stack, int x, int y) {
        if (stack.amount == 0)
            return;
        FluidRender.renderFluid(stack, x, y);
        String numRep = NumberUtils.getFluidCountRepresentation(stack.amount);
        if (fontRenderer.getStringWidth(numRep) > 16) {
            float scale = BPDConfig.label_scale;
            drawLabelCorneredScaled(x, y, numRep, scale);
        } else {
            drawLabelCorneredScaled(x, y, numRep, 1);
        }
    }
}
