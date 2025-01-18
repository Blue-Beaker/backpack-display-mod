package io.bluebeaker.backpackdisplay.utils.forge;

import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class FluidRenderImpl {

    public static TextureAtlasSprite getStillFluidSprite(FluidStack fluidStack) {
        net.minecraftforge.fluids.FluidStack forgeFluidStack = FluidStackHooksForge.toForge(fluidStack);

        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation resourceLocation = renderProperties.getStillTexture(forgeFluidStack);
        if(resourceLocation==null){
            resourceLocation = MissingTextureAtlasSprite.getLocation();
        }
        return Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(resourceLocation);
    }

    public static int getColorTint(FluidStack fluidStack) {
        net.minecraftforge.fluids.FluidStack forgeFluidStack = FluidStackHooksForge.toForge(fluidStack);

        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        return renderProperties.getTintColor(forgeFluidStack);
    }
}
