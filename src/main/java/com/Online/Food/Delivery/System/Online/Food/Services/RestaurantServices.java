package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.MenuDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.RestaurantDTO;
import org.springframework.http.ResponseEntity;

public interface RestaurantServices {
    public ResponseEntity<?> addRestaurant(RestaurantDTO restaurantDTO);
    public ResponseEntity<?> assignFoodToRestaurant(Long id, MenuDTO menuDTO);
    public ResponseEntity<?> getOrdersByRestaurantId(Long restaurantId);
    public ResponseEntity<?> orderReady(Long orderId);
    public ResponseEntity<?> OrderDelivered(Long orderId,Integer otp);

}
