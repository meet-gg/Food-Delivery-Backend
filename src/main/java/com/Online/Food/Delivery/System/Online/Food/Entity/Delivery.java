package com.Online.Food.Delivery.System.Online.Food.Entity;

import com.Online.Food.Delivery.System.Online.Food.DTO.Enums.DStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "delivery")
@Builder
@ToString
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private DStatus deliveryStatus; // Assigned, Out for Delivery, Delivered

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private DeliveryPerson deliveryPerson;
}

