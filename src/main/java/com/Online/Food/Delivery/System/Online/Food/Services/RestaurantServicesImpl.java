package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.*;
import com.Online.Food.Delivery.System.Online.Food.Entity.*;
import com.Online.Food.Delivery.System.Online.Food.Exceptions.ResourceNotFoundException;
import com.Online.Food.Delivery.System.Online.Food.Repository.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.Online.Food.Delivery.System.Online.Food.DTO.Enums.DStatus.DELIVERED;
import static com.Online.Food.Delivery.System.Online.Food.DTO.Enums.DStatus.OUT_OF_DELIVERY;

@Service
@RequiredArgsConstructor
public class RestaurantServicesImpl implements RestaurantServices {

    private static final Logger log = LoggerFactory.getLogger(RestaurantServicesImpl.class);
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryStatusRepository deliveryStatusRepository;
    private final OrderItemRepository orderItemRepository;
    private final MailServices mailServices;
    @Getter
    private static Integer OTP;

    @Override
    public ResponseEntity<?> addRestaurant(RestaurantDTO restaurantDTO) {
        try {
            Restaurant restaurant=modelMapper.map(restaurantDTO, Restaurant.class);
            Restaurant savedRestaurant = restaurantRepository.save(restaurant);
            return new ResponseEntity<>(modelMapper.map(savedRestaurant,RestaurantDTO.class), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> assignFoodToRestaurant(Long id,MenuDTO menuDTO) {
        Restaurant restaurant=restaurantRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Restaurant not found"));
        Menu menu=modelMapper.map(menuDTO, Menu.class);
        menu.setRestaurant(restaurant);
        Menu savemenu = menuRepository.save(menu);

        List<Menu> menus = menuRepository.findByRestaurantId(id);
        restaurant.setMenus(menus);
        return new ResponseEntity<>(modelMapper.map(restaurant,RestaurantDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getOrdersByRestaurantId(Long restaurantId) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        List<RestaurantResponseDTO> RestaurantResponse = orders.stream().map(order -> modelMapper.map(order, RestaurantResponseDTO.class)).toList();
        RestaurantResponse.forEach(restaurantResponseDTO -> {
            List<OrderItemDTO> orderItems = restaurantResponseDTO.getOrderItems();
            Double price=0.0;
            Integer quantity=0;
//            orderItems.forEach(orderItem -> {
//                double price=finalPrice;
//                OrderItem byOrderIdAndMenuId = orderItemRepository.findByOrderIdAndMenuId(order.getId(), orderItem.getMenuId());
//                price+=byOrderIdAndMenuId.getTotalPrice();
//
//            });
            for (OrderItemDTO orderItem:orderItems){
                OrderItem byOrderIdAndMenuId = orderItemRepository.findByOrderIdAndMenuId(restaurantResponseDTO.getId(), orderItem.getMenuId());
                //change
                price+=byOrderIdAndMenuId.getTotalPrice();
                quantity+=byOrderIdAndMenuId.getQuantity();
            }
            restaurantResponseDTO.setTotalPrice(price);
            restaurantResponseDTO.setTotalQuantity(quantity);
        });

        return new ResponseEntity<>(RestaurantResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> orderReady(Long orderId){
        Random random=new Random();
        OTP=random.nextInt(10000);
        log.info("OTP is {}",OTP);
        DeliveryStatus delivery = deliveryStatusRepository.findByOrderId(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        delivery.setDeliveryStatus(OUT_OF_DELIVERY);
        Order order = orderRepository.findById(orderId).get();
        mailServices.sendMail(order.getUser().getEmail(),"Verification Code","OTP is : "+OTP);
        deliveryStatusRepository.save(delivery);
        return new ResponseEntity<>("Order Out Of Delivery (Status updated)",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> OrderDelivered(Long orderId,Integer otp){
        if (Objects.equals(otp, OTP)){
            DeliveryStatus delivery = deliveryStatusRepository.findByOrderId(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
            delivery.setDeliveryStatus(DELIVERED);
            deliveryStatusRepository.save(delivery);
            return new ResponseEntity<>("Order Accepted",HttpStatus.OK);
        }
        return new ResponseEntity<>("Enter Valid OTP",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllRestaurants() {
        try {
            List<Restaurant> restaurants = restaurantRepository.findAll();
            List<RestaurantDTO> restaurantDTOList = restaurants.stream().map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class)).toList();
            return new ResponseEntity<>(restaurantDTOList, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
