package io.bluebeaker.backpackdisplay.crafttweaker.forge;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import dev.architectury.fluid.FluidStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

public class CTIntegrationImpl {

    public static FluidStack getFluidstackFromIFluidStack(IFluidStack iFluidStack){
        net.minecraftforge.fluids.FluidStack internal = iFluidStack.<net.minecraftforge.fluids.FluidStack>getInternal();
        Fluid fluid = internal.getFluid();
        long amount = internal.getAmount();
        CompoundTag tag = internal.getTag();
        return FluidStack.create(fluid,amount,tag);
    }
}
