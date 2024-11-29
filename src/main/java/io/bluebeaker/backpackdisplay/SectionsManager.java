package io.bluebeaker.backpackdisplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.bluebeaker.backpackdisplay.api.IDisplaySection;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;

public class SectionsManager {
    public static List<IDisplaySection> sections = new ArrayList<IDisplaySection>();
    public static HashMap<String, Integer> sectionPriorities = new HashMap<String, Integer>();
    private static SectionSorter sectionSorter = new SectionSorter();

    /** Use this to add your custom section to the tooltip. */
    public static void addSection(IDisplaySection newSection) {
        sections.add(newSection);
    }
    
    public static void sortSections() {
        sections.sort(sectionSorter);
    }

    private static class SectionSorter implements Comparator<IDisplaySection> {
        @Override
        public int compare(IDisplaySection o1, IDisplaySection o2) {
            int result = getPriority(o2) - getPriority(o1);
            if (result > 1)
                result = 1;
            else if (result < -1)
                result = -1;
            return result;
        }
    }
    /**Get priority of the section. */
    public static int getPriority(IDisplaySection section) {
        String id = section.getID();
        if (sectionPriorities.keySet().contains(id)) {
            return sectionPriorities.get(id);
        } else {
            return section.defaultPriority();
        }
    }
    /**Update section priorities */
    public static void updateFromConfig() {
        for (String rule : BPDConfig.priorities) {
            String[] split = rule.split(":");
            if (split.length > 2) {
                String id = split[0];
                try {
                    int priority = Integer.parseInt(split[1]);
                    sectionPriorities.put(id, priority);
                } catch (Exception e) {
                    BackpackDisplayMod.getLogger().error("Priority rule format error: must be 'id:priority'", e);
                }
            } else {
                BackpackDisplayMod.getLogger().error("Priority rule format error: must be 'id:priority'");
            }
        }
    }
    /**Updates config file, add missing priorities */
    public static void updateConfig() {
        List<String> priorities = new ArrayList<String>(Arrays.asList(BPDConfig.priorities));
        boolean modified = false;
        for (IDisplaySection section : sections) {
            String id = section.getID();
            if (!sectionPriorities.keySet().contains(id)) {
                priorities.add(id + ":" + section.defaultPriority());
                modified = true;
            }
        }
        if(modified){
            BPDConfig.priorities = priorities.toArray(new String[0]);
            ConfigManager.sync(BackpackDisplayMod.MODID, Type.INSTANCE);
        }
    }
}
