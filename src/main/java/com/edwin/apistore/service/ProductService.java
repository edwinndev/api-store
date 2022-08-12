package com.edwin.apistore.service;

import com.edwin.apistore.entity.Product;
import com.edwin.apistore.exception.DataNotFoundException;
import com.edwin.apistore.exception.GeneralException;
import com.edwin.apistore.exception.RequestException;
import com.edwin.apistore.repository.ProductRepository;
import com.edwin.apistore.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@Slf4j
@Service
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    /**
     * Obtener una coleccion de productos de manera paginada
     * @param page
     * @return lista de {@link Product}
     */
    public List<Product> findAll(Pageable page){
        return repository.findAll(page).toList();
    }

    /**
     * Obtener un producto mediante su ID
     * @param id
     * @return {@link Product}
     */
    public Product finById(Long id){
        try{
            return repository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("El producto no existe"));
        } catch(DataNotFoundException e){
            log.info(e.getMessage());
            throw e;
        } catch(Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralException(e.getMessage());
        }
    }

    /**
     * Crear o actualizar un producto en la Base de Datos
     * @param entity {@link Product}
     * @return {@link Product}
     */
    @Transactional
    public Product createOrUpdate(Product entity) {
        try{
            ProductValidator.validate(entity);
            if(entity.getId() == null){
                return repository.save(entity);
            }
            Product product = repository.findById(entity.getId())
                    .orElseThrow(()-> new DataNotFoundException("El producto no existe"));
            product.setCategory(entity.getCategory());
            product.setMark(entity.getMark());
            product.setName(entity.getName());
            product.setSalePrice(entity.getSalePrice());
            return repository.save(product);
        } catch (RequestException | DataNotFoundException e){
            log.info(e.getMessage());
            throw e;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralException(e.getMessage());
        }
    }

    /**
     * Eliminar un producto mediante su ID
     * @param id 
     * @return Mensaje indicando el estado de la operacion
     */
    @Transactional
    public String delete(Long id){
        try {
            Product product = repository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("El producto a eliminar no existe"));
            this.repository.deleteById(product.getId());
            return "Producto eliminado correctamente";
        } catch (DataNotFoundException e) {
            log.info(e.getMessage());           
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralException(e.getMessage());
        }
    }
}