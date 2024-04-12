package com.ravi.repository;

import com.ravi.model.Cart;
import com.ravi.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
