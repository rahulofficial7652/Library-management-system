package org.example.library_management_system.Dtos.AuthDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseDTO {
    private UUID id;
    private String userNme;
    private String email;
    private String token;
}
