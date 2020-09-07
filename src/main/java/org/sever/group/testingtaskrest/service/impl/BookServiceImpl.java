package org.sever.group.testingtaskrest.service.impl;

import org.sever.group.testingtaskrest.domain.Book;
import org.sever.group.testingtaskrest.domain.util.LevelNumber;
import org.sever.group.testingtaskrest.repo.BookRepo;
import org.sever.group.testingtaskrest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Orlov Diga
 */
@Service
public class BookServiceImpl implements BookService {

    private BookRepo repo;

    @Autowired
    public BookServiceImpl(BookRepo repo) {
        this.repo = repo;
    }

    @Override
    public Book findById(Long name) {
        return repo.findById(name).orElse(null);
    }

    @Override
    public List<Book> findAllByRackIdAndLevelNumber(Long rackId, LevelNumber number) {
        return repo.findAllByRackIdAndLevelNumber(rackId, number);
    }

    @Override
    public List<Book> findAllByRackId(Long rackId) {
        return repo.findAllByRackId(rackId);
    }

    @Override
    public List<Book> findAllByLevelNumber(LevelNumber number) {
        return repo.findBooksByLevelNumber(number);
    }

    @Override
    public Book searchBookByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public Book addBook(Book book) {
        return repo.save(book);
    }

    @Override
    public void removeBook(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Book updateBook(Book book, Long bookId) {
        Book oldBook = repo.findById(bookId).orElse(null);

        if (oldBook == null) {
            return null;
        }

        if (!book.getAuthor().isEmpty()) {
            oldBook.setAuthor(book.getAuthor());
        }
        if (!book.getName().isEmpty()) {
            oldBook.setName(book.getName());
        }
        if (!book.getLevelNumber().equals(oldBook.getLevelNumber())) {
            oldBook.setLevelNumber(book.getLevelNumber());
        }
        if (!book.getRackId().equals(oldBook.getRackId())) {
            oldBook.setRackId(book.getRackId());
        }

        return repo.save(oldBook);
    }


}
