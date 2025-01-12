package io.bluebeaker.backpackdisplay.utils;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import dev.architectury.fluid.FluidStack;

public class FluidRender {

    static Minecraft client = Minecraft.getInstance();
    
    public static void renderFluid(FluidStack fluidStack, int x, int y){
        Fluid fluid = fluidStack.getFluid();
//        int color = fluid.getColor(fluidStack);
//
//        setGLColorFromInt(color);
        
        TextureAtlasSprite sprite = getStillFluidSprite(client, fluid);
        
        drawTexture(x, y, sprite, 0);
        
    }
	private static TextureAtlasSprite getStillFluidSprite(Minecraft minecraft, Fluid fluid) {
		TextureMap textureMapBlocks = minecraft.getTextureMapBlocks();
		ResourceLocation fluidStill = fluid.getStill();
		TextureAtlasSprite fluidStillSprite = null;
		if (fluidStill != null) {
			fluidStillSprite = textureMapBlocks.getTextureExtry(fluidStill.toString());
		}
		if (fluidStillSprite == null) {
			fluidStillSprite = textureMapBlocks.getMissingSprite();
		}
		return fluidStillSprite;
	}
	private static void drawTexture(double xCoord, double yCoord, TextureAtlasSprite textureSprite, double zLevel) {
		
		client.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        double uMin = textureSprite.getMinU();
		double uMax = textureSprite.getMaxU();
		double vMin = textureSprite.getMinV();
		double vMax = textureSprite.getMaxV();

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tesselator.getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.pos(xCoord, yCoord + 16, zLevel).tex(uMin, vMax).endVertex();
		bufferBuilder.pos(xCoord + 16, yCoord + 16, zLevel).tex(uMax, vMax).endVertex();
		bufferBuilder.pos(xCoord + 16, yCoord, zLevel).tex(uMax, vMin).endVertex();
		bufferBuilder.pos(xCoord, yCoord, zLevel).tex(uMin, vMin).endVertex();
		tesselator.draw();
	}
	private static void setGLColorFromInt(int color) {
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;

		GlStateManager.color(red, green, blue, 1.0F);
	}
}
