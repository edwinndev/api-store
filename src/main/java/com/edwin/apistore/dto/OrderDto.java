package com.edwin.apistore.dto;

import java.util.List;
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
public class OrderDto {
    private Long id;
    private String regDate;
    private List<OrderItemDto> items;
    private Double totalDto;
    private Double totalImport;
}