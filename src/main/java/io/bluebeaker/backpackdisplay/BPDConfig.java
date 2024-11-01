package io.bluebeaker.backpackdisplay;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = BackpackDisplayMod.MODID,type = Type.INSTANCE,category = "general")
public class BPDConfig {
    @Comment({"Rules for getting contained items of container items for the tooltip to display.",
    "Format: '<modID>:<itemID>[:metadatas]#<content type>#<rule def>'",
    "Content type 'single': single item stored in certain path of NBT;",
    "Content type 'list': a list of items stored under a NBTTagList",
    "Content type 'dummy': fixed items intended for testing the display"})
    @LangKey("config.backpackdisplay.displayrules.name")
    public static String[] displayRules = {
        "minecraft:white_shulker_box#list#BlockEntityTag.Items",
        "minecraft:orange_shulker_box#list#BlockEntityTag.Items",
        "minecraft:magenta_shulker_box#list#BlockEntityTag.Items",
        "minecraft:light_blue_shulker_box#list#BlockEntityTag.Items",
        "minecraft:yellow_shulker_box#list#BlockEntityTag.Items",
        "minecraft:lime_shulker_box#list#BlockEntityTag.Items",
        "minecraft:pink_shulker_box#list#BlockEntityTag.Items",
        "minecraft:gray_shulker_box#list#BlockEntityTag.Items",
        "minecraft:silver_shulker_box#list#BlockEntityTag.Items",
        "minecraft:cyan_shulker_box#list#BlockEntityTag.Items",
        "minecraft:purple_shulker_box#list#BlockEntityTag.Items",
        "minecraft:blue_shulker_box#list#BlockEntityTag.Items",
        "minecraft:brown_shulker_box#list#BlockEntityTag.Items",
        "minecraft:green_shulker_box#list#BlockEntityTag.Items",
        "minecraft:red_shulker_box#list#BlockEntityTag.Items",
        "minecraft:black_shulker_box#list#BlockEntityTag.Items",
        "storagedrawers:basicdrawers:0#single#BlockEntityTag.Drawers.0.Item;BlockEntityTag.Drawers.0.Count",
        "storagedrawers:basicdrawers:1,3#single#BlockEntityTag.Drawers.0.Item;BlockEntityTag.Drawers.0.Count",
        "storagedrawers:basicdrawers:1,3#single#BlockEntityTag.Drawers.1.Item;BlockEntityTag.Drawers.1.Count",
        "storagedrawers:basicdrawers:2,4#single#BlockEntityTag.Drawers.0.Item;BlockEntityTag.Drawers.0.Count",
        "storagedrawers:basicdrawers:2,4#single#BlockEntityTag.Drawers.1.Item;BlockEntityTag.Drawers.1.Count",
        "storagedrawers:basicdrawers:2,4#single#BlockEntityTag.Drawers.2.Item;BlockEntityTag.Drawers.2.Count",
        "storagedrawers:basicdrawers:2,4#single#BlockEntityTag.Drawers.3.Item;BlockEntityTag.Drawers.3.Count",
        "storagedrawers:customdrawers:0#single#BlockEntityTag.Drawers.0.Item;BlockEntityTag.Drawers.0.Count",
        "storagedrawers:customdrawers:1,3#single#BlockEntityTag.Drawers.0.Item;BlockEntityTag.Drawers.0.Count",
        "storagedrawers:customdrawers:1,3#single#BlockEntityTag.Drawers.1.Item;BlockEntityTag.Drawers.1.Count",
        "storagedrawers:customdrawers:2,4#single#BlockEntityTag.Drawers.0.Item;BlockEntityTag.Drawers.0.Count",
        "storagedrawers:customdrawers:2,4#single#BlockEntityTag.Drawers.1.Item;BlockEntityTag.Drawers.1.Count",
        "storagedrawers:customdrawers:2,4#single#BlockEntityTag.Drawers.2.Item;BlockEntityTag.Drawers.2.Count",
        "storagedrawers:customdrawers:2,4#single#BlockEntityTag.Drawers.3.Item;BlockEntityTag.Drawers.3.Count",
        "storagedrawers:compdrawers#single#BlockEntityTag.Drawers.Items.0.Item",
        "storagedrawers:compdrawers#single#BlockEntityTag.Drawers.Items.1.Item",
        "storagedrawers:compdrawers#single#BlockEntityTag.Drawers.Items.2.Item"
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

    enum KeybindType{
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

    @Comment("Color of the backpack display")
    public static int backgroundColor = 0xF0100010;
    @Comment("Color of the backpack display")
    public static int borderColorStart = 0x505000FF;
}