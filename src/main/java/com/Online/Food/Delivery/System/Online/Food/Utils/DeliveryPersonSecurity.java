package com.Online.Food.Delivery.System.Online.Food.Utils;

import com.Online.Food.Delivery.System.Online.Food.Entity.DeliveryPerson;
import com.Online.Food.Delivery.System.Online.Food.Entity.User;
import com.Online.Food.Delivery.System.Online.Food.Exceptions.ResourceNotFoundException;
import com.Online.Food.Delivery.System.Online.Food.Repository.DeliveryPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryPersonSecurity {
    private final DeliveryPersonRepository deliveryPersonRepository;
    public boolean isDeliveryBoySaveInformation(){
        User deliveryBoy = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DeliveryPerson byId = deliveryPersonRepository.findByEmail(deliveryBoy.getEmail()).orElseThrow(()-> new ResourceNotFoundException("delivery person not found with email: " + deliveryBoy.getEmail()));
        return true;
    }
}
