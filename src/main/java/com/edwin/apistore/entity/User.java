package com.edwin.apistore.entity;

import lombok.*;
import javax.persistence.*;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "username", nullable = false, length = 20)
    private String username;
    
    @Column(name = "names", length = 50)
    private String names;
    
    @Column(name = "p_lastname", length = 30)
    private String pLastname;
    
    @Column(name = "m_lastname", length = 30)
    private String mLastname;
    
    @Column(name = "email", length = 60)
    private String email;
    
    @Column(name = "phone", length = 60)
    private String phone;
    
    @Column(name = "password", nullable = false, length = 150)
    private String password;
}