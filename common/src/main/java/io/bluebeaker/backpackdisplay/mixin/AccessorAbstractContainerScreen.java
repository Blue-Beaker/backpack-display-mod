package io.bluebeaker.backpackdisplay.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerScreen.class)
public interface AccessorAbstractContainerScreen {
    @Accessor
    public Slot getHoveredSlot();
    @Accessor
    public int getLeftPos();
    @Accessor
    public int getTopPos();
}
