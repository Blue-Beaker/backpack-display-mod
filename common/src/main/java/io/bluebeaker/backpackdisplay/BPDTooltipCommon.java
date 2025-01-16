package io.bluebeaker.backpackdisplay;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.joml.Vector2ic;

import java.util.List;

public class BPDTooltipCommon {
    public static final BPDTooltipCommon INSTANCE = new BPDTooltipCommon();
    private ItemStack stack;

    private BPDTooltipCommon(){}
    public void register(){
        ClientTooltipEvent.RENDER_MODIFY_POSITION.register(this::getPos);
        ClientTooltipEvent.ITEM.register(this::onItem);
        ClientTooltipEvent.RENDER_PRE.register(this::onRender);
    }

    private EventResult onRender(GuiGraphics graphics, List<? extends ClientTooltipComponent> clientTooltipComponents, int i, int i1) {
        return EventResult.pass();
    }

    private void onItem(ItemStack stack, List<Component> components, TooltipFlag tooltipFlag) {
        this.stack=stack;
    }

    private void getPos(GuiGraphics graphics, ClientTooltipEvent.PositionContext context){
        int tooltipX = context.getTooltipX();
        int tooltipY = context.getTooltipY();

        if(!BPDTooltip.beforeRender())
            return;

        ItemStack stack = this.stack;

        graphics.pose().pushPose();

        BPDTooltip.render(stack,graphics, tooltipX, tooltipY, 100,100);

        graphics.pose().popPose();
        this.stack=ItemStack.EMPTY;
    }
}
