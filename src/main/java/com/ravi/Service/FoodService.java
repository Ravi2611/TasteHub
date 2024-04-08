package com.ravi.Service;

import com.ravi.model.Category;
import com.ravi.model.Food;
import com.ravi.model.Restaurant;
import com.ravi.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegetarian,
                                         boolean isNonVegetarian,
                                         boolean isSeasonal,
                                         String foodCategory);
    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food toggleAvailabilityStatus(Long foodId) throws Exception;


}
