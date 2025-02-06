package com.Online.Food.Delivery.System.Online.Food.DTO;

import com.Online.Food.Delivery.System.Online.Food.DTO.Enums.DStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryStatusCheckDTO {
    private Long id;
    private Long orderId;
    private DStatus deliveryStatus;
}
