package io.bluebeaker.backpackdisplay.crafttweaker.forge;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import dev.architectury.fluid.FluidStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

public class CTIntegrationImpl {

    public static FluidStack getFluidstackFromIFluidStack(IFluidStack iFluidStack){
        net.minecraftforge.fluids.FluidStack immutableInternal = iFluidStack.<net.minecraftforge.fluids.FluidStack>getImmutableInternal();
        Fluid fluid = immutableInternal.getFluid();
        long amount = immutableInternal.getAmount();
        CompoundTag tag = immutableInternal.getTag();
        return FluidStack.create(fluid,amount,tag);
    }
}
