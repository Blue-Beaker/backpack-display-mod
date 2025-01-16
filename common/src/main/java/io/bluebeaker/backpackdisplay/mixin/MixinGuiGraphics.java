package io.bluebeaker.backpackdisplay.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.bluebeaker.backpackdisplay.BPDTooltipCommon;
import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiGraphics.class)
public abstract class MixinGuiGraphics {
    @Inject(method = "renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V",at= @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawManaged(Ljava/lang/Runnable;)V"))
    private void getTooltipDimensions(Font font, List<ClientTooltipComponent> list, int i, int j, ClientTooltipPositioner clientTooltipPositioner, CallbackInfo ci, @Local(ordinal = 4) int w, @Local(ordinal = 5) int h, @Local(ordinal = 6) int x, @Local(ordinal = 7) int y){

        BackpackDisplayMod.logInfo(" "+x+" "+y+" "+w+" "+h);
        BPDTooltipCommon.INSTANCE.updateDimensions(x,y,w,h);
    }
}
