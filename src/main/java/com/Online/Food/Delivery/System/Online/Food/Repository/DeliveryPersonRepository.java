package com.Online.Food.Delivery.System.Online.Food.Repository;

import com.Online.Food.Delivery.System.Online.Food.Entity.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
    DeliveryPerson findByEmail(String email);
}
