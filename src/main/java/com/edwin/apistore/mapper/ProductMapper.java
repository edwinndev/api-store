package com.edwin.apistore.mapper;

import com.edwin.apistore.dto.ProductDto;
import com.edwin.apistore.entity.Product;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
public class ProductMapper extends BaseMapper<Product, ProductDto> {

    @Override
    public Product toEntity(ProductDto dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .category(dto.getCategory())
                .mark(dto.getMark())
                .salePrice(dto.getSalePrice())
                .build();
    }

    @Override
    public ProductDto toDto(Product entity) {
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(entity.getCategory())
                .mark(entity.getMark())
                .salePrice(entity.getSalePrice())
                .build();
    }
}