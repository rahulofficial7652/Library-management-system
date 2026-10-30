package org.example.library_management_system.Enums;

public class BookEnums {

    public enum BookCategory {
        // --- FICTION ---
        FICTION,
        ACTION_ADVENTURE,
        CLASSICS,
        DRAMA,
        FANTASY,
        HISTORICAL_FICTION,
        HORROR,
        MYSTERY_THRILLER,
        ROMANCE,
        SCIENCE_FICTION,

        // --- NON-FICTION ---
        NON_FICTION,
        ARTS_PHOTOGRAPHY,
        BIOGRAPHY_AUTOBIOGRAPHY,
        BUSINESS_FINANCE,
        COMPUTER_SCIENCE_TECH,
        HISTORY,
        PHILOSOPHY,
        POLITICS_CIVICS,
        POETRY,
        SELF_HELP_MOTIVATION,
        SCIENCE_NATURE,

        // --- ACADEMIC & PROFESSIONAL ---
        ACADEMIC_EDUCATION,
        LAW_LEGAL_STUDIES,
        MEDICAL_HEALTH,
        REFERENCE,

        // --- OTHERS ---
        CHILDREN_BOOKS,
        COMICS_GRAPHIC_NOVELS,
        OTHER
    }

    public enum BookType {
        HARDCOVER,       // Premium physical book with a rigid protective cover
        PAPERBACK,       // Standard physical book with a flexible paper cover
        EBOOK,           // Digital version (PDF, EPUB, Kindle format)
        AUDIOBOOK,       // Audio recording of the book being read aloud
        MAGAZINE,        // Periodical publication / journal format
        COMIC_BOOK,      // Sequential art/panel format
        JOURNAL_NOTEBOOK // Blank or ruled book meant for writing
    }
}