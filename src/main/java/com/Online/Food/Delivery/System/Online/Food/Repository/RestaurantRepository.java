package com.Online.Food.Delivery.System.Online.Food.Repository;

import com.Online.Food.Delivery.System.Online.Food.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByUserId(Long id);
}
