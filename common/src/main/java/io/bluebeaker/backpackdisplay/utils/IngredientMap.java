package io.bluebeaker.backpackdisplay.utils;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;

import java.util.*;

public class IngredientMap<T>{

    private final Map<IIngredient, Map<IngredientMapEntry<T>,T>> internal = new HashMap<>();

    public List<T> getEntries(IItemStack item) {
        List<T> entries = new ArrayList<>();
        for(IIngredient ingredient:internal.keySet()){
            if(ingredient.matches(item)){
                entries.addAll(internal.get(ingredient).values());
            }
        }
        return entries;
    }
    public void unregister(IngredientMapEntry<T> entry) {
        internal.forEach(((ingredient, ingredientMapEntryTMap) -> {
            ingredientMapEntryTMap.remove(entry);
        }));
    }
    public IngredientMapEntry<T> register(IIngredient ingredient,T entry){
        this.internal.computeIfAbsent(ingredient, k -> new HashMap<>());
        IngredientMapEntry<T> mapEntry = new IngredientMapEntry<T>();
        this.internal.get(ingredient).put(mapEntry,entry);
        return mapEntry;
    }

    public static class IngredientMapEntry<T>{

    }
}
