package io.bluebeaker.backpackdisplay;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BPDTooltipCommon {
    public static final BPDTooltipCommon INSTANCE = new BPDTooltipCommon();
    private ItemStack stack;
    private int tooltipX = 0;
    private int tooltipY = 0;
    private int tooltipW = 0;
    private int tooltipH = 0;

    private BPDTooltipCommon(){}
    public void register(){
        ClientTooltipEvent.ITEM.register(this::onItem);
        ClientGuiEvent.RENDER_POST.register(this::render);
    }

    private void onItem(ItemStack stack, List<Component> components, TooltipFlag tooltipFlag) {
        this.stack=stack;
    }

    private void render(Screen screen, GuiGraphics graphics, int x, int y, float delta) {
        if(!BPDTooltip.beforeRender())
            return;

        ItemStack stack = this.stack;

        graphics.pose().pushPose();
        BPDTooltip.updateParams(graphics.guiWidth(),x);
        BPDTooltip.render(stack,graphics, tooltipX, tooltipY, tooltipW,tooltipH);

        graphics.pose().popPose();
        this.stack=ItemStack.EMPTY;
    }

    public void updateDimensions(int x,int y,int w,int h){
        this.tooltipX=x;
        this.tooltipY=y;
        this.tooltipW=w;
        this.tooltipH=h;
    }
}
