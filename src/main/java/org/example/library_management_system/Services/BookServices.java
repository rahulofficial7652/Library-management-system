package org.example.library_management_system.Services;

import lombok.RequiredArgsConstructor;
import org.example.library_management_system.Dtos.BookDtos.BookRequestDTO;
import org.example.library_management_system.Dtos.BookDtos.BookResponseDTO;
import org.example.library_management_system.Enums.BookEnums;
import org.example.library_management_system.Enums.UserEnum;
import org.example.library_management_system.Models.Books;
import org.example.library_management_system.Models.User;
import org.example.library_management_system.Repository.BookRepository;
import org.example.library_management_system.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServices {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

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
        book.setBookCategory(BookEnums.BookCategory.valueOf(bookRequestDTO.getCategory().toUpperCase().trim()));
        book.setBookType(BookEnums.BookType.valueOf(bookRequestDTO.getType().toUpperCase().trim()));
        Books saveBook = bookRepository.save(book);

        return BookResponseDTO.builder()
                .id(saveBook.getId())
                .title(saveBook.getBookTitle())
                .author(saveBook.getAuthorName())
                .price(saveBook.getPrice())
                .length(saveBook.getBookLength())
                .bookUrl(saveBook.getBookUrl())
                .createdAt(saveBook.getCreatedAt())
                .category(saveBook.getBookCategory() != null ? saveBook.getBookCategory().name() : null)
                .type(saveBook.getBookType() != null ? saveBook.getBookType().name() : null)
                .build();
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

        return BookResponseDTO.builder()
                .id(updateBook.getId())
                .title(updateBook.getBookTitle())
                .author(updateBook.getAuthorName())
                .price(updateBook.getPrice())
                .length(updateBook.getBookLength())
                .bookUrl(updateBook.getBookUrl())
                .category(updateBook.getBookCategory() != null ? updateBook.getBookCategory().name() : null)
                .type(updateBook.getBookType() != null ? updateBook.getBookType().name() : null)
                .build();
    }

    //+++++++++++++++++++++++++++ Book Delete Service ++++++++++++++++++++++++++++++++++++++++++++++++++ \\
    @Transactional
    public String deleteBook(UUID userId, UUID bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (user.getRole() != UserEnum.Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized");
        }
        Books findBook = bookRepository.findById(bookId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        findBook.setDeleted(true);
        findBook.setDeletedAt(LocalDateTime.now());

        bookRepository.save(findBook);
        return "Book deleted successfully";
    }


    public List<BookResponseDTO> getAllBooks() {
        List<Books> findAllBooks = bookRepository.findByIsDeletedFalse();
        return findAllBooks.stream().map(
                books -> BookResponseDTO.builder()
                        .id(books.getId())
                        .title(books.getBookTitle())
                        .author(books.getAuthorName())
                        .price(books.getPrice())
                        .length(books.getBookLength())
                        .bookUrl(books.getBookUrl())
                        .category(books.getBookCategory() != null ? books.getBookCategory().name() : null)
                        .type(books.getBookType() != null ? books.getBookType().name() : null)
                        .build()).toList();
    }
}
