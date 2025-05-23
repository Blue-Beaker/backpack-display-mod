package io.bluebeaker.backpackdisplay.section;

import io.bluebeaker.backpackdisplay.BPDConfig;
import io.bluebeaker.backpackdisplay.BPDRegistryItems;
import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.api.IDisplaySection;
import io.bluebeaker.backpackdisplay.crafttweaker.CTIntegration;
import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import io.bluebeaker.backpackdisplay.utils.NumberUtils;
import io.bluebeaker.backpackdisplay.utils.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplaySectionItem implements IDisplaySection {

    private ItemStack itemStack = ItemStack.EMPTY;
    private List<ItemStack> itemsToRender;
    /** Amount of items hidden by overflow */
    private int overflowItems = 0;
    /** Width in pixels */
    private int width = 0;
    /** Height in pixels */
    private int height = 0;

    public DisplaySectionItem() {
    }

    /** Update the tooltip with a new ItemStack. */
    public void update(@Nonnull ItemStack stack) {
        // If stack is unchanged, skip updating
        if (ItemStack.areItemStacksEqual(stack, this.itemStack))
            return;
        this.itemStack = stack.copy();
        this.itemsToRender = this.getItemsForItem(this.itemStack);
        this.updateGeometry();
    }

    private List<ItemStack> getItemsForItem(ItemStack stack) {
        if (stack.isEmpty())
            return Collections.emptyList();
        List<ItemStack> items = new ArrayList<ItemStack>();
        if (Loader.isModLoaded("crafttweaker")) {
            try {
                items.addAll(CTIntegration.getItemsForCT(stack));
            } catch (Exception e) {
                BackpackDisplayMod.getLogger().error("Exception when getting display items from crafttweaker: ", e);
            }
        }

        List<IDisplaySlotEntry> entries = getRenderRules(stack);
        if (entries != null) {
            for (IDisplaySlotEntry rule : entries) {
                if (rule.isItemMatches(stack)) {
                    items.addAll(rule.getItemsFromContainer(stack));
                }
            }
        }
        return items;
    }

    private void updateGeometry() {
        List<ItemStack> items = this.itemsToRender;

        if (items == null || items.isEmpty()) {
            this.width = 0;
            this.height = 0;
            return;
        }

        int maxCount = BPDConfig.tooltipWidth * BPDConfig.tooltipHeight;

        int totalCount = items.size();

        // Get width of tooltip
        int totalWidth = Math.min(items.size(), BPDConfig.tooltipWidth);

        // Draw label for overflowed items that takes a slot
        if (totalCount > maxCount) {
            this.overflowItems = itemsToRender.size() - (maxCount - 1);
        } else {
            this.overflowItems = 0;
        }

        // Get height of tooltip
        int totalHeight = Math.min((totalCount - 1) / BPDConfig.tooltipWidth + 1, BPDConfig.tooltipHeight);

        int pixelWidth = totalWidth * 18;
        int pixelHeight = totalHeight * 18;

        this.width = pixelWidth;
        this.height = pixelHeight;
    }

    private static @Nullable List<IDisplaySlotEntry> getRenderRules(ItemStack stack) {
        Item item = stack.getItem();
        List<IDisplaySlotEntry> entries = BPDRegistryItems.registry.get(item);
        return entries;
    }

    @Override
    public void render(int x, int y) {
        List<ItemStack> items = this.itemsToRender;

        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.translate(0.0F, 0.0F, 512.0F);

        int totalCount = this.itemsToRender.size() - overflowItems;

        if (this.overflowItems > 0) {
            RenderUtils.drawLabelCentered(x + (BPDConfig.tooltipWidth - 1) * 18, y + (BPDConfig.tooltipHeight - 1) * 18,
                    "+" + NumberUtils.getItemCountRepresentation(overflowItems));
        }

        // Render every item
        for (int i = 0; i < totalCount; i++) {
            ItemStack stack2 = items.get(i);
            int slotX = i % BPDConfig.tooltipWidth;
            int slotY = i / BPDConfig.tooltipWidth;
            RenderUtils.renderItemStack(stack2, x + (slotX) * 18, y + (slotY) * 18);
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }

    @Override
    public String getID() {
        return "items";
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isAvailable() {
        return this.itemsToRender != null && !this.itemsToRender.isEmpty();
    }

    @Override
    public int defaultPriority() {
        return 0;
    }

}
