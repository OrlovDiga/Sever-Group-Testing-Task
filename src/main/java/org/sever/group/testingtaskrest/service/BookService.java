package org.sever.group.testingtaskrest.service;

import org.sever.group.testingtaskrest.domain.Book;
import org.sever.group.testingtaskrest.domain.util.LevelNumber;

import java.util.List;
import java.util.Set;

/**
 * @author Orlov Diga
 */
public interface BookService {

    Book findById(Long bookId);
    List<Book> findAllByRackIdAndLevelNumber(Long rackId, LevelNumber number);
    List<Book> findAllByRackId(Long rackId);
    List<Book> findAllByLevelNumber(LevelNumber number);
    Book searchBookByName(String name);
    Book addBook(Book book);
    void removeBook(Long id);
    Book updateBook(Book book, Long bookId);
}
