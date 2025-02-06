package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.MenuDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.OrderItemDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.OrderItemsDTO;
import com.Online.Food.Delivery.System.Online.Food.Entity.*;
import com.Online.Food.Delivery.System.Online.Food.Exceptions.ResourceNotFoundException;
import com.Online.Food.Delivery.System.Online.Food.Repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.Online.Food.Delivery.System.Online.Food.DTO.Enums.DStatus.*;

@Service
@RequiredArgsConstructor
public class OrderServicesImpl implements OrderServices {

    private static final Logger log = LoggerFactory.getLogger(OrderServicesImpl.class);
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryStatusRepository deliveryStatusRepository;

    private final DeliveryRepository deliveryRepository;
//    private final Map<Long,List<Menu>> map=new HashMap<>();

    @Override
    public ResponseEntity<?> placeOrder(Long restaurantId, List<OrderItemDTO> orderItemDTO) {
        try {
            Order order=new Order();
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));
            order.setUser(user);
//            order.setUser(null);
            order.setRestaurant(restaurant);

            for (OrderItemDTO orderMenu:orderItemDTO){
                Menu menu=menuRepository.findById(orderMenu.getMenu().getId()).orElseThrow(() -> new ResourceNotFoundException("Menu Not Found"));
//change
                OrderItem orderItem=new OrderItem(menu,orderMenu.getQuantity());
                order.addOrderItem(orderItem);
            }
            orderRepository.save(order);

            //for delivery Purpose
            Delivery delivery=Delivery.builder()
                    .order(order).build();
            delivery.setDeliveryStatus(ASSIGNED_SOON);
            deliveryRepository.save(delivery);

            DeliveryStatus deliveryStatus = modelMapper.map(delivery, DeliveryStatus.class);
            deliveryStatusRepository.save(deliveryStatus);

            //
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getAllOrder() {
            List<Order> orders = orderRepository.findAll();
        List<List<Menu>> list = orders.stream().map(order -> orderItemRepository.findByOrder(order).stream().map(orderItem -> orderItem.getMenu()).toList()).toList();
        List<List<MenuDTO>> menuDto=list.stream().map(menus -> menus.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList()).toList();
        List<OrderItemsDTO> orderItemsDTOS=new ArrayList<>();
        int i=0;
        for (List<MenuDTO> menus: menuDto) {
            OrderItemsDTO orderItemsDTO = new OrderItemsDTO();
            orderItemsDTO.setOrderId(orders.get(i).getId());
            int finalI = i;
            menus.forEach(menuDTO -> {
                OrderItem byOrderIdAndMenuId = orderItemRepository.findByOrderIdAndMenuId(orders.get(finalI).getId(), menuDTO.getId());
                if (byOrderIdAndMenuId != null) {
                    menuDTO.setQuantity(byOrderIdAndMenuId.getQuantity());
                }
            });
            orderItemsDTO.setMenus(menus);
            orderItemsDTOS.add(orderItemsDTO);
//            map.put(orders.get(i).getId(),menus);
            i++;
        }
        return new ResponseEntity<>(orderItemsDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderItemsDTO> getOrderById(Long id) {
//
//        List<Menu> orders = map.get(id);
//        return new ResponseEntity<>(orders, HttpStatus.OK);
        List<Order> orders = orderRepository.findAll();
        List<List<Menu>> list = orders.stream().map(order -> orderItemRepository.findByOrder(order).stream().map(orderItem -> orderItem.getMenu()).toList()).toList();
        List<List<MenuDTO>> menudto=list.stream().map(menus -> menus.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList()).toList();
        int i=0;
        OrderItemsDTO orderItemsDTO = new OrderItemsDTO();
        for (List<MenuDTO> menus: menudto) {
            if (orders.get(i).getId().equals(id)) {
                orderItemsDTO.setOrderId(orders.get(i).getId());

                menus.forEach(menuDTO -> {
                    OrderItem byOrderIdAndMenuId = orderItemRepository.findByOrderIdAndMenuId(id, menuDTO.getId());
                    if (byOrderIdAndMenuId != null) {
                        menuDTO.setQuantity(byOrderIdAndMenuId.getQuantity());
                    }
                });

                orderItemsDTO.setMenus(menus);
                Double totalPrice=0.0;
                for (MenuDTO menu: menus) {
                    totalPrice+=menu.getPrice()*menu.getQuantity();
                }
                orderItemsDTO.setTotalPrice(totalPrice);
                break;
            }
            i++;
        }
        return new ResponseEntity<>(orderItemsDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> OrderByUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orderByUser = orderRepository.findByUser(user);
        List<OrderItemsDTO> orderItemsDTOS=new ArrayList<>();
        for(Order order:orderByUser){
            ResponseEntity<OrderItemsDTO> response=getOrderById(order.getId());
            orderItemsDTOS.add(response.getBody());
        }
        return new ResponseEntity<>(orderItemsDTOS, HttpStatus.OK);
    }
}
