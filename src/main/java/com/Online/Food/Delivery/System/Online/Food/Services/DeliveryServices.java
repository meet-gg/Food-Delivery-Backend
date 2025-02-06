package com.Online.Food.Delivery.System.Online.Food.Services;

import org.springframework.http.ResponseEntity;

public interface DeliveryServices {
     ResponseEntity<?> getAllOrder();
     void removeDeliverOrder(Long orderId);
     ResponseEntity<?> getDeliveryById(Long orderId);
     ResponseEntity<?> assignDelivery(Long deliveryId);
}
