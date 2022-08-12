package com.edwin.apistore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemDto {
    private Long id;
    private ProductDto product;
    private Double price;
    private Double quantity;
    private Double dto;
    private Double total;
}