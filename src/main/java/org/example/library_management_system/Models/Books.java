package org.example.library_management_system.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.example.library_management_system.Enums.BookEnums;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Books")
@Getter
@Setter
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Book Name is required")
    private String bookTitle;

    @NotBlank(message = "Author Name is required")
    private  String authorName;
    private int bookLength;
    private String bookUrl;
    @Enumerated(EnumType.STRING)
    private BookEnums.BookCategory bookCategory;
    @Enumerated(EnumType.STRING)
    private BookEnums.BookType bookType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "User_Books",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    private LocalDateTime bookPublishDate;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


}
