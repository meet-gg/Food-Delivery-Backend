package com.Online.Food.Delivery.System.Online.Food.DTO;

import com.Online.Food.Delivery.System.Online.Food.Entity.Menu;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long menuId;
    private Integer quantity;
}
