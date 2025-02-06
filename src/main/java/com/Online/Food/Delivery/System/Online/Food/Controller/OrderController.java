package com.Online.Food.Delivery.System.Online.Food.Controller;

import com.Online.Food.Delivery.System.Online.Food.DTO.OrderItemDTO;
import com.Online.Food.Delivery.System.Online.Food.Services.OrderServices;
import com.Online.Food.Delivery.System.Online.Food.Services.OrderServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServices orderServices;

    @PostMapping("/placeOrder/{restaurantId}")
    private ResponseEntity<?> createOrder(@PathVariable Long restaurantId, @RequestBody List<OrderItemDTO> orderItemDTO) {
        ResponseEntity<?> placeOrder = orderServices.placeOrder(restaurantId, orderItemDTO);
        return ResponseEntity.ok(placeOrder);
    }
//    @GetMapping("/getAll")
//    private ResponseEntity<?> getAllOrders() {
//        ResponseEntity<?> allOrder = orderServices.getAllOrder();
//        return ResponseEntity.ok(allOrder);
//    }
    @GetMapping("/getById/{id}")
    private ResponseEntity<?> getOrderById(@PathVariable Long id) {
        ResponseEntity<?> order = orderServices.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/getByUser")
    private ResponseEntity<?> getOrderByUser() {
        ResponseEntity<?> orderByUser = orderServices.OrderByUser();
        return ResponseEntity.ok(orderByUser);
    }
}
