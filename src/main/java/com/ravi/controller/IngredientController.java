package com.ravi.controller;

import com.ravi.Service.IngredientsService;
import com.ravi.Service.UserService;
import com.ravi.model.IngredientCategory;
import com.ravi.model.User;
import com.ravi.request.IngredientCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
