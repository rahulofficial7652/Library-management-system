package org.example.library_management_system.Controllers;


import lombok.RequiredArgsConstructor;
import org.example.library_management_system.Dtos.AuthDtos.RegisterRequestDTO;
import org.example.library_management_system.Dtos.AuthDtos.RegisterResponseDTO;
import org.example.library_management_system.Services.AuthServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServices authServices;

    @PostMapping("/registerUser")
    public ResponseEntity<RegisterResponseDTO> userRegistrationController(@RequestBody RegisterRequestDTO registerRequestDTO){
        RegisterResponseDTO registerResponseDTO = authServices.registerService(registerRequestDTO);
        return ResponseEntity.ok(registerResponseDTO);

    }
}
