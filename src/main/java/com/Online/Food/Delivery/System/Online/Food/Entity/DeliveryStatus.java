package com.Online.Food.Delivery.System.Online.Food.Entity;

import com.Online.Food.Delivery.System.Online.Food.DTO.Enums.DStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class DeliveryStatus {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private DStatus deliveryStatus; // Assigned, Out for Delivery, Delivered

    private Long orderId;

    private Long deliveryPersonId;
}
