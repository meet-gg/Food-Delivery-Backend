package com.Online.Food.Delivery.System.Online.Food.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "delivery_person")
public class DeliveryPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "deliveryPerson", cascade = CascadeType.ALL)
    private List<Delivery> deliveries;
}
