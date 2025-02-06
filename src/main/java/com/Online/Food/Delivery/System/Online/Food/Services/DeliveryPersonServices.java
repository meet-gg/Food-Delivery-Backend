package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.DeliveryPersonDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.DeliveryPersonResponseDTO;
import com.Online.Food.Delivery.System.Online.Food.Entity.DeliveryPerson;
import com.Online.Food.Delivery.System.Online.Food.Entity.User;
import com.Online.Food.Delivery.System.Online.Food.Repository.DeliveryPersonRepository;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryPersonServices {
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<?> save(DeliveryPersonDTO deliveryPersonDTO) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            DeliveryPerson deliveryPerson=modelMapper.map(deliveryPersonDTO, DeliveryPerson.class);
            deliveryPerson.setEmail(user.getEmail());
            deliveryPerson.setName(user.getName());
            DeliveryPerson savedPerson = deliveryPersonRepository.save(deliveryPerson);
            DeliveryPersonResponseDTO deliveryPersonResponseDTO=modelMapper.map(savedPerson, DeliveryPersonResponseDTO.class);
            return new ResponseEntity<>(deliveryPersonResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
