package org.example.library_management_system.Services;

import org.example.library_management_system.Dtos.AuthDtos.LoginRequestDTO;
import org.example.library_management_system.Dtos.AuthDtos.LoginResponseDTO;
import org.example.library_management_system.Dtos.AuthDtos.SignupRequestDTO;
import org.example.library_management_system.Dtos.AuthDtos.SignupResponseDTO;
import org.example.library_management_system.Enums.UserEnum;
import org.example.library_management_system.Models.User;
import org.example.library_management_system.Reopsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServices {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private String passwordEncoder;

    // ++++++++++++++++++++++++ User Registration Services ++++++++++++++++++++++++++++++++++++ \\

    public SignupResponseDTO signupServices(SignupRequestDTO signupRequestDTO) {
        if (userRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        User user = new User();
        user.setUsername(signupRequestDTO.getUserName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPassword(signupRequestDTO.getPassword());
        user.setRole(UserEnum.Role.USER);

        User saveUser = userRepository.save(user);

        return new SignupResponseDTO(
                saveUser.getId(),
                saveUser.getUsername(),
                saveUser.getEmail(),
                null
        );
    }


}

