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
        "minecraft:black_shulker_box#list#BlockEntityTag.Items"};

    @LangKey("config.backpackdisplay.tooltipRows.name")
    @Config.RangeInt(min = 1)
    public static int tooltipWidth = 9;
    @LangKey("config.backpackdisplay.tooltipHeight.name")
    @Config.RangeInt(min = 1)
    public static int tooltipHeight = 4;
}