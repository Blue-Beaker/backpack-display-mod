package io.bluebeaker.backpackdisplay.utils.forge;

import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FluidUtilsImpl {

    public static List<FluidStack> getFluidInItem(ItemStack itemStack){
        Optional<net.minecraftforge.fluids.FluidStack> fluidContained = FluidUtil.getFluidContained(itemStack);

        return fluidContained.isPresent()?
                Collections.singletonList(FluidStackHooksForge.fromForge(fluidContained.get())) : Collections.emptyList();
    }
}
