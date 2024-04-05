package com.ravi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ravi.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fullName;

    private String email;

    private String password;

    // also giving default role
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @ElementCollection // Create a seperate table for favourites - table will store all the elements of the list
    private List<RestaurantDto> favourites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // If user is deleted from db than all its addresses will be deleted
    private List<Address> addresses = new ArrayList<>();

}
