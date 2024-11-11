package io.bluebeaker.backpackdisplay.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.util.IngredientMap;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author Blue_Beaker
 */
@ZenClass("mods.backpackdisplay.BackpackDisplay")
@ZenRegister
public class DisplaySlotEntriesCT {
    private static final IngredientMap<IContainerFunction> PREVIEW_FUNCTIONS = new IngredientMap<>();

    
    @ZenMethod
    public static void addBackDisplay(IIngredient ingredient, IContainerFunction function) {
        CraftTweakerAPI.apply(new AddBackpackDisplayAction(ingredient, function));
    }

    public static List<IContainerFunction> getFunctions(IItemStack item) {
        return PREVIEW_FUNCTIONS.getEntries(item);
    }

    /**
     * @param item IItemstack to get items from
     * @return list of IItemstack
     */
    public static List<IItemStack> getDisplayItems(IItemStack item) {
        List<IItemStack> items = new ArrayList<IItemStack>();
        for(IContainerFunction func:getFunctions(item)){
            for(IItemStack stack : func.process(item)){
                items.add(stack);
            }
        }
        return items;
    }

    private static class AddBackpackDisplayAction implements IAction {

        private final IIngredient ingredient;
        private final IContainerFunction function;
        
        public AddBackpackDisplayAction(IIngredient ingredient, IContainerFunction function) {
            this.ingredient = ingredient;
            this.function = function;
        }
        
        @Override
        public void apply() {
            PREVIEW_FUNCTIONS.register(ingredient, function);
        }
        
        
        @Override
        public String describe() {
            return "Adding backpack display for " + ingredient;
        }
    }
}
