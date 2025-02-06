package com.Online.Food.Delivery.System.Online.Food.Repository;

import com.Online.Food.Delivery.System.Online.Food.Entity.Menu;
import com.Online.Food.Delivery.System.Online.Food.Entity.Order;
import com.Online.Food.Delivery.System.Online.Food.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
//    List<Menu> findByOrderId(Long orderId);
    List<OrderItem> findByOrder(Order order);
    OrderItem findByOrderIdAndMenuId(Long orderId, Long menuId);
}
