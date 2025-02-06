package com.Online.Food.Delivery.System.Online.Food.Repository;

import com.Online.Food.Delivery.System.Online.Food.Entity.Delivery;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByOrderId(Long orderId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Delivery d WHERE d.order.id = :orderId")
    void deleteByOrderId(@Param("orderId") Long orderId);
}
