package org.example.library_management_system.Services;

import org.example.library_management_system.Dtos.BookDtos.BookRequestDTO;
import org.example.library_management_system.Dtos.BookDtos.BookResponseDTO;
import org.example.library_management_system.Enums.BookEnums;
import org.example.library_management_system.Enums.UserEnum;
import org.example.library_management_system.Models.Books;
import org.example.library_management_system.Models.User;
import org.example.library_management_system.Reopsitory.BookRepository;
import org.example.library_management_system.Reopsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class BookServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    //+++++++++++++++++++++++++++ Book Add Service ++++++++++++++++++++++++++++++++++++++++++++++++++ \\
    @Transactional
    public BookResponseDTO addBook(BookRequestDTO bookRequestDTO, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not found"));
        if (user.getRole() != UserEnum.Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your are not authorized");
        }

        boolean existBook = bookRepository.existsByBookTitleAndAuthorName(bookRequestDTO.getTitle(), bookRequestDTO.getAuthor());
        if (existBook)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This book by the same author already exists");

        Books book = new Books();

        book.setBookTitle(bookRequestDTO.getTitle());
        book.setAuthorName(bookRequestDTO.getAuthor());
        book.setPrice(bookRequestDTO.getPrice());
        book.setBookLength(bookRequestDTO.getLength());
        book.setBookCategory(BookEnums.BookCategory.valueOf(bookRequestDTO.getCategory()));
        book.setBookType(BookEnums.BookType.valueOf(bookRequestDTO.getType()));
        Books saveBook = bookRepository.save(book);

        return new BookResponseDTO(
                saveBook.getId(),
                saveBook.getBookTitle(),
                saveBook.getAuthorName(),
                saveBook.getPrice(),
                saveBook.getBookLength(),
                saveBook.getBookUrl(),
                saveBook.getBookCategory().name(),
                saveBook.getBookType().name()
        );
    }

    //+++++++++++++++++++++++++++ Book Update Service ++++++++++++++++++++++++++++++++++++++++++++++++++ \\
    @Transactional
    public BookResponseDTO updateBook(BookRequestDTO bookRequestDTO, UUID bookId, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not found"));
        if (user.getRole() != UserEnum.Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your are not authorized");
        }

        Books book = bookRepository.findById(bookId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found"));

        book.setBookTitle(bookRequestDTO.getTitle());
        book.setAuthorName(bookRequestDTO.getAuthor());
        book.setPrice(bookRequestDTO.getPrice());
        book.setBookLength(bookRequestDTO.getLength());
        book.setBookCategory(BookEnums.BookCategory.valueOf(bookRequestDTO.getCategory()));
        book.setBookType(BookEnums.BookType.valueOf(bookRequestDTO.getType()));
        Books updateBook = bookRepository.save(book);

        return new BookResponseDTO(
                updateBook.getId(),
                updateBook.getBookTitle(),
                updateBook.getAuthorName(),
                updateBook.getPrice(),
                updateBook.getBookLength(),
                updateBook.getBookUrl(),
                updateBook.getBookCategory().name(),
                updateBook.getBookType().name()
        );
    }

    //+++++++++++++++++++++++++++ Book Delete Service ++++++++++++++++++++++++++++++++++++++++++++++++++ \\
    @Transactional
    public String deletedBook(UUID userId, UUID bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (user.getRole() != UserEnum.Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized");
        }
        Books findBook = bookRepository.findById(bookId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not find"));

        findBook.setDeleted(true);
        findBook.setDeletedAt(LocalDateTime.now());

        bookRepository.save(findBook);
        return "Book deleted successfully";
    }
}
