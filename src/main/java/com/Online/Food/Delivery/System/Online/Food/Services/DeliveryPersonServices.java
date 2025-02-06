package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.DeliveryPersonDTO;
import org.springframework.http.ResponseEntity;

public interface DeliveryPersonServices {
     ResponseEntity<?> save(DeliveryPersonDTO deliveryPersonDTO);
}
