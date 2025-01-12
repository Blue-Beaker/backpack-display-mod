package io.bluebeaker.backpackdisplay.forge;

import io.bluebeaker.backpackdisplay.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BPDTooltipForge {

    static Minecraft client = Minecraft.getInstance();
    private static int screenWidth = 1;
    private static int screenHeight = 1;
    private static int mouseX = 1;

    @SubscribeEvent
    public static void pre(RenderTooltipEvent.Pre event) {
        screenWidth = event.getScreenWidth();
        screenHeight = event.getScreenHeight();
        mouseX = event.getX();
        // BackpackDisplayMod.logInfo(event.getStack() + ":" + screenWidth + "," +
        // screenHeight);
    }

    @SubscribeEvent
    public static void render(RenderTooltipEvent.Pre event) {
        if(!BPDTooltip.beforeRender())
            return;

        ItemStack stack = event.getItemStack();
        BPDTooltip.render(stack,event.getGraphics(),event.getX(),event.getY(),100,100);
    }
}
