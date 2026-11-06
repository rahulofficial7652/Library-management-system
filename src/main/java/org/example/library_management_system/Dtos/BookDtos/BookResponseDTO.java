package org.example.library_management_system.Dtos.BookDtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseDTO {
    private UUID id;
    private String title;
    private String author;
    private int price;
    private int length;
    private String category;
    private String type;
    private String bookUrl;
    private LocalDateTime createdAt;
}
