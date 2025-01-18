package io.bluebeaker.backpackdisplay.section.item;

import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.ConfigProvider;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntryBase;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntryList;
import io.bluebeaker.backpackdisplay.displayslot.DisplaySlotEntrySingle;
import io.bluebeaker.backpackdisplay.displayslot.IDisplaySlotEntry;
import io.bluebeaker.backpackdisplay.utils.ItemUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BPDRegistryItems {
    public static HashMap<Item,List<IDisplaySlotEntry>> registry = new HashMap<Item,List<IDisplaySlotEntry>>();

    public static void updateFromConfig(){
        registry.clear();
        for (String rule : ConfigProvider.getConfig().itemSection.displayRules){
            try {
                addRule(rule);
            } catch (Exception e) {
                BackpackDisplayMod.logError("Error when processing rule '"+rule+"': \n"+e.toString());
            }
        }
    }

    public static void addRule(String rule){
        String[] splitted = rule.split("(?<!\\\\)#");
        if(splitted.length<3) return;
        String itemStr = splitted[0].trim();
        String type = splitted[1].trim().toLowerCase();
        String nbtRule = splitted[2].trim();

        String[] itemsplit = itemStr.split(":");
        String modid = itemsplit[0];
        String resourceid = itemsplit[1];

        ResourceLocation itemID = new ResourceLocation(modid, resourceid);
        Item item = ItemUtils.getItemFromID(itemID);
        if(item==null || item == Items.AIR) return;

        IDisplaySlotEntry entry = buildEntryFromStringRule(type, nbtRule);

        addEntry(item, entry);
        if(ConfigProvider.getConfig().verbose_info)
            BackpackDisplayMod.logInfo("Adding entry with "+item.toString()+"type:"+type+", entry: "+entry.toString());
    }
    public static IDisplaySlotEntry buildEntryFromStringRule(String type, String nbtRule){
        IDisplaySlotEntry entry = switch (type) {
            case "single" -> new DisplaySlotEntrySingle(nbtRule);
            case "list" -> new DisplaySlotEntryList(nbtRule);
            default -> new DisplaySlotEntryBase(nbtRule);
        };

        return entry;
    }
    public static void addEntry(Item item, IDisplaySlotEntry entry){
        if(!registry.containsKey(item)){
            registry.put(item, new ArrayList<IDisplaySlotEntry>());
        }
        registry.get(item).add(entry);
    }
}
