package com.edwin.apistore.config;

import com.edwin.apistore.mapper.OrderMapper;
import com.edwin.apistore.mapper.ProductMapper;
import com.edwin.apistore.mapper.UserMapper;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@Configuration
public class MapperConfiguration {
    @Value("${store.api.date}")
    private String pattern;
    
    @Bean
    public ProductMapper getProductMapper(){
        return new ProductMapper();
    }
    
    @Bean
    public OrderMapper getOrderMapper(){
        return new OrderMapper(this.getProductMapper(), DateTimeFormatter.ofPattern(pattern));
    }
    
    @Bean
    public UserMapper getUserMapper(){
        return new UserMapper();
    }
}