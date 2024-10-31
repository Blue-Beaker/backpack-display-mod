package io.bluebeaker.backpackdisplay;

import java.util.List;

import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BackpackDisplayTooltip {
    @SubscribeEvent
    public static void render(RenderTooltipEvent.PostText event){
        // GuiContainer container = event.getGuiContainer();
        // Slot slot = container.getSlotUnderMouse();
        // if (slot==null) return;
        // ItemStack stack=slot.getStack();
        ItemStack stack=event.getStack();
        if(stack.isEmpty()) return;
        List<IDisplaySlotEntry> entries = getRenderRules(stack);
        if(entries==null) return;
        for (IDisplaySlotEntry rule : entries){
            if(rule.isItemMatches(stack)){
                List<ItemStack> items = rule.getItemsFromContainer(stack);
                // renderItems(items,event.getMouseX()-container.getGuiLeft(),event.getMouseY()-container.getGuiTop());
                renderItems(items,event.getX(),event.getY());
            }
        }
    }
    private static void renderItems(List<ItemStack> items,int x,int y){
        Minecraft client = Minecraft.getMinecraft();
        GlStateManager.translate(0.0F, 0.0F, 256.0F);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        int gridX=0;
        int gridY=0;
        for (ItemStack stack2: items){
            client.getRenderItem().renderItemIntoGUI(stack2, x+gridX*18, y-32);
            gridX++;
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }
    public static List<IDisplaySlotEntry> getRenderRules(ItemStack stack){
        Item item = stack.getItem();
        List<IDisplaySlotEntry> entries = BackpackDisplayRegistry.registry.get(item);
        return entries;
    }
}
