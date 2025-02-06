package com.Online.Food.Delivery.System.Online.Food.Controller;

import com.Online.Food.Delivery.System.Online.Food.DTO.MenuDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.RestaurantDTO;
import com.Online.Food.Delivery.System.Online.Food.Entity.Restaurant;
import com.Online.Food.Delivery.System.Online.Food.Services.RestaurantServices;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantServices restaurantServices;

    @PostMapping("/addRest")
    public ResponseEntity<?> addRest(@RequestBody RestaurantDTO restaurantDTO) {
        ResponseEntity<?> restaurantDTO1=restaurantServices.addRestaurant(restaurantDTO);
        return ResponseEntity.ok(restaurantDTO1);
    }

    @PostMapping("/assignFood/{restaurantId}")
    public ResponseEntity<?> assignFood(@PathVariable Long restaurantId, @RequestBody MenuDTO menuDTO) {
        ResponseEntity<?> restaurantDTO1=restaurantServices.assignFoodToRestaurant(restaurantId,menuDTO);
        return ResponseEntity.ok(restaurantDTO1);
    }

    @GetMapping("/getOrderByRestaurantId/{restaurantId}")
    public ResponseEntity<?> getOrderByRestaurantId(@PathVariable Long restaurantId) {
        ResponseEntity<?> orders = restaurantServices.getOrdersByRestaurantId(restaurantId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orderOutOfDelivery/{orderId}")
    public ResponseEntity<?> orderOutOfDelivery(@PathVariable Long orderId) {
        ResponseEntity<?> orderReady = restaurantServices.orderReady(orderId);
        return ResponseEntity.ok(orderReady);
    }



}
