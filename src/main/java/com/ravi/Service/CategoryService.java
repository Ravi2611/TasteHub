package com.ravi.Service;

import com.ravi.model.Category;

import java.util.List;

public interface CategoryService {
    // Using user id we will find the restaurant for which we will create category
    public Category createCategory(String name, Long userId) throws Exception;


    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception;

    public List<Category> findCategoryByUserId(Long userId) throws Exception;

    public Category findCategoryById(Long id) throws Exception;

}
