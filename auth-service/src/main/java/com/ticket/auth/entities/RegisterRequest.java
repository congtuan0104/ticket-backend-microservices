package com.ticket.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String avatar;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String role;
}
