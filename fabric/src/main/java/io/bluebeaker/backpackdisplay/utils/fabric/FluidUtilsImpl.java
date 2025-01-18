package io.bluebeaker.backpackdisplay.utils.fabric;

import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FluidUtilsImpl {
    public static List<FluidStack> getFluidInItem(ItemStack itemStack){
        Storage<FluidVariant> storage = ContainerItemContext.withConstant(itemStack).find(FluidStorage.ITEM);
        if(storage==null)
            return Collections.emptyList();
        List<FluidStack> fluidStacks = new ArrayList<>();
        for (StorageView<FluidVariant> view : storage.nonEmptyViews()) {
            fluidStacks.add(FluidStackHooksFabric.fromFabric(view));
        }
        return fluidStacks;
    }
}
