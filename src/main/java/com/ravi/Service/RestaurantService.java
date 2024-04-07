package com.ravi.Service;

import com.ravi.dto.RestaurantDto;
import com.ravi.model.Restaurant;
import com.ravi.model.User;
import com.ravi.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurants();

    public List<Restaurant> searchRestaurant(String query);

    public Restaurant findRestaurantById(Long restaurantId) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception;
    public RestaurantDto removeFromFavourites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantHeartBeat(Long restaurantId) throws Exception;
}
