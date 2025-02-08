package com.Online.Food.Delivery.System.Online.Food.Controller;

import com.Online.Food.Delivery.System.Online.Food.DTO.MenuDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.RestaurantDTO;
import com.Online.Food.Delivery.System.Online.Food.Services.DeliveryServices;
import com.Online.Food.Delivery.System.Online.Food.Services.RestaurantServices;
import com.Online.Food.Delivery.System.Online.Food.Services.RestaurantServicesImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantServices restaurantServices;
    private final DeliveryServices deliveryServices;

    @PostMapping("/addRest")
    @Operation(summary = "add Restaurant")  // for use of Swagger ui
    public ResponseEntity<?> addRest(@RequestBody RestaurantDTO restaurantDTO) {
        ResponseEntity<?> restaurantDTO1= restaurantServices.addRestaurant(restaurantDTO);
        return ResponseEntity.ok(restaurantDTO1);
    }

    @PostMapping("/assignFood/{restaurantId}")
    @PreAuthorize("@restaurantSecurity.isValidRestaurantAddFood(#restaurantId)")
    public ResponseEntity<?> assignFood(@PathVariable Long restaurantId, @RequestBody MenuDTO menuDTO) {
        ResponseEntity<?> restaurantDTO1= restaurantServices.assignFoodToRestaurant(restaurantId,menuDTO);
        return ResponseEntity.ok(restaurantDTO1);
    }

    @GetMapping("/getOrderByRestaurantId/{restaurantId}")
    public ResponseEntity<?> getOrderByRestaurantId(@PathVariable Long restaurantId) {
        ResponseEntity<?> orders = restaurantServices.getOrdersByRestaurantId(restaurantId);
        return ResponseEntity.ok(orders);
    }


    @GetMapping("/orderOutOfDelivery/{orderId}")
    @PreAuthorize("@restaurantSecurity.isUserOrderExist(#orderId)")
    public ResponseEntity<?> orderOutOfDelivery(@PathVariable Long orderId) {
        ResponseEntity<?> orderReady = restaurantServices.orderReady(orderId);
        return ResponseEntity.ok(orderReady);
    }

    @GetMapping("/checkStatus/{orderId}")
    @PreAuthorize("@restaurantSecurity.isUserOrderExist(#orderId)")
    public ResponseEntity<?> checkStatus(@PathVariable Long orderId) {
        ResponseEntity<?> deliveryById = deliveryServices.getDeliveryById(orderId);
        return ResponseEntity.ok(deliveryById);
    }
}
