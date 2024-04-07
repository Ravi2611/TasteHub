package com.ravi.controller;

import com.ravi.Response.MessageResponse;
import com.ravi.Service.RestaurantService;
import com.ravi.Service.UserService;
import com.ravi.model.Restaurant;
import com.ravi.model.User;
import com.ravi.request.CreateRestaurantRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    private static final Logger logger = LogManager.getLogger(AuthController.class);
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        logger.debug("Inside admin controller..");

        logger.info("Finding user by jwt token");
        User user = userService.findUserByJwtToken(jwt);
        logger.error("User Found: " + user);

        Restaurant restaurant = restaurantService.createRestaurant(req, user);
        logger.error("Created restaurant: " + restaurant);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {

        logger.debug("Inside admin controller..");

        logger.info("Finding user by jwt token");
        User user = userService.findUserByJwtToken(jwt);
        logger.error("User Found: " + user);

        Restaurant restaurant = restaurantService.updateRestaurant(id, req);
        logger.error("Updated restaurant: " + restaurant);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {

        logger.debug("Inside admin controller..");

        logger.info("Finding user by jwt token");
        User user = userService.findUserByJwtToken(jwt);
        logger.error("User Found: " + user);

        Restaurant restaurant = restaurantService.findRestaurantById(id);
        logger.error("Restaurant found with id: {} -> {}", id, restaurant);

        restaurantService.deleteRestaurant(id);
        logger.error("Deleted restaurant with id: " + id);

        return new ResponseEntity<>(new MessageResponse("Restaurant deleted succesfully"), HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {

        logger.debug("Inside admin controller..");

        logger.info("Finding user by jwt token");
        User user = userService.findUserByJwtToken(jwt);
        logger.error("User Found: " + user);

        Restaurant restaurant = restaurantService.updateRestaurantHeartBeat(id);
        logger.info("Restaurant heartbeat succesfully updated");

        logger.error("Current heartbeat status: {}", restaurant.isOpen());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        logger.debug("Inside admin controller..");

        logger.info("Finding user by jwt token");
        User user = userService.findUserByJwtToken(jwt);
        logger.error("User Found: " + user);

        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        logger.error("Restaurant found wrt user: {} -> {}", user, restaurant);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
