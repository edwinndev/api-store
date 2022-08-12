package com.edwin.apistore.validator;

import com.edwin.apistore.dto.LoginRequestDto;
import com.edwin.apistore.entity.User;
import com.edwin.apistore.exception.RequestException;

 /** 
  * @author Edwin Vargas
  * @version 1.0
  */
public class UserValidator {
    
    public static void validate(User user){
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()){
            throw new RequestException("El nombre de usuario es un campo obligatorio");
        }
        
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            throw new RequestException("El password en un campo obligatorio");
        }
        
        if(user.getPassword().length() < 5){
            throw new RequestException("Longitud del password muy corta");
        }
        
        if(user.getPassword().length() > 20){
            throw new RequestException("Longitud del password muy larga");
        }
        
        if(user.getEmail() != null){
            if(user.getEmail().length() < 10){
                throw new RequestException("El email es invalido");
            }
        }
    }
    
    public static void login(LoginRequestDto login){
        if(login.getPassword() == null || login.getPassword().isEmpty()){
            throw new RequestException("Password incorrecto");
        }
        
        if(login.getUsername() == null || login.getUsername().trim().isEmpty()){
            throw new RequestException("Nombre de usuario incorrecto");
        }
    }
}