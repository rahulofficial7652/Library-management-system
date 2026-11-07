package org.example.library_management_system.Services;

import lombok.RequiredArgsConstructor;
import org.example.library_management_system.Dtos.BookDtos.BookRequestDTO;
import org.example.library_management_system.Dtos.BookDtos.BookResponseDTO;
import org.example.library_management_system.Enums.BookEnums;
import org.example.library_management_system.Enums.UserEnum;
import org.example.library_management_system.Exceptions.ResourceConflictException;
import org.example.library_management_system.Exceptions.ResourceNotFoundException;
import org.example.library_management_system.Exceptions.UnauthorizedException;
import org.example.library_management_system.Models.Books;
import org.example.library_management_system.Models.User;
import org.example.library_management_system.Repository.BookRepository;
import org.example.library_management_system.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServices {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    // +++++++++++++++++++++++++++ Book Add Service ++++++++++++++++++++++++++++++++++++++++++++++++++ \\
    @Transactional
    public BookResponseDTO addBook(BookRequestDTO bookRequestDTO, UUID userId) {
        validateAdmin(userId);
        boolean existBook = bookRepository.existsByBookTitleAndAuthorNameAndIsDeletedFalse(
                bookRequestDTO.getTitle(),
                bookRequestDTO.getAuthor()
        );
        if (existBook) {
            throw new ResourceConflictException("This book by the same author already exists");
        }

        Books book = new Books();
        book.setBookTitle(bookRequestDTO.getTitle());
        book.setAuthorName(bookRequestDTO.getAuthor());
        book.setPrice(bookRequestDTO.getPrice());
        book.setBookLength(bookRequestDTO.getLength());
        book.setBookCategory(BookEnums.BookCategory.valueOf(bookRequestDTO.getCategory().toUpperCase().trim()));
        book.setBookType(BookEnums.BookType.valueOf(bookRequestDTO.getType().toUpperCase().trim()));

        Books saveBook = bookRepository.save(book);
        return mapToResponseDTO(saveBook);
    }

    // +++++++++++++++++++++++++++ Book Update Service ++++++++++++++++++++++++++++++++++++++++++++++++++ \\
    @Transactional
    public BookResponseDTO updateBook(BookRequestDTO bookRequestDTO, UUID bookId, UUID userId) {
        validateAdmin(userId);

        Books book = findActiveBookById(bookId);

        book.setBookTitle(bookRequestDTO.getTitle());
        book.setAuthorName(bookRequestDTO.getAuthor());
        book.setPrice(bookRequestDTO.getPrice());
        book.setBookLength(bookRequestDTO.getLength());
        book.setBookCategory(BookEnums.BookCategory.valueOf(bookRequestDTO.getCategory().toUpperCase().trim()));
        book.setBookType(BookEnums.BookType.valueOf(bookRequestDTO.getType().toUpperCase().trim()));

        Books updateBook = bookRepository.save(book);
        return mapToResponseDTO(updateBook);
    }

    // +++++++++++++++++++++++++++ Book Delete Service ++++++++++++++++++++++++++++++++++++++++++++++++++ \\
    @Transactional
    public String deleteBook(UUID userId, UUID bookId) {
        validateAdmin(userId);

        Books findBook = findActiveBookById(bookId);

        findBook.setDeleted(true);
        findBook.setDeletedAt(LocalDateTime.now());

        bookRepository.save(findBook);
        return "Book deleted successfully";
    }

    // +++++++++++++++++++++++++++ Get All Books Service ++++++++++++++++++++++++++++++++++++++++++++++++ \\
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findByIsDeletedFalse()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // +++++++++++++++++++++++++++ Helper Methods ++++++++++++++++++++++++++++++++++++++++++++++++++ \\
    private void validateAdmin(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (user.getRole() != UserEnum.Role.ADMIN) {
            throw new UnauthorizedException("You are not authorized to perform this operation");
        }
    }

    private Books findActiveBookById(UUID bookId) {
        return bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
    }

    private BookResponseDTO mapToResponseDTO(Books book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getBookTitle())
                .author(book.getAuthorName())
                .price(book.getPrice())
                .length(book.getBookLength())
                .bookUrl(book.getBookUrl())
                .createdAt(book.getCreatedAt())
                .category(book.getBookCategory() != null ? book.getBookCategory().name() : null)
                .type(book.getBookType() != null ? book.getBookType().name() : null)
                .build();
    }
}