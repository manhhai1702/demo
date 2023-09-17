package com.chum.demo_db.domains.dtos;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
}
