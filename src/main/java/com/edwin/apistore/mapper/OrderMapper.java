package com.edwin.apistore.mapper;

import com.edwin.apistore.dto.OrderItemDto;
import com.edwin.apistore.dto.OrderDto;
import com.edwin.apistore.entity.OrderItem;
import com.edwin.apistore.entity.Order;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@AllArgsConstructor
public class OrderMapper extends BaseMapper<Order, OrderDto> {
    private final ProductMapper productMapper;
    private final DateTimeFormatter formatter;

    @Override
    public Order toEntity(OrderDto dto) {
        if(dto == null) return null;
        
        List<OrderItem> items = this.toEntityItems(dto.getItems());
        return Order.builder()
                .id(dto.getId())
                .items(items)
                .totalDto(dto.getTotalDto())
                .totalImport(dto.getTotalImport())
                .build();
    }

    @Override
    public OrderDto toDto(Order entity) {
        if(entity == null) return null;
        
        List<OrderItemDto> items = this.toDtoItems(entity.getItems());
        return OrderDto.builder()
                .id(entity.getId())
                .regDate(entity.getRegDate().format(formatter))
                .items(items)
                .totalDto(entity.getTotalDto())
                .totalImport(entity.getTotalImport())
                .build();
    }
 
    private List<OrderItem> toEntityItems(List<OrderItemDto> list){
        if(list == null) return null;
        
        return list.stream().map(item -> {
            return OrderItem.builder()
                    .id(item.getId())
                    .product(productMapper.toEntity(item.getProduct()))
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .dto(item.getDto())
                    .subtotal(item.getTotal())
                    .build();
        }).collect(Collectors.toList());
    }
    
    private List<OrderItemDto> toDtoItems(List<OrderItem> list){
        if(list == null) return null;
        
        return list.stream().map(item -> {
            return OrderItemDto.builder()
                    .id(item.getId())
                    .product(productMapper.toDto(item.getProduct()))
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .dto(item.getDto())
                    .total(item.getSubtotal())
                    .build();
        }).collect(Collectors.toList());
    }
}