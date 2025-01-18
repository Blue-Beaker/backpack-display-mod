package io.bluebeaker.backpackdisplay.section.fluid;

import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.ConfigProvider;
import io.bluebeaker.backpackdisplay.utils.ItemUtils;
import io.bluebeaker.backpackdisplay.utils.StringUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashSet;
import java.util.List;

public class BPDRegistryFluid {

    public static HashSet<Item> registry = new HashSet<>();

    public static void updateFromConfig() {
        registry.clear();
        for (String rule : ConfigProvider.getConfig().fluidSection.simpleContainerList) {
            List<String> filledRules = StringUtils.fillInTemplates(rule);
            for (String filledRule : filledRules) {
                try {
                    addRule(filledRule);
                } catch (Exception e) {
                    BackpackDisplayMod.logError("Error when processing rule '" + filledRule + "': \n" + e.toString());
                }
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