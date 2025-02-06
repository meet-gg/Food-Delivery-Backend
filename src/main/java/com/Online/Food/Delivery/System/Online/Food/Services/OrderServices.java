package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.OrderItemDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.OrderItemsDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderServices {
     ResponseEntity<?> placeOrder(Long restaurantId, List<OrderItemDTO> orderItemDTO);
     ResponseEntity<?> getAllOrder();
     ResponseEntity<OrderItemsDTO> getOrderById(Long id);
     ResponseEntity<?> OrderByUser();
}
