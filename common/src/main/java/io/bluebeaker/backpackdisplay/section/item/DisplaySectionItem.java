package io.bluebeaker.backpackdisplay.section.item;

import io.bluebeaker.backpackdisplay.BPDConfig;
import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.api.IDisplaySection;
import io.bluebeaker.backpackdisplay.crafttweaker.CTIntegration;
import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import io.bluebeaker.backpackdisplay.utils.EnvironmentUtils;
import io.bluebeaker.backpackdisplay.utils.NumberUtils;
import io.bluebeaker.backpackdisplay.utils.RenderUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplaySectionItem implements IDisplaySection {

    private ItemStack itemStack;
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
    public void update( ItemStack stack) {
        // If stack is unchanged, skip updating
        if (this.itemStack != null && ItemStack.isSameItemSameTags(stack, this.itemStack))
            return;
        this.itemStack = stack.copy();
        this.itemsToRender = this.getItemsForItem(this.itemStack);
        this.updateGeometry();
    }

    private List<ItemStack> getItemsForItem(ItemStack stack) {
        if (stack == null || stack.isEmpty())
            return Collections.emptyList();
        List<ItemStack> items = new ArrayList<ItemStack>();
        if (EnvironmentUtils.isCraftTweakerLoaded()) {
            try {
                items.addAll(CTIntegration.getItemsForCT(stack));
            } catch (Exception e) {
                BackpackDisplayMod.logError("Exception when getting display items from crafttweaker: ", e);
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

        int maxCount = BPDConfig.getInstance().tooltipWidth * BPDConfig.getInstance().tooltipHeight;

        int totalCount = items.size();

        // Get width of tooltip
        int totalWidth = Math.min(items.size(), BPDConfig.getInstance().tooltipWidth);

        // Draw label for overflowed items that takes a slot
        if (totalCount > maxCount) {
            this.overflowItems = itemsToRender.size() - (maxCount - 1);
        } else {
            this.overflowItems = 0;
        }

        // Get height of tooltip
        int totalHeight = Math.min((totalCount - 1) / BPDConfig.getInstance().tooltipWidth + 1, BPDConfig.getInstance().tooltipHeight);

        int pixelWidth = totalWidth * 18;
        int pixelHeight = totalHeight * 18;

        this.width = pixelWidth;
        this.height = pixelHeight;
    }

    private static List<IDisplaySlotEntry> getRenderRules(ItemStack stack) {
        Item item = stack.getItem();
        List<IDisplaySlotEntry> entries = BPDRegistryItems.registry.get(item);
        return entries;
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y) {
        int count = 0;
        List<ItemStack> items = this.itemsToRender;

        int totalCount = this.itemsToRender.size() - overflowItems;

        if (this.overflowItems > 0) {
            RenderUtils.drawLabelCentered( graphics,x + (BPDConfig.getInstance().tooltipWidth - 1) * 18, y + (BPDConfig.getInstance().tooltipHeight - 1) * 18,
                    "+" + NumberUtils.getItemCountRepresentation(overflowItems));
        }

        // Render every item
        for (int i = 0; i < totalCount; i++) {
            ItemStack stack2 = items.get(i);
            int slotX = count % BPDConfig.getInstance().tooltipWidth;
            int slotY = count / BPDConfig.getInstance().tooltipWidth;
            RenderUtils.renderItemStack(graphics,stack2, x + (slotX) * 18, y + (slotY) * 18);
            count++;
        }
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
        return this.itemsToRender != null && this.itemsToRender.size() > 0;
    }

    @Override
    public int defaultPriority() {
        return 0;
    }

}
