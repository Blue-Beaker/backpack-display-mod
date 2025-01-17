package io.bluebeaker.backpackdisplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.bluebeaker.backpackdisplay.api.IDisplaySection;
/**Sections manager for internal use. Plugins should use 
 * {@link io.bluebeaker.backpackdisplay.api.Sections} to register their sections instead.  */
public class SectionsManager {
    private static List<IDisplaySection> sections = new ArrayList<IDisplaySection>();
    protected static HashMap<String, Integer> sectionPriorities = new HashMap<String, Integer>();
    protected static SectionSorter sectionSorter = new SectionSorter();

    /** Adds new section. */
    public static void addSection(IDisplaySection newSection) {
        sections.add(newSection);
    }
    /** Get a list of all registered sections. */
    public static List<IDisplaySection> getSections(){
        return sections;
    }
    /** Sort sections after getting priorities from the config. */
    public static void sortSections() {
        sections.sort(sectionSorter);
    }

    protected static class SectionSorter implements Comparator<IDisplaySection> {
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
        for (String rule : BPDConfig.getInstance().priorities) {
            String[] split = rule.replaceAll(" ", "").split(":");
            if (split.length >= 2) {
                String id = split[0];
                try {
                    int priority = Integer.parseInt(split[1]);
                    sectionPriorities.put(id, priority);
                } catch (Exception e) {
                    BackpackDisplayMod.logError("Error when loading priority, please check it.", e);
                }
            } else {
                BackpackDisplayMod.logError("Priority rule format error: must be 'id:priority'");
            }
        }
    }
    /**Updates config file, add missing priorities */
    public static void updateConfig() {
        List<String> priorities = new ArrayList<String>(Arrays.asList(BPDConfig.getInstance().priorities));
        boolean modified = false;
        for (IDisplaySection section : sections) {
            String id = section.getID();
            if (!sectionPriorities.keySet().contains(id)) {
                priorities.add(id + ":" + section.defaultPriority());
                modified = true;
            }
        }
        if(modified){
            BPDConfig.getInstance().priorities = priorities.toArray(new String[0]);

//            ConfigManager.sync(BackpackDisplayMod.MODID, Type.INSTANCE);
        }
    }
}
