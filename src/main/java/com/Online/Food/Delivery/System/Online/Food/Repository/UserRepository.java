package com.Online.Food.Delivery.System.Online.Food.Repository;

import com.Online.Food.Delivery.System.Online.Food.Entity.User;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
@Registered
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
}
