package com.ravi.Service;

import com.ravi.model.IngredientCategory;
import com.ravi.model.IngredientsItems;

import java.util.List;

// Adding service methods for both ingredientItems and ingredientCategory (controller will be different)
public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception;

    public IngredientsItems createIngredientItem(Long restaurantId, String ingredientName, Long ingredientCategoryId) throws Exception;

    public List<IngredientsItems> findIngredientItemsByRestaurantId(Long restaurantId);

    public IngredientsItems toggleAvailability (Long ingredientItemId) throws Exception;
}
