package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.MenuDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.RestaurantDTO;
import org.springframework.http.ResponseEntity;

public interface RestaurantServices {
     ResponseEntity<?> addRestaurant(RestaurantDTO restaurantDTO);
     ResponseEntity<?> assignFoodToRestaurant(Long id, MenuDTO menuDTO);
     ResponseEntity<?> getOrdersByRestaurantId(Long restaurantId);
     ResponseEntity<?> orderReady(Long orderId);
     ResponseEntity<?> OrderDelivered(Long orderId,Integer otp);
     ResponseEntity<?> getAllRestaurants();
}
