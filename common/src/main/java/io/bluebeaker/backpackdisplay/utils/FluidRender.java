package io.bluebeaker.backpackdisplay.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.architectury.fluid.FluidStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Matrix4f;

public class FluidRender {

    static Minecraft client = Minecraft.getInstance();
    
    public static void renderFluid(GuiGraphics graphics, FluidStack fluidStack, int x, int y){
		int color = getColorTint(fluidStack);

		RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        setGLColorFromInt(color);

        TextureAtlasSprite sprite = getStillFluidSprite(fluidStack);

		Matrix4f matrix = graphics.pose().last().pose();
		drawTexture(matrix, x, y, sprite, 100);
		setGLColorFromInt(0xFFFFFFFF);
    }
	@ExpectPlatform
	public static TextureAtlasSprite getStillFluidSprite(FluidStack fluidStack) {
		TextureAtlasSprite particleIcon = Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(fluidStack.getFluid().defaultFluidState().createLegacyBlock()).getParticleIcon();
		return particleIcon;
	}

	@ExpectPlatform
	public static int getColorTint(FluidStack fluidStack) {
		return 0xFFFFFFFF;
	}

	private static void setGLColorFromInt(int color) {
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		float alpha = ((color >> 24) & 0xFF) / 255F;

		RenderSystem.setShaderColor(red, green, blue, alpha);
	}

	private static void drawTexture(Matrix4f matrix, float xCoord, float yCoord, TextureAtlasSprite textureSprite, float zLevel) {
		float uMin = textureSprite.getU0();
		float uMax = textureSprite.getU1();
		float vMin = textureSprite.getV0();
		float vMax = textureSprite.getV1();

		RenderSystem.setShader(GameRenderer::getPositionTexShader);

		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.vertex(matrix, xCoord, yCoord + 16, zLevel).uv(uMin, vMax).endVertex();
		bufferBuilder.vertex(matrix, xCoord + 16, yCoord + 16, zLevel).uv(uMax, vMax).endVertex();
		bufferBuilder.vertex(matrix, xCoord + 16, yCoord, zLevel).uv(uMax, vMin).endVertex();
		bufferBuilder.vertex(matrix, xCoord, yCoord, zLevel).uv(uMin, vMin).endVertex();
		tessellator.end();
	}
}
