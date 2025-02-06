package com.Online.Food.Delivery.System.Online.Food.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
//    private String status;
    private Long userId;
    private Long restaurantId;
}
