package com.ravi.Service;

import com.ravi.model.IngredientCategory;
import com.ravi.model.IngredientsItems;
import com.ravi.model.Restaurant;
import com.ravi.repository.IngredientCategoryRepository;
import com.ravi.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImplementation implements IngredientsService{
    @Autowired
    private IngredientItemRepository ingredientItemRepository;
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setRestaurant(restaurant);
        ingredientCategory.setName(name);

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> optionalIngredientCategory = ingredientCategoryRepository.findById(id);

        if (optionalIngredientCategory.isEmpty()) {
            throw new Exception("Ingredient Category not found");
        }
        return optionalIngredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception {
        // Checking if there is any restaurant with given restaurantId - if not throws exception
        restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItems createIngredientItem(Long restaurantId, String ingredientName, Long ingredientCategoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = findIngredientCategoryById(ingredientCategoryId);

        IngredientsItems ingredientsItems = new IngredientsItems();
        ingredientsItems.setName(ingredientName);
        ingredientsItems.setRestaurant(restaurant);
        ingredientsItems.setCategory(ingredientCategory);

        IngredientsItems savedIngredientItem = ingredientItemRepository.save(ingredientsItems);
        ingredientCategory.getIngredients().add(savedIngredientItem);
        return savedIngredientItem;
    }

    @Override
    public List<IngredientsItems> findIngredientItemsByRestaurantId(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItems toggleAvailability(Long ingredientItemId) throws Exception {
        Optional<IngredientsItems> optionalIngredientsItems = ingredientItemRepository.findById(ingredientItemId);
        if (optionalIngredientsItems.isEmpty()) {
            throw new Exception("Ingredient item not found with the given id..");
        }
        IngredientsItems ingredientsItems = optionalIngredientsItems.get();
        ingredientsItems.setInStock(!ingredientsItems.isInStock());
        return ingredientItemRepository.save(ingredientsItems);
    }
}
