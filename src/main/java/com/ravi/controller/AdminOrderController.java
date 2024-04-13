package com.ravi.controller;

import com.ravi.Service.OrderService;
import com.ravi.Service.UserService;
import com.ravi.model.Order;
import com.ravi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getRestaurantOrderHistory (
            @PathVariable Long restaurantId,
            @RequestParam (required = false) String orderStatus,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantOrders(restaurantId, orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus (
            @PathVariable Long orderId,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order orders =  orderService.updateOrder(orderId, orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
