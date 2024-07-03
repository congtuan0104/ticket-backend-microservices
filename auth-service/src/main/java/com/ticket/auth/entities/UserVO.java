package com.ticket.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserVO {
    private String id;
    private String avatar;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String role;

}
