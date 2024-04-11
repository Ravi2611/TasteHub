package com.ravi.controller;

import com.ravi.Service.IngredientsService;
import com.ravi.Service.UserService;
import com.ravi.model.IngredientCategory;
import com.ravi.model.IngredientsItems;
import com.ravi.model.User;
import com.ravi.request.IngredientCategoryRequest;
import com.ravi.request.IngredientItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
    @Autowired
    private IngredientsService ingredientsService;
    @Autowired
    private UserService userService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        IngredientCategory ingredientCategory = ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItems> createIngredientItem (
            @RequestBody IngredientItemRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        IngredientsItems item = ingredientsService.createIngredientItem(req.getRestaurantId(), req.getName(), req.getCategoryId());

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PutMapping("/{ingredientItemId}/stock")
    public ResponseEntity<IngredientsItems> toggleIngredientStock (
            @PathVariable Long ingredientItemId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        IngredientsItems item = ingredientsService.toggleAvailability(ingredientItemId);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<IngredientsItems>> getRestaurantAllIngredientItems (
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<IngredientsItems> ingredientsItems = ingredientsService.findIngredientItemsByRestaurantId(restaurantId);

        return new ResponseEntity<>(ingredientsItems, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantAllIngredientCategory (
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<IngredientCategory> ingredientCategories = ingredientsService.findIngredientCategoryByRestaurantId(restaurantId);

        return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
    }
}
