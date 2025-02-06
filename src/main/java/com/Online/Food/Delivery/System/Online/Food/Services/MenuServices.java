package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.MenuDTO;
import org.springframework.http.ResponseEntity;

public interface MenuServices {
     ResponseEntity<?> createMenu(MenuDTO menuDTO);
}
