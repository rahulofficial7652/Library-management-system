package org.example.library_management_system.Repository;

import org.example.library_management_system.Models.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository <Books, UUID> {
    boolean existsByBookTitleAndAuthorNameAndIsDeletedFalse(String title, String author);
   List<Books> findByIsDeletedFalse();

    Optional<Books> findByIdAndIsDeletedFalse(UUID uuid);
}
