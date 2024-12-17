package io.bluebeaker.backpackdisplay.section;

import io.bluebeaker.backpackdisplay.BPDConfig;
import io.bluebeaker.backpackdisplay.BPDRegistryFluid;
import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.api.IDisplaySection;
import io.bluebeaker.backpackdisplay.crafttweaker.CTIntegration;
import io.bluebeaker.backpackdisplay.displayslot.IItemMatcher;
import io.bluebeaker.backpackdisplay.utils.NumberUtils;
import io.bluebeaker.backpackdisplay.utils.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplaySectionFluid implements IDisplaySection {

    private ItemStack itemStack;
    private List<FluidStack> fluidStacks = Collections.emptyList();
    /** Amount of fluids hidden by overflow */
    private int overflowFluids = 0;
    /** Width in pixels */
    private int width = 0;
    /** Height in pixels */
    private int height = 0;

    @Override
    public int defaultPriority() {
        return -1;
    }

    @Override
    public String getID() {
        return "fluid";
    }

    @Override
    public void update(@Nonnull ItemStack stack) {
        // If stack is unchanged, skip updating
        if (this.itemStack != null && ItemStack.areItemStacksEqual(stack, this.itemStack))
            return;
        this.itemStack = stack.copy();
        this.fluidStacks = getFluidStacks(stack);
        this.updateGeometry();
    }

    private List<FluidStack> getFluidStacks(@Nonnull ItemStack stack) {
        if (stack == null || stack.isEmpty())
            return Collections.emptyList();

        List<FluidStack> fluids = new ArrayList<FluidStack>();
        fluids.addAll(getFluidStacksCT(stack));

        if (BPDConfig.fluidSection.simpleRule
                && (isSimpleContainer(stack))) {
            FluidStack simpleFluid = getFluidStackBasic(stack);
            if (simpleFluid != null) {
                fluids.add(simpleFluid);
            }
        }
        return fluids;
    }

    private FluidStack getFluidStackBasic(@Nonnull ItemStack stack) {
        FluidStack fluid = FluidUtil.getFluidContained(stack);
        return fluid;
    }

    private List<FluidStack> getFluidStacksCT(@Nonnull ItemStack stack) {
        if (Loader.isModLoaded("crafttweaker")) {
            try {
                return CTIntegration.getFluidsForCT(stack);
            } catch (Exception e) {
                BackpackDisplayMod.getLogger().error("Exception when getting display fluids from crafttweaker: ", e);
            }
        }
        return Collections.emptyList();
    }

    private boolean isSimpleContainer(@Nonnull ItemStack stack) {
        boolean invert = BPDConfig.fluidSection.simpleRuleBlacklist;
        List<IItemMatcher> rules = BPDRegistryFluid.registry.get(stack.getItem());
        if (rules == null)
            return invert;
        for (IItemMatcher rule : rules) {
            if (rule.isItemMatches(stack)) {
                return !invert;
            }
        }
        return invert;
    }

    private void updateGeometry() {

        List<FluidStack> fluids = this.fluidStacks;

        if (fluids == null || fluids.isEmpty()) {
            this.width = 0;
            this.height = 0;
            return;
        }

        int maxCount = BPDConfig.tooltipWidth * BPDConfig.tooltipHeight;

        int totalCount = fluids.size();

        // Get width of tooltip
        int totalWidth = Math.min(fluids.size(), BPDConfig.tooltipWidth);

        // Draw label for overflowed items that takes a slot
        if (totalCount > maxCount) {
            this.overflowFluids = fluids.size() - (maxCount - 1);
        } else {
            this.overflowFluids = 0;
        }

        // Get height of tooltip
        int totalHeight = Math.min((totalCount - 1) / BPDConfig.tooltipWidth + 1, BPDConfig.tooltipHeight);

        int pixelWidth = totalWidth * 18;
        int pixelHeight = totalHeight * 18;

        this.width = pixelWidth;
        this.height = pixelHeight;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public boolean isAvailable() {
        return !this.fluidStacks.isEmpty();
    }

    @Override
    public void render(int x, int y) {
        int count = 0;
        List<FluidStack> fluids = this.fluidStacks;

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
//        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.translate(0.0F, 0.0F, 512.0F);

        int totalCount = fluids.size() - overflowFluids;

        if (this.overflowFluids > 0) {
            RenderUtils.drawLabelCentered(x + (BPDConfig.tooltipWidth - 1) * 18, y + (BPDConfig.tooltipHeight - 1) * 18,
                    "+" + NumberUtils.getItemCountRepresentation(overflowFluids));
        }

        // Render every item
        for (int i = 0; i < totalCount; i++) {
            FluidStack stack2 = fluids.get(i);
            int slotX = count % BPDConfig.tooltipWidth;
            int slotY = count / BPDConfig.tooltipWidth;

            RenderUtils.renderFluidStack(stack2, x + (slotX) * 18, y + (slotY) * 18);
            count++;
        }
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
//        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }
}