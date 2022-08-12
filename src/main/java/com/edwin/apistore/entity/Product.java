package com.edwin.apistore.entity;

import lombok.*;
import javax.persistence.*;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "category", length = 20, nullable = false)
    private String category;

    @Column(name = "mark", length = 20, nullable = false)
    private String mark;

    @Column(name = "price")
    private Double salePrice;
}
