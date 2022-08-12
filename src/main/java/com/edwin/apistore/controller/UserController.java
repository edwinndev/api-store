package com.edwin.apistore.controller;

import com.edwin.apistore.dto.LoginRequestDto;
import com.edwin.apistore.dto.LoginResponseDto;
import com.edwin.apistore.dto.SignupRequestDto;
import com.edwin.apistore.dto.UserDto;
import com.edwin.apistore.mapper.UserMapper;
import com.edwin.apistore.service.UserService;
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
@RequestMapping(value = "/users")
@RestController
public class UserController {
    private final UserService service;
    private final UserMapper mapper;
    
    @Autowired
    public UserController(UserService service, UserMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }
    
    /**
     * Solicitud para obtener una lista de usuarios en paginas
     * @param page Numero de pagina
     * @param size Tamaño de pagina
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @GetMapping(value = "")
    public ResponseEntity<CustomResponse<List<UserDto>>> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size
    ){
        Pageable pageRequest = PageRequest.of(page, size);
        List<UserDto> users = this.mapper.toDtoList(this.service.findAll(pageRequest));
        CustomResponse<List<UserDto>> response = new CustomResponse<>(users);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Solicitud para registrar nuevo usuario
     * @param userRequest
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @PostMapping(value = "/signup")
    public ResponseEntity<CustomResponse<UserDto>> signUp(@RequestBody SignupRequestDto userRequest){
        UserDto user = this.mapper.toDto(this.service.signUp(this.mapper.toUser(userRequest)));
        CustomResponse<UserDto> response = new CustomResponse<>(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Solicitud para autenticar un usuario
     * @param login
     * @return Json con la siguiente estructura: {@link CustomResponse}
     */
    @GetMapping(value = "/login")
    public ResponseEntity<CustomResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto login){
        LoginResponseDto loginResponse = this.service.login(login);
        CustomResponse<LoginResponseDto> response = new CustomResponse<>(loginResponse);
        return ResponseEntity.ok(response);
    }
}