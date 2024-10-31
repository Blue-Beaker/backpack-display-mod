package io.bluebeaker.backpackdisplay;

import java.util.List;

import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BackpackDisplayTooltip {
    @SubscribeEvent
    public static void render(GuiContainerEvent.DrawForeground event){
        GuiContainer container = event.getGuiContainer();
        Slot slot = container.getSlotUnderMouse();
        if (slot==null) return;
        ItemStack stack=slot.getStack();
        if(stack.isEmpty()) return;
        for (IDisplaySlotEntry rule : getRenderRules(stack)){
            if(rule.isItemMatches(stack)){
                List<ItemStack> list = rule.getItemsFromContainer(stack);
                container.drawItemStack(stack,event.getMouseX(),event.getMouseY()-32,"");
            }
        }
    }
    public static List<IDisplaySlotEntry> getRenderRules(ItemStack stack){
        Item item = stack.getItem();
        List<IDisplaySlotEntry> entries = BackpackDisplayRegistry.registry.get(item);
        return entries;
    }
}
