package io.bluebeaker.backpackdisplay;

import java.util.ArrayList;
import java.util.List;

import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BackpackDisplayTooltip {
    static Minecraft client = Minecraft.getMinecraft();
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
        List<ItemStack> items = new ArrayList<ItemStack>();
        for (IDisplaySlotEntry rule : entries){
            if(rule.isItemMatches(stack)){
                items.addAll(rule.getItemsFromContainer(stack));
                // renderItems(items,event.getMouseX()-container.getGuiLeft(),event.getMouseY()-container.getGuiTop());
            }
        }
        renderItems(items,event.getX(),event.getY());
    }
    private static void renderItems(List<ItemStack> items,int x,int y){
        GlStateManager.translate(0.0F, 0.0F, 256.0F);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        int count=0;
        int totalHeight=Math.min((items.size()-1)/BPDConfig.tooltipWidth,BPDConfig.tooltipHeight);
        int maxCount=BPDConfig.tooltipWidth*BPDConfig.tooltipHeight;
        int totalCount = items.size();
        if(totalCount>maxCount) totalCount=maxCount-1;
        for (int i=0;i<totalCount;i++){
            ItemStack stack2 = items.get(i);
            int slotX=count%BPDConfig.tooltipWidth;
            int slotY=count/BPDConfig.tooltipWidth;
            renderItemStack(stack2, x+(slotX)*18, y-18+(-totalHeight+slotY)*18);
            count++;
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }
    public static void renderItemStack(ItemStack stack,int x,int y){
        client.getRenderItem().renderItemIntoGUI(stack,x,y);
        client.getRenderItem().renderItemOverlayIntoGUI(client.fontRenderer, stack, x, y, null);
    }
    public static List<IDisplaySlotEntry> getRenderRules(ItemStack stack){
        Item item = stack.getItem();
        List<IDisplaySlotEntry> entries = BackpackDisplayRegistry.registry.get(item);
        return entries;
    }
}
