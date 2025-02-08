package com.Online.Food.Delivery.System.Online.Food.Controller;

import com.Online.Food.Delivery.System.Online.Food.Services.DeliveryServices;
import com.Online.Food.Delivery.System.Online.Food.Services.MenuServices;
import com.Online.Food.Delivery.System.Online.Food.Services.RestaurantServices;
import com.Online.Food.Delivery.System.Online.Food.Services.RestaurantServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final MenuServices menuServices;

    @GetMapping("/checkStatus/{orderId}")
    @PreAuthorize("@customerSecurity.isOrderExist(#orderId)")
    public ResponseEntity<?> checkStatus(@PathVariable Long orderId) {
        ResponseEntity<?> deliveryById = deliveryServices.getDeliveryById(orderId);
        return ResponseEntity.ok(deliveryById);
    }

    @GetMapping("/getMenuByRestaurantId/{restaurantId}")
    public ResponseEntity<?> getMenuByRestaurantId(@PathVariable Long restaurantId) {
        ResponseEntity<?> menuByRestaurantId = menuServices.getMenuByRestaurantId(restaurantId);
        return ResponseEntity.ok(menuByRestaurantId);
    }
}
