package com.Online.Food.Delivery.System.Online.Food.Controller;

import com.Online.Food.Delivery.System.Online.Food.DTO.DeliveryPersonDTO;
import com.Online.Food.Delivery.System.Online.Food.Services.DeliveryPersonServices;
import com.Online.Food.Delivery.System.Online.Food.Services.DeliveryServices;
import com.Online.Food.Delivery.System.Online.Food.Services.DeliveryServicesImpl;
import com.Online.Food.Delivery.System.Online.Food.Services.RestaurantServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery-person")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryServices deliveryServices;
    private final DeliveryPersonServices deliveryPersonServices;
    private final RestaurantServices restaurantServices;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody DeliveryPersonDTO deliveryPerson) {
        ResponseEntity<?> save = deliveryPersonServices.save(deliveryPerson);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/orderDelivered/{orderId}/{otp}")
    @PreAuthorize("@deliveryPersonSecurity.isDeliveryBoySaveInformation()")
    public ResponseEntity<?> orderAccept(@PathVariable Long orderId, @PathVariable Integer otp) {
        ResponseEntity<?> orderReady = restaurantServices.OrderDelivered(orderId,otp);
        return ResponseEntity.ok(orderReady);
    }

    @GetMapping("/getAll")
    @PreAuthorize("@deliveryPersonSecurity.isDeliveryBoySaveInformation()")
    public ResponseEntity<?> getAll() {
        ResponseEntity<?> allOrder = deliveryServices.getAllOrder();
        return ResponseEntity.ok(allOrder.getBody());
    }

//    @DeleteMapping("/deleteByOrderId/{id}")
//    public ResponseEntity<?> deleteByOrderId(@PathVariable Long id) {
//        ResponseEntity<?> order = deliveryServices.removeDeliverOrder(id);
//        return ResponseEntity.ok(order);
//    }

    @GetMapping("/assignD/{deliveryId}")
    @PreAuthorize("@deliveryPersonSecurity.isDeliveryBoySaveInformation()")
    public ResponseEntity<?> assignDelivery(@PathVariable Long deliveryId) {
        ResponseEntity<?> order = deliveryServices.assignDelivery(deliveryId);
        return ResponseEntity.ok(order);
    }

}
