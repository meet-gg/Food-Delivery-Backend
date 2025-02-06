package com.Online.Food.Delivery.System.Online.Food.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDTO {
    @JsonProperty("orderId")
    private Long id;
    private List<OrderItemDTO> orderItems;
    private Long userId;
    private Double totalPrice;
    private Integer totalQuantity;
}
