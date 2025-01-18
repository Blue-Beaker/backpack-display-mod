package io.bluebeaker.backpackdisplay.utils;

import dev.architectury.fluid.FluidStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class FluidUtils {
    @ExpectPlatform
    public static List<FluidStack> getFluidInItem(ItemStack itemStack){
        return Collections.emptyList();
    }
}
