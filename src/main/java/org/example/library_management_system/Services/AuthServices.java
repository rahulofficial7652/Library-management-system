package org.example.library_management_system.Services;

import lombok.RequiredArgsConstructor;
import org.example.library_management_system.Dtos.AuthDtos.RegisterRequestDTO;
import org.example.library_management_system.Dtos.AuthDtos.RegisterResponseDTO;
import org.example.library_management_system.Enums.UserEnum;
import org.example.library_management_system.Models.User;
import org.example.library_management_system.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServices {

    private final UserRepository userRepository;
//    @Autowired
//    private String passwordEncoder;

    // ++++++++++++++++++++++++ User Registration Services ++++++++++++++++++++++++++++++++++++ \\

    public RegisterResponseDTO registerService(RegisterRequestDTO signupRequestDTO) {
        if (userRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        User user = new User();
        user.setUsername(signupRequestDTO.getUserName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPassword(signupRequestDTO.getPassword());
        user.setRole(UserEnum.Role.USER);

        User saveUser = userRepository.save(user);

        return new RegisterResponseDTO(
                saveUser.getId(),
                saveUser.getUsername(),
                saveUser.getEmail(),
                null
        );
    }


}

