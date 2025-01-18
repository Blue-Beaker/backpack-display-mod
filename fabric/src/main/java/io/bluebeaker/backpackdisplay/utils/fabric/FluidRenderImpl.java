package io.bluebeaker.backpackdisplay.utils.fabric;

import dev.architectury.fluid.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class FluidRenderImpl {

    public static TextureAtlasSprite getStillFluidSprite(FluidStack fluidStack) {
        FluidVariant fluidVariant = FluidVariant.of(fluidStack.getFluid(),fluidStack.getTag());
        return FluidVariantRendering.getSprite(fluidVariant);
    }

    public static int getColorTint(FluidStack fluidStack) {
        FluidVariant fluidVariant = FluidVariant.of(fluidStack.getFluid(),fluidStack.getTag());
        return FluidVariantRendering.getColor(fluidVariant);
    }
}
