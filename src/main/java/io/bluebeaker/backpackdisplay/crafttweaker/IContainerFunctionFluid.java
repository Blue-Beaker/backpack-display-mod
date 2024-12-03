package io.bluebeaker.backpackdisplay.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.backpackdisplay.IContainerFunctionFluid")
public interface IContainerFunctionFluid {
    ILiquidStack[] process(IItemStack ingredient);
}
