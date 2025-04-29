package io.bluebeaker.backpackdisplay.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.util.IngredientMap;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.zenutils.api.reload.Reloadable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Blue_Beaker
 */
@ZenClass("mods.backpackdisplay.BackpackDisplay")
@ZenRegister

public class DisplaySlotEntriesCT {
    private static final IngredientMap<IContainerFunction> PREVIEW_FUNCTIONS = new IngredientMap<>();
    private static final Map<IContainerFunction,String> FUNC_DEFINITIONS = new HashMap<>();

    
    /**
     * @param ingredient IIngredient representing items to add tooltip for
     * @param function Function to get displayed items from the container item: IItemStack->IItemStack[]
     */
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
            try {
                for (IItemStack resultingStack : func.process(item)) {
                    if(resultingStack==null){
                        throw new NullPointerException("An item is null");
                    }
                    items.add(resultingStack);
                }
            } catch (Exception e) {
                throw new RuntimeException(String.format("Exception in the function defined in: %s, when getting items for: %s", FUNC_DEFINITIONS.get(func), item.toCommandString()),e);
            }
        }
        return items;
    }

    @Reloadable
    public static class AddBackpackDisplayAction implements IAction {

        private final IIngredient ingredient;
        private final IContainerFunction function;
        private IngredientMap.IngredientMapEntry<IContainerFunction> entry;

        public AddBackpackDisplayAction(IIngredient ingredient, IContainerFunction function) {
            this.ingredient = ingredient;
            this.function = function;
        }
        
        @Override
        public void apply() {

            entry = PREVIEW_FUNCTIONS.register(ingredient, function);
            FUNC_DEFINITIONS.put(function,CraftTweakerAPI.getScriptFileAndLine());
        }

        public void undo() {
            PREVIEW_FUNCTIONS.unregister(entry);
        }
        
        @Override
        public String describe() {
            return "Adding backpack display for " + ingredient;
        }
    }
}
