package io.bluebeaker.backpackdisplay.section;

import io.bluebeaker.backpackdisplay.api.IDisplaySection;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**A dummy section with nothing in it, for testing purposes. */
public class DisplaySectionDummy implements IDisplaySection {

    public DisplaySectionDummy(String id){
        this.id=id;
    }

    private final String id;
    private boolean available;
    @Override
    public int defaultPriority() {
        return 999;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void update(@Nonnull ItemStack stack) {
        available=!stack.isEmpty();
    }

    @Override
    public int getWidth() {
        return 20;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void render(int x, int y) {
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.id, (float) x,
                (float) y, 16777215);
    }
}
