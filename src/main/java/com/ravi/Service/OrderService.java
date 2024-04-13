package com.ravi.Service;

import com.ravi.model.Order;
import com.ravi.model.User;
import com.ravi.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest orderRequest, User user) throws Exception;
    public Order updateOrder(Long orderId, String orderStatus) throws Exception;
    public void cancelOrder(Long orderId) throws Exception;
    public List<Order> getUserOrders (Long userId) throws Exception;
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;
    public Order findOrderById(Long orderId) throws Exception;
}
