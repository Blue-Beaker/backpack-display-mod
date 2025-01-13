package io.bluebeaker.backpackdisplay.api;



import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

/**Implement this class to add a new section of display for BackPackDisplay. */
public interface IDisplaySection {
    /**Default priority of the section. Will be overridden by the config later. */
    public int defaultPriority();
    /**ID of the section. Must not contain spaces and the symbol ':'. */
    public String getID();
    /**Update the tooltip with a new ItemStack */
    public void update(ItemStack stack);
    /**Get width of the tooltip */
    public int getWidth();
    /**Get height of the tooltip */
    public int getHeight();
    /**Returns whether the tooltip is available and non-empty. */
    public boolean isAvailable();
    /**Renders the tooltip */
    public void render(GuiGraphics graphics, int x, int y);
}
