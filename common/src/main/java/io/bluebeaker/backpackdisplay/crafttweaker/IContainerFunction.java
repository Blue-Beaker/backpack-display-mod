package io.bluebeaker.backpackdisplay.crafttweaker;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.backpackdisplay.IContainerFunction")
public interface IContainerFunction {
    IItemStack[] process(IItemStack ingredient);
}
