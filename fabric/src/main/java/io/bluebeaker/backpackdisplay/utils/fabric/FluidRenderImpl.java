package io.bluebeaker.backpackdisplay.utils.fabric;

import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;

public class FluidRenderImpl {

    public static TextureAtlasSprite getStillFluidSprite(FluidStack fluidStack) {
        FluidVariant fluidVariant = FluidStackHooksFabric.toFabric(fluidStack);
        TextureAtlasSprite sprite = FluidVariantRendering.getSprite(fluidVariant);
        if(sprite==null){
            return Minecraft.getInstance()
                    .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                    .apply(MissingTextureAtlasSprite.getLocation());
        }
        return sprite;
    }

    public static int getColorTint(FluidStack fluidStack) {
        FluidVariant fluidVariant = FluidStackHooksFabric.toFabric(fluidStack);
        return FluidVariantRendering.getColor(fluidVariant);
    }
}
