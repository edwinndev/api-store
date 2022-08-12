package com.edwin.apistore.validator;

import com.edwin.apistore.entity.OrderItem;
import com.edwin.apistore.entity.Order;
import com.edwin.apistore.exception.RequestException;

 /** 
  * @author Edwin Vargas
  * @version 1.0
  */
public class OrderValidator {

    public static void validate(Order order){
        if(order.getItems()== null || order.getItems().isEmpty()){
            throw new RequestException("La orden es invalida, debe tener al menos un producto para ser completada");
        }
        
        for(OrderItem item : order.getItems()){
            if(item.getQuantity() == null || item.getQuantity() < 1){
                throw new RequestException("La cantidad para cada producto es obligatirio");
            }
            if(item.getProduct() == null || item.getProduct().getId() == null){
                throw new RequestException("Es necesario al menos un producto para completar la order");
            }
            if(item.getId() != null){
                throw new RequestException("Error, el ID del item(Detalle) no forma parte de la solicitud");
            }
        }
    }
    
}