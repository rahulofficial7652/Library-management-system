package org.example.library_management_system.Reopsitory;

import org.example.library_management_system.Models.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@EnableJpaRepositories
@Repository
public interface BookRepository extends JpaRepository <Books, UUID> {
    boolean existsByBookTitleAndAuthorName(String title, String author);
}
