package com.edwin.apistore.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase abstracta para convertir una Entidad a un DTO
 * Los DTO son clases con atributos similares a los de una entidad, con la diferencia que estos son 
 * los necesario para mostrar a la aplicacion Cliente <br>
 * Cada clase Entity cuenta con una clase DTO, esta ultima debe extender(heredar) de esta clase
 * @param <E> ENTITY
 * @param <D> DTO
 * @author Edwin Vargas
 * @version 1.0
 */
public abstract class BaseMapper<E, D> {
 
    public abstract E toEntity(D dto);

    public abstract D toDto(E entity);
    
    public List<E> toEntityList(List<D> dtos){
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<D> toDtoList(List<E> entities){
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}