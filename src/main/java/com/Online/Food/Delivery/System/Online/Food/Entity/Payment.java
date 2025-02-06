//package com.Online.Food.Delivery.System.Online.Food.Entity;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "payment")
//public class Payment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Double amount;
//    private String paymentStatus; // Pending, Completed
//
//    @OneToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
//}
//
