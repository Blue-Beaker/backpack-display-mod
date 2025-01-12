package io.bluebeaker.backpackdisplay.crafttweaker;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.backpackdisplay.IContainerFunction")
public interface IContainerFunction {
    IItemStack[] process(IItemStack ingredient);
}
