package com.Online.Food.Delivery.System.Online.Food.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuDTO {
    private Long id;
    private String itemName;
    private Double price;
    private Integer quantity;
}
