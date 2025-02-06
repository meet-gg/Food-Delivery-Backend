package com.Online.Food.Delivery.System.Online.Food.Controller;

import com.Online.Food.Delivery.System.Online.Food.Services.DeliveryServices;
import com.Online.Food.Delivery.System.Online.Food.Services.RestaurantServices;
import com.Online.Food.Delivery.System.Online.Food.Services.RestaurantServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final RestaurantServices restaurantServices;
    private final DeliveryServices deliveryServices;
    @GetMapping("/orderDelivered/{orderId}/{otp}")
    public ResponseEntity<?> orderAccept(@PathVariable Long orderId, @PathVariable Integer otp) {
        ResponseEntity<?> orderReady = restaurantServices.OrderDelivered(orderId,otp);
        return ResponseEntity.ok(orderReady);
    }
    @GetMapping("/checkStatus/{deliveryId}")
    public ResponseEntity<?> checkStatus(@PathVariable Long deliveryId) {
        ResponseEntity<?> deliveryById = deliveryServices.getDeliveryById(deliveryId);
        return ResponseEntity.ok(deliveryById);
    }
}
