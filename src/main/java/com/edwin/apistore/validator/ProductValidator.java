package com.edwin.apistore.validator;

import com.edwin.apistore.entity.Product;
import com.edwin.apistore.exception.RequestException;

 /** 
  * @author Edwin Vargas
  * @version 1.0
  */
public class ProductValidator {

    public static void validate(Product product){
        if(product.getName() == null || product.getName().isEmpty()){
            throw new RequestException("El nombre del producto es invalido");
        }
        if(product.getCategory() == null || product.getCategory().isEmpty()){
            throw new RequestException("La categoria del producto es invalido");
        }

        if(product.getSalePrice() == null || product.getSalePrice() < 0){
            throw new RequestException("El precio del producto es invalido");
        }
    }
}