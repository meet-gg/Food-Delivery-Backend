package com.Online.Food.Delivery.System.Online.Food.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonResponseDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
}
