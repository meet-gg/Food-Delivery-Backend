package com.Online.Food.Delivery.System.Online.Food.DTO;

import com.Online.Food.Delivery.System.Online.Food.Entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private Long id;
    private String name;
    private String location;
    private List<Menu> menus;
}
