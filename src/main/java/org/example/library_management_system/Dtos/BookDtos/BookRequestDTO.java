package org.example.library_management_system.Dtos.BookDtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    @NotBlank(message = "Book name is required")
    private String title;
    private String author;
    private int price;
    private int length;
    private String category;
    private String type;
}
