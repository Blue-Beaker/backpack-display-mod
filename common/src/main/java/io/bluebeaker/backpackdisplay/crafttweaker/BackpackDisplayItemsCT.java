package io.bluebeaker.backpackdisplay.crafttweaker;

import java.util.*;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.utils.IngredientMap;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;



/**
 * @author Blue_Beaker
 */
@ZenCodeType.Name("mods.backpackdisplay.BackpackDisplay")
@ZenRegister

public class BackpackDisplayItemsCT {
    private static final IngredientMap<IContainerFunction> PREVIEW_FUNCTIONS = new IngredientMap<>();

    
    /**
     * @param ingredient IIngredient representing items to add tooltip for
     * @param function Function to get displayed items from the container item: IItemStack->IItemStack[]
     */
    @ZenCodeType.Method
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
            items.addAll(Arrays.asList(func.process(item)));
        }
        return items;
    }

    
    public static class AddBackpackDisplayAction implements IUndoableAction {

        private final IIngredient ingredient;
        private final IContainerFunction function;
        @Nullable
        private IngredientMap.IngredientMapEntry<IContainerFunction> entry;

        public AddBackpackDisplayAction(IIngredient ingredient, IContainerFunction function) {
            this.ingredient = ingredient;
            this.function = function;
        }
        
        @Override
        public void apply() {

            entry = PREVIEW_FUNCTIONS.register(ingredient, function);
        }

        public void undo() {
            PREVIEW_FUNCTIONS.unregister(entry);
        }

        @Override
        public String describe() {
            return "Adding backpack display items for " + ingredient;
        }

        @Override
        public String describeUndo() {
            return "Removing backpack display item for " + ingredient;
        }

        @Override
        public String systemName() {
            return BackpackDisplayMod.MOD_ID+"-Item";
        }
    }
}
