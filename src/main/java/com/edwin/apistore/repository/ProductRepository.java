package com.edwin.apistore.repository;

import com.edwin.apistore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}