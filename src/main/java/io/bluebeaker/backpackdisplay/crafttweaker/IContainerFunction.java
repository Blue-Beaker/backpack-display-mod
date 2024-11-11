package io.bluebeaker.backpackdisplay.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("crafttweaker.item.IContainerFunction")
public interface IContainerFunction {
    IItemStack[] process(IItemStack ingredient);
}
