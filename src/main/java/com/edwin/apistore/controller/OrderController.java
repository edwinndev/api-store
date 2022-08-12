package com.edwin.apistore.controller;

import com.edwin.apistore.dto.OrderDto;
import com.edwin.apistore.mapper.OrderMapper;
import com.edwin.apistore.service.OrderService;
import com.edwin.apistore.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService service;
    private final OrderMapper mapper;
    
    @Autowired
    public OrderController(OrderService service, OrderMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }
    
    /**
     * Solicitud para obtener una lista de ordenes en paginas
     * @param page Numero de pagina
     * @param size Tamaño de pagina
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @GetMapping(value = "")
    public ResponseEntity<CustomResponse<List<OrderDto>>> findAll(
            @RequestParam(name="page", required=false, defaultValue="0") int page,
            @RequestParam(name="size", required=false, defaultValue="20") int size
    ){
        Pageable pageRequest = PageRequest.of(page, size);
        List<OrderDto> orders = this.mapper.toDtoList(service.findAll(pageRequest));
        CustomResponse<List<OrderDto>> response = new CustomResponse<>(orders);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Solicitud para obtener una orden mediante su ID
     * @param id ID de la orden
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomResponse<OrderDto>> findById(@PathVariable("id") Long id){
        OrderDto order = this.mapper.toDto(this.service.findById(id));
        CustomResponse<OrderDto> response = new CustomResponse<>(order);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Solicitud para generar una nueva orden
     * @param order
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @PostMapping(value = "")
    public ResponseEntity<CustomResponse<OrderDto>> create(@RequestBody OrderDto order){
        OrderDto orderDto = this.mapper.toDto(this.service.createOrUpdate(this.mapper.toEntity(order)));
        CustomResponse<OrderDto> response = new CustomResponse<>(orderDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Solicitud para actualizar una orden
     * @param order 
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @PutMapping(value = "")
    public ResponseEntity<CustomResponse<OrderDto>> update(@RequestBody OrderDto order){
        OrderDto orderDto = this.mapper.toDto(this.service.createOrUpdate(this.mapper.toEntity(order)));
        CustomResponse<OrderDto> response = new CustomResponse<>(orderDto);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Solicitud para eliminar una orden
     * @param id ID de la orden a eliminar
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CustomResponse<String>> delete(@PathVariable("id") Long id){
        this.service.delete(id);
        CustomResponse<String> response = new CustomResponse<>("Orden eliminada correctamente");
        return ResponseEntity.ok(response);
    }
}