package com.edwin.apistore.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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
@Table(name = "ORDERS")
public class Order implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "reg_date", nullable = false, updatable = false)
    private LocalDateTime regDate;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> items;
    
    @Column(name = "dcto")
    private Double totalDto;
    
    @Column(name = "total")
    private Double totalImport;
}