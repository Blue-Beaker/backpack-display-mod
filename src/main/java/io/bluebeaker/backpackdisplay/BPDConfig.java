package io.bluebeaker.backpackdisplay;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = BackpackDisplayMod.MODID,type = Type.INSTANCE,category = "general")
public class BPDConfig {
    @Comment({"Rules for getting contained items of container items for the tooltip to display.",
    "Format: '<modID>:<itemID>[:meta]#<rule type>#<rule definition>'",
    "rule type 'single': single item stored in certain path of NBT;",
    "rule type 'list': a list of items stored under a NBTTagList",
    "rule type 'dummy': fixed items intended for testing the display",
    "Replacement templates can be used for simplification, see README for details.",
    "For more complex rules, see the CraftTweaker support. "})
    @LangKey("config.backpackdisplay.displayrules.name")
    public static String[] displayRules = {
        //Vanilla Shulker Boxes
        "minecraft:{white,orange,magenta,light_blue,yellow,lime,pink,gray,silver,cyan,purple,blue,brown,green,red,black}_shulker_box#list#BlockEntityTag.Items",
        //Storage Drawers (Normal)
        "storagedrawers:{basicdrawers,customdrawers}#list#{BlockEntityTag,tile}.Drawers",
        //Storage Drawers (Compacting)
        "storagedrawers:compdrawers#single#BlockEntityTag.Drawers.Items.0.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.0.Conv",
        "storagedrawers:compdrawers#single#BlockEntityTag.Drawers.Items.1.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.1.Conv",
        "storagedrawers:compdrawers#single#BlockEntityTag.Drawers.Items.2.Item;BlockEntityTag.Drawers.Count;/BlockEntityTag.Drawers.Items.2.Conv",
        "storagedrawers:compdrawers#single#tile.Drawers.Items.0.Item;tile.Drawers.Count;/tile.Drawers.Items.0.Conv",
        "storagedrawers:compdrawers#single#tile.Drawers.Items.1.Item;tile.Drawers.Count;/tile.Drawers.Items.1.Conv",
        "storagedrawers:compdrawers#single#tile.Drawers.Items.2.Item;tile.Drawers.Count;/tile.Drawers.Items.2.Conv",
        //Forestry Backpacks
        "forestry:{apiarist,lepidopterist}_bag#list#Slots",
        "forestry:{miner,digger,forester,hunter,adventurer,builder}_bag{,_t2}#list#Slots"
    };

    @Comment("Max width of the backpack tooltip, in slots")
    @LangKey("config.backpackdisplay.tooltip_rows.name")
    @Config.RangeInt(min = 1,max = 36)
    public static int tooltipWidth = 9;

    @Comment("Max height of the backpack tooltip, in slots.")
    @LangKey("config.backpackdisplay.tooltip_height.name")
    @Config.RangeInt(min = 1,max = 36)
    public static int tooltipHeight = 4;

    @Comment("The item label will keep this count of digits before using k, M and G prefixes")
    @LangKey("config.backpackdisplay.full_digits.name")
    @Config.RangeInt(min = 3)
    public static int full_digits = 4;

    public enum KeybindType{
        NOT_NEEDED,
        PRESSED,
        RELEASED
    }

    @Comment("Whether a keybind is needed to show the container tooltip.")
    @LangKey("config.backpackdisplay.needs_keybind.name")
    public static KeybindType needs_keybind = KeybindType.NOT_NEEDED;
    

    @Comment("Offset of the backpack display")
    @LangKey("config.backpackdisplay.offset_x.name")
    public static int offset_x = 0;
    @Comment("Offset of the backpack display")
    @LangKey("config.backpackdisplay.offset_y.name")
    public static int offset_y = -8;

    @Comment("Scale of the count label in display when number is too long")
    @LangKey("config.backpackdisplay.label_scale.name")
    public static float label_scale = 0.5f;

    @Comment("Show verbose info when loading mod. Useful for writing new rules.")
    @LangKey("config.backpackdisplay.verbose_info.name")
    public static boolean verbose_info = false;


    @Comment("Config for showing contained fluids.")
    @LangKey("config.backpackdisplay.fluidSection.name")
    public static FluidSection fluidSection = new FluidSection();
    public static class FluidSection{
        @Comment({"Simple fluid rule is to get fluid automatically from containers. ",
        "If fluid can be drained/filled directly by using it on a tank-like block, it will be shown. ",
        "For more complex rules, see the CraftTweaker support. "})
        public boolean simpleRule = true;
        @Comment("If simpleRuleBlacklist is true, simpleContainers will work as blacklist. ")
        public boolean simpleRuleBlacklist = false;
        @Comment("Containers to include/exclude for simple rule. Format: <modID>:<itemID>[:meta]")
        public String[] simpleContainers = {};
    }

    @Comment({"Change priorities for display sections in this mod. ",
    "A section with higher priority will show on above another one with lower priority. ",
    "Format: id:priority"})
    @RequiresMcRestart
    public static String[] priorities = {"items:0"};

    public static Colors colors = new Colors();

    public static class Colors{
        // public static int backgroundColor = 0xF0100010;
        // public static int borderColorStart = 0x505000FF;
        public Color backgroundColor = new Color(0xF0100010);
        public Color borderColor = new Color(0x505000FF);
        public static class Color{
            public Color(int color){
                alpha=color>>24&255;
                red=color>>16&255;
                green=color>>8&255;
                blue=color&255;
            }
            @Config.SlidingOption
            @Config.RangeInt(min = 0,max = 255)
            public int alpha = 255;
            @Config.SlidingOption
            @Config.RangeInt(min = 0,max = 255)
            public int red = 255;
            @Config.SlidingOption
            @Config.RangeInt(min = 0,max = 255)
            public int green = 255;
            @Config.SlidingOption
            @Config.RangeInt(min = 0,max = 255)
            public int blue = 255;
            public int getColor(){
                return (alpha<<24)+(red<<16)+(green<<8)+blue;
            }
        }
    }
}