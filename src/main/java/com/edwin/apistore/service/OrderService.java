package com.edwin.apistore.service;

import com.edwin.apistore.entity.OrderItem;
import com.edwin.apistore.entity.Order;
import com.edwin.apistore.entity.Product;
import com.edwin.apistore.exception.DataNotFoundException;
import com.edwin.apistore.exception.GeneralException;
import com.edwin.apistore.exception.RequestException;
import com.edwin.apistore.repository.OrderRepository;
import com.edwin.apistore.repository.ProductRepository;
import com.edwin.apistore.validator.OrderValidator;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edwin.apistore.repository.OrderItemRepository;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@Slf4j
@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderItemRepository itemRepository;
    private final ProductRepository productRepository;
    
    @Autowired
    public OrderService(OrderRepository repository, OrderItemRepository itemRepository, ProductRepository productRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    /**
     * Obtener una coleccion de ordenes paginadas
     * @param page
     * @return lista de {@link Order}
     */
    public List<Order> findAll(Pageable page) {
        return this.repository.findAll(page).toList();
    }

    /**
     * Obtener una orden mediante su ID
     * @param id
     * @return {@link Order}
     */
    public Order findById(Long id) {
        try {
            return this.repository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException("La orden que intenta buscar no existe"));
        } catch (RequestException | DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralException(e.getMessage());
        }
    }

    /**
     * Crear o actualizar una orden en la BBDD
     * @param order
     * @return {@link Order}
     */
    @Transactional
    public Order createOrUpdate(Order order) {
        try {
            OrderValidator.validate(order);
            double totalDcto = 0.0;
            double totalImport = 0.0;
            for(OrderItem item : order.getItems()){
                Product product = this.productRepository.findById(item.getProduct().getId())
                        .orElseThrow(() -> new DataNotFoundException("El producto que intenta grabar para esta orden no existe"));
                item.setProduct(product);
                item.setOrder(order);
                item.setPrice(product.getSalePrice());
                totalDcto += item.getDto();
                double subtotal = item.getQuantity() * item.getPrice();
                item.setSubtotal(subtotal);
                totalImport += subtotal;
            }
            order.setTotalDto(totalDcto);
            order.setTotalImport(totalImport - (totalImport * totalDcto / 100));
            
            if (order.getId() == null) {
                order.setRegDate(LocalDateTime.now());
                return this.repository.save(order);
            }

            Order db = this.repository.findById(order.getId())
                    .orElseThrow(() -> new DataNotFoundException("La orden que intenta buscar no existe"));
            List<OrderItem> details = db.getItems();
            details.removeAll(order.getItems());
            this.itemRepository.deleteAll(details);
            order.setRegDate(db.getRegDate());
            return this.repository.save(order);
        } catch (RequestException | DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralException(e.getMessage());
        }
    }
    
    /**
     * Eliminar una Orden de la BBDD
     * @param id
     * @return Mensaje indicando el estado de la operacion
     */
    @Transactional
    public String delete(Long id){
        try {
            Order order = this.repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("La orden a eliminar no existe"));
            this.repository.deleteById(order.getId());
            return "La orden se ha eliminado con exito";
        } catch (RequestException | DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralException(e.getMessage());
        }
    }
}