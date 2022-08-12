package com.edwin.apistore.util;

/**
 * Esta clase contine una estructura estandar adecuada para la respuesta a<br>
 * todas las solictudes de los clientes
 * 
 * @param <T> Carga util de la respuesta
 * 
 * @author Edwin Vargas
 * @version 1.0
 */
public class CustomResponse<T> {
    private final Boolean ok;
    private final String message;
    private final T data;

    public CustomResponse(T data) {
        this.ok = true;
        this.message = "Successful";
        this.data = data;
    }

    public CustomResponse(Boolean ok, String message, T data) {
        this.ok = ok;
        this.message = message;
        this.data = data;
    }

    public Boolean getOk() {
        return ok;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}