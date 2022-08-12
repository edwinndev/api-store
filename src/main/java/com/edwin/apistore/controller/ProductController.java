package com.edwin.apistore.controller;

import com.edwin.apistore.dto.ProductDto;
import com.edwin.apistore.mapper.ProductMapper;
import com.edwin.apistore.service.ProductService;
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
@RequestMapping(value = "/products")
@RestController
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    @Autowired
    public ProductController(ProductService service, ProductMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Solicitud para obtener una lista de productos en paginas
     * @param page Numero de pagina
     * @param size Tamaño de pagina
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @GetMapping(value = "")
    public ResponseEntity<CustomResponse<List<ProductDto>>> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size
    ){
        Pageable pageRequest = PageRequest.of(page, size);
        List<ProductDto> products = this.mapper.toDtoList(this.service.findAll(pageRequest));
        CustomResponse<List<ProductDto>> response = new CustomResponse<>(products);
        return ResponseEntity.ok(response);
    }

    /**
     * Solicitud para obtener un producto
     * @param id ID del producto a buscar
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomResponse<ProductDto>> findById(@PathVariable(name = "id") Long id){
        ProductDto product = this.mapper.toDto(this.service.finById(id));
        CustomResponse<ProductDto> response = new CustomResponse<>(product);
        return ResponseEntity.ok(response);
    }

    /**
     * Solicitud para guardar un nuevo producto
     * @param body
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @PostMapping(value = "")
    public ResponseEntity<CustomResponse<ProductDto>> create(@RequestBody ProductDto body){
        ProductDto product = this.mapper.toDto(this.service.createOrUpdate(this.mapper.toEntity(body)));
        CustomResponse<ProductDto> response = new CustomResponse<>(product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Solicitud para actualizar un producto
     * @param body
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @PutMapping(value = "")
    public ResponseEntity<CustomResponse<ProductDto>> update(@RequestBody ProductDto body){
        ProductDto product = this.mapper.toDto(this.service.createOrUpdate(this.mapper.toEntity(body)));
        CustomResponse<ProductDto> response = new CustomResponse<>(product);
        return ResponseEntity.ok(response);
    }

    /**
     * Solicitud para eliminar un producto mediante su ID
     * @param id ID del producto a eliminar
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CustomResponse<String>> delete(@PathVariable(value = "id") Long id){
        CustomResponse<String> response = new CustomResponse<>(this.service.delete(id));
        return ResponseEntity.ok(response);
    }
}