package com.edwin.apistore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SignupRequestDto {
    private String username;
    private String names;
    private String plastname;
    private String mlastname;
    private String email;
    private String phone;
    private String password;
}