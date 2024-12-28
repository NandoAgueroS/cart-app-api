package com.cart_app.user_service.dto;

import lombok.*;

import java.util.Set;

@Value
@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class UserDTO {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Set<String> roles;
}
