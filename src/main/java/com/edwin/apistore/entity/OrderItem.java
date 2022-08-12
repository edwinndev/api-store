package com.edwin.apistore.entity;

import lombok.*;
import javax.persistence.*;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "fk_order", nullable = false)
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "fk_product", nullable = false)
    private Product product;
    
    @Column(name = "price")
    private Double price;
    
    @Column(name = "quantity")
    private Double quantity;
    
    @Column(name = "dcto")
    private Double dto;
    
    @Column(name = "total")
    private Double subtotal;
}