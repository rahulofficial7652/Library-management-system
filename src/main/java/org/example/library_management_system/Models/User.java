package org.example.library_management_system.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.library_management_system.Enums.UserEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String username;

    @NotBlank(message = "Email field cannot be left blank")
    @Email(message = "Please enter a valid email address format")
    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String address;
    private int age;
    private String contact;

    @ManyToMany(mappedBy = "users")
    private List<Books> borrowedBooks;

    @Enumerated(EnumType.STRING)
    private UserEnum.Role role;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
