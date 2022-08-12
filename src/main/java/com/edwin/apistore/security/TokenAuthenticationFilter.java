package com.edwin.apistore.security;

import com.edwin.apistore.entity.User;
import com.edwin.apistore.exception.DataNotFoundException;
import com.edwin.apistore.repository.UserRepository;
import com.edwin.apistore.service.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String TOKEN = this.getTokenFromHeaderRequest(request);
            if(StringUtils.hasText(TOKEN) && this.userService.isTokenValid(TOKEN)){
                final String username = this.userService.getSubjectFromTokenPayload(TOKEN).trim();
                final User user = this.userRepository.findByUsername(username)
                        .orElseThrow(() -> new DataNotFoundException("El usuario no existe"));
                UserPrincipal userPrincipal = UserPrincipal.get(user);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Obtener el TOKEN de la cabecera de la solicitud 
     * @param request
     * @return Token
     */
    private String getTokenFromHeaderRequest(HttpServletRequest request) {
        final String BEARER_TOKEN = request.getHeader("Authorization");
        if(StringUtils.hasText(BEARER_TOKEN) && BEARER_TOKEN.startsWith("Bearer ")){
            return BEARER_TOKEN.substring(7, BEARER_TOKEN.length());
        }
        return null;
    }   
}