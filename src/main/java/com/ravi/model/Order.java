package com.ravi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ravi.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private User customer;

    @JsonIgnore // whenever we fetch Order from db we don't want this restaurant object and hence JsonIgnore. We will be creating different API all together for this as Restaurant is also a different db
    @ManyToOne
    private Restaurant restaurant;

    private Long totalAmount;

    private String orderStatus;

    private Date createdAt;

    @ManyToOne
    private Address deliveryAddress;

    @OneToMany // The @OneToMany annotation signifies a one-to-many relationship between the Order entity and the OrderItem entity.
    private List<OrderItem> items;

    // private Payment payment;

    private int totalItem;

    private int totalPrice;
}
