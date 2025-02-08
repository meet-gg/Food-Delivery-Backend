package com.Online.Food.Delivery.System.Online.Food.Utils;

import com.Online.Food.Delivery.System.Online.Food.Entity.Order;
import com.Online.Food.Delivery.System.Online.Food.Entity.Restaurant;
import com.Online.Food.Delivery.System.Online.Food.Entity.User;
import com.Online.Food.Delivery.System.Online.Food.Exceptions.ResourceNotFoundException;
import com.Online.Food.Delivery.System.Online.Food.Repository.OrderRepository;
import com.Online.Food.Delivery.System.Online.Food.Repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantSecurity {

    // this is use for je user ae je restaurant ma order karo hoy aej restaurant valo order accept kari shake
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    public boolean isUserRestaurantExist() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return restaurantRepository.existsByUserId(user.getId());
    }

    public boolean isUserOrderExist(Long orderId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = orderRepository.findById(orderId).get();
        Long restaurantId = order.getRestaurant().getId();
        if (user.getRestaurant()==null){
            throw new ResourceNotFoundException("Restaurant not found with user id "+user.getId());
        }
        return user.getRestaurant().getId().equals(restaurantId);
    }

    public boolean isValidRestaurantAddFood(Long restaurantId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getRestaurant()==null){
            throw new ResourceNotFoundException("Restaurant not found with user id "+user.getId());
        }
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        return restaurant.getId().equals(user.getRestaurant().getId());
    }
}
