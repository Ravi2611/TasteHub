package com.ravi.controller;

import com.ravi.Service.FoodService;
import com.ravi.Service.RestaurantService;
import com.ravi.Service.UserService;
import com.ravi.model.Food;
import com.ravi.model.Restaurant;
import com.ravi.model.User;
import com.ravi.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.searchFood(keyword);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable Long restaurantId,
                                                 @RequestParam(required = false) boolean vegetarian,
                                                 @RequestParam(required = false) boolean seasonal,
                                                 @RequestParam(required = false) boolean nonVeg,
                                                 @RequestParam(required = false) String food_category,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegetarian, nonVeg, seasonal, food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
