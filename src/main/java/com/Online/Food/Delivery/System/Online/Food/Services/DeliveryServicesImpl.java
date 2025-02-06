package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.DeliveryResponseDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.DeliveryStatusDTO;
import com.Online.Food.Delivery.System.Online.Food.Entity.Delivery;
import com.Online.Food.Delivery.System.Online.Food.Entity.DeliveryPerson;
import com.Online.Food.Delivery.System.Online.Food.Entity.DeliveryStatus;
import com.Online.Food.Delivery.System.Online.Food.Entity.User;
import com.Online.Food.Delivery.System.Online.Food.Exceptions.ResourceNotFoundException;
import com.Online.Food.Delivery.System.Online.Food.Repository.DeliveryPersonRepository;
import com.Online.Food.Delivery.System.Online.Food.Repository.DeliveryRepository;
import com.Online.Food.Delivery.System.Online.Food.Repository.DeliveryStatusRepository;
import com.Online.Food.Delivery.System.Online.Food.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.Online.Food.Delivery.System.Online.Food.DTO.Enums.DStatus.*;

@Service
@RequiredArgsConstructor
public class DeliveryServicesImpl implements DeliveryServices{
    private static final Logger log = LoggerFactory.getLogger(DeliveryServicesImpl.class);
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final OrderServicesImpl orderServicesImpl;
    private final ModelMapper modelMapper;
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final DeliveryStatusRepository deliveryStatusRepository;

    @Override
    public ResponseEntity<?> getAllOrder(){
        List<Delivery> orders = deliveryRepository.findAll();
        List<DeliveryResponseDTO> orderss = orders.stream().map(delivery -> modelMapper.map(delivery, DeliveryResponseDTO.class)).toList();

        return new ResponseEntity<>(orderss, HttpStatus.OK);
    }

    @Override
    public void removeDeliverOrder(Long orderId){
        try {
            Delivery delivery = deliveryRepository.findByOrderId(orderId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
            log.info("helooooooooooo {}",delivery);
            deliveryRepository.deleteByOrderId(orderId);
//            new ResponseEntity<>("Delete success", HttpStatus.OK);
        } catch (ResponseStatusException e) {
            new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // user check the delivery status with hel of delivery id
    @Override
    public ResponseEntity<?> getDeliveryById(Long orderId){
        DeliveryStatus delivery = deliveryStatusRepository.findByOrderId(orderId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        DeliveryStatusDTO checkStatus = modelMapper.map(delivery, DeliveryStatusDTO.class);
        if (delivery.getDeliveryPersonId()!=null){
        Optional<DeliveryPerson> deliveryPerson = deliveryPersonRepository.findById(delivery.getDeliveryPersonId());
        checkStatus.setDeliveryPerson(deliveryPerson.get());
        }

        return new ResponseEntity<>(checkStatus,HttpStatus.OK);
    }

    // if user is delivery boy so update status
    @Override
    public ResponseEntity<?> assignDelivery(Long deliveryId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Delivery delivery=deliveryRepository.findById(deliveryId).orElseThrow(()-> new ResourceNotFoundException("Delivery not found"));
        delivery.setDeliveryStatus(ASSIGNED);
        delivery.setDeliveryPerson(deliveryPersonRepository.findByEmail(user.getEmail()));
        deliveryRepository.save(delivery);
        DeliveryStatus deliveryStatus=deliveryStatusRepository.findById(deliveryId).orElseThrow(()-> new ResourceNotFoundException("Delivery id not found"));
        deliveryStatus.setDeliveryStatus(ASSIGNED);
        deliveryStatus.setDeliveryPersonId(delivery.getDeliveryPerson().getId());
        deliveryStatusRepository.save(deliveryStatus);

//        remove from list because of for  when user accepted then delete
        Long orderId = delivery.getOrder().getId();
        removeDeliverOrder(orderId);

        DeliveryStatusDTO deliveryStatusDTO = modelMapper.map(delivery, DeliveryStatusDTO.class);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
}