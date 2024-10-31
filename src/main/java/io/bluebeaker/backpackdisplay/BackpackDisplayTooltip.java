package io.bluebeaker.backpackdisplay;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiContainerEvent;

public class BackpackDisplayTooltip {
    public static void render(GuiContainerEvent event){
        Slot slot = event.getGuiContainer().getSlotUnderMouse();
        if (slot!=null){
            ItemStack stack=slot.getStack();
        }
    }
}
