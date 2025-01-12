package io.bluebeaker.backpackdisplay.crafttweaker;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.backpackdisplay.IContainerFunctionFluid")
public interface IContainerFunctionFluid {
    IFluidStack[] process(IItemStack ingredient);
}
