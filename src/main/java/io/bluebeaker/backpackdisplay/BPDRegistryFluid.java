package io.bluebeaker.backpackdisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.bluebeaker.backpackdisplay.displayslot.IItemMatcher;
import io.bluebeaker.backpackdisplay.displayslot.MetadataMatcher;
import io.bluebeaker.backpackdisplay.utils.NumberUtils;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class BPDRegistryFluid {

    public static HashMap<Item, List<IItemMatcher>> registry = new HashMap<Item, List<IItemMatcher>>();

    public static void updateFromConfig() {
        registry.clear();
        for (String rule : BPDConfig.fluidSection.simpleContainers) {
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
        if (!Item.REGISTRY.containsKey(itemID))
            return;
        Item item = Item.REGISTRY.getObject(itemID);
        Set<Integer> metadataList;

        if (itemsplit.length >= 3)
            metadataList = NumberUtils.parseMeta(itemsplit[2]);
        else
            metadataList = new HashSet<Integer>();

        IItemMatcher entry = new MetadataMatcher(metadataList);

        if (entry != null && item != null) {
            addEntry(item, entry);
            if (BPDConfig.verbose_info)
                BackpackDisplayMod.logInfo("Adding simple fluid rule for " + item.toString());
        }
    }

    public static void addEntry(Item item, IItemMatcher entry) {
        if (!registry.containsKey(item)) {
            registry.put(item, new ArrayList<IItemMatcher>());
        }
        registry.get(item).add(entry);
    }
}