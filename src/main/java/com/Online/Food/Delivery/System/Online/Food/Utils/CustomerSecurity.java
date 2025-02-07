package com.Online.Food.Delivery.System.Online.Food.Utils;

import com.Online.Food.Delivery.System.Online.Food.Entity.Order;
import com.Online.Food.Delivery.System.Online.Food.Entity.User;
import com.Online.Food.Delivery.System.Online.Food.Repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerSecurity {
    private final OrderRepository orderRepository;
    public boolean isOrderExist(Long orderId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orders = orderRepository.findByUser(user);
        Order byId = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));
        return orders.contains(byId);
    }
}
