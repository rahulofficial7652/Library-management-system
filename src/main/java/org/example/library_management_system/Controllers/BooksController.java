package org.example.library_management_system.Controllers;


import lombok.RequiredArgsConstructor;
import org.example.library_management_system.Dtos.BookDtos.BookRequestDTO;
import org.example.library_management_system.Dtos.BookDtos.BookResponseDTO;
import org.example.library_management_system.Services.BookServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BooksController {

    private final BookServices bookServices;

    @PostMapping("/addBook/user/{userId}")
    public ResponseEntity<BookResponseDTO> addBookController(@RequestBody BookRequestDTO bookRequestDTO, @PathVariable UUID userId){
        BookResponseDTO responseDTO = bookServices.addBook(bookRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookResponseDTO>> getAllBookController(){
        List<BookResponseDTO> responseDTOS = bookServices.getAllBooks();
        return ResponseEntity.ok(responseDTOS);
    }


    @PatchMapping("/updateBook/{bookId}/user/{userId}")
    public ResponseEntity<BookResponseDTO> updateBookController(@RequestBody BookRequestDTO bookRequestDTO, @PathVariable UUID bookId, @PathVariable UUID userId){
        BookResponseDTO responseDTO = bookServices.updateBook(bookRequestDTO, bookId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/deleteBook/{bookId}/user/{userId}")
    public ResponseEntity<String> deleteBookController(@PathVariable UUID bookId, @PathVariable UUID userId){
        String deleteResponse = bookServices.deleteBook(userId, bookId);
        return ResponseEntity.ok(deleteResponse);
    }

}
