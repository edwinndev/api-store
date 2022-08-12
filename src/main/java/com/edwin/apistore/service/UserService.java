package com.edwin.apistore.service;

import com.edwin.apistore.dto.LoginRequestDto;
import com.edwin.apistore.dto.LoginResponseDto;
import com.edwin.apistore.entity.User;
import com.edwin.apistore.exception.DataNotFoundException;
import com.edwin.apistore.exception.RequestException;
import com.edwin.apistore.mapper.UserMapper;
import com.edwin.apistore.repository.UserRepository;
import com.edwin.apistore.validator.UserValidator;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@Slf4j
@Service
public class UserService {
    @Value("${store.api.secret}")
    private String SECRET_KEY;
    
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    
    @Autowired
    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder encoder){
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }
    
    public List<User> findAll(Pageable page){
        return this.repository.findAll(page).toList();
    }
    
    /**
     * Registrar un nuevo usuario
     * @param user
     * @return {@link User}
     */
    @Transactional
    public User signUp(User user){
        try {
            UserValidator.validate(user);
            User response = this.repository.findByUsername(user.getUsername())
                    .orElse(null);
            if(response != null) throw new RequestException("El usuario ya existe");
            final String PASSWORD_ENCODE = this.encoder.encode(user.getPassword());
            user.setPassword(PASSWORD_ENCODE);
            return this.repository.save(user);
        } catch (RequestException | DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        }
    }
    
    /**
     * Autenticar un usuario
     * @param login
     * @return {@link LoginResponseDto}
     */
    public LoginResponseDto login(LoginRequestDto login){
        try {
            UserValidator.login(login);
            User user = this.repository.findByUsername(login.getUsername())
                .orElseThrow(() -> new DataNotFoundException("Usuario o password incorrecto"));
            
            if(!this.encoder.matches(login.getPassword(), user.getPassword())) 
                throw new RequestException("Usuario o password incorrecto");
            
            final String TOKEN = this.makeToken(user);
            return LoginResponseDto.builder()
                    .user(this.mapper.toDto(user))
                    .token(TOKEN)
                    .build();
        } catch (RequestException | DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        }
    }
    
    /**
     * Crear un TOKEN de acceso del usuario autenticado
     * @param USER
     * @return Token de acceso
     */
    private String makeToken(final User USER){
        Date date = new Date();
        Date expired = new Date(date.getTime() + ( 1000 * 60 * 60 * 24));
        return Jwts.builder()
                .setSubject(USER.getUsername())
                .setIssuedAt(date)
                .setIssuer("ApiStore")
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    
    /**
     * Validar si un TOKEN es correcto
     * @param TOKEN
     * @return true=valido, false=invalido
     */
    public boolean isTokenValid(final String TOKEN){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(TOKEN);
            return true;
        } catch (UnsupportedJwtException e){
            log.error("El token es incorecto");
        } catch (MalformedJwtException e){
            log.error("El token es incorrecto");
        } catch (SignatureException e){
            log.error("Error de firma del token");
        } catch (ExpiredJwtException e){
            log.error("Token expirado");
        }
        return false;
    }

    /**
     * Obtener el Sujeto del cuerpo del TOKEN
     * @param TOKEN
     * @return Subject del TOKEN
     */
    public String getSubjectFromTokenPayload(final String TOKEN) {
        try{
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(TOKEN).getBody().getSubject();
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e){
            log.error(e.getMessage());
            throw new RequestException("Token invalido");
        }
    }   
}