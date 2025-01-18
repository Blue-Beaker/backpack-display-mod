package io.bluebeaker.backpackdisplay.section.fluid;

import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.ConfigProvider;
import io.bluebeaker.backpackdisplay.utils.ItemUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashSet;

public class BPDRegistryFluid {

    public static HashSet<Item> registry = new HashSet<>();

    public static void updateFromConfig() {
        registry.clear();
        for (String rule : ConfigProvider.getConfig().fluidSection.simpleContainerList) {
            try {
                addRule(rule);
            } catch (Exception e) {
                BackpackDisplayMod.logError("Error when processing rule '" + rule + "': \n" + e.toString());
            }
        }
    }

    public static void addRule(String rule) {
        String[] itemsplit = rule.split(":");
        String modid = itemsplit[0];
        String resourceid = itemsplit[1];

        ResourceLocation itemID = new ResourceLocation(modid, resourceid);
        Item item = ItemUtils.getItemFromID(itemID);
        if(item==null || item == Items.AIR) return;

        addEntry(item);
        if (ConfigProvider.getConfig().verbose_info)
            BackpackDisplayMod.logInfo("Adding simple fluid rule for " + item.toString());
    }

    public static void addEntry(Item item) {
        registry.add(item);
    }
}