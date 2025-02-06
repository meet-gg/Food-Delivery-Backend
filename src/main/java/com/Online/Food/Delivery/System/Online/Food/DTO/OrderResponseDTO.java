package com.Online.Food.Delivery.System.Online.Food.DTO;

import jakarta.persistence.Column;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private List<OrderItemDTO> orderItems;
}
