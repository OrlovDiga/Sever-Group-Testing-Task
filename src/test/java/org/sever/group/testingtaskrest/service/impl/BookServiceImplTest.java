package org.sever.group.testingtaskrest.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.sever.group.testingtaskrest.domain.Book;
import org.sever.group.testingtaskrest.domain.util.LevelNumber;
import org.sever.group.testingtaskrest.repo.BookRepo;
import org.sever.group.testingtaskrest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author Orlov Diga
 */
@RunWith(SpringRunner.class)
public class BookServiceImplTest {

    @TestConfiguration
    static class BookServiceImplTestContextConfiguration {
        @Bean
        public BookService bookService() {
            return new BookServiceImpl();
        }
    }

    @MockBean
    private BookRepo repo;

    @Autowired
    private BookServiceImpl service;

    private Book book;
    private List<Book> books;

    public BookServiceImplTest() {
        Book book1 = new Book();
        book1.setName("book1");
        book1.setAuthor("author1");
        book1.setRackId(1L);
        book1.setLevelNumber(LevelNumber.FIRST);

        Book book2 = new Book();
        book2.setName("book2");
        book2.setAuthor("author2");
        book2.setRackId(1L);
        book2.setLevelNumber(LevelNumber.FIRST);

        Book book3 = new Book();
        book3.setName("book3");
        book3.setAuthor("author4");
        book3.setRackId(1L);
        book3.setLevelNumber(LevelNumber.FIRST);

        this.books = Arrays.asList(book1, book2, book3);
        this.book = book1;
    }

    @Test
    @DirtiesContext
    public void findBookById() {
        when(repo.findById(1L)).thenReturn(Optional.of(book));
        Book resultBook = service.findById(1L);

        Assert.assertEquals(book, resultBook);
    }

    @Test
    @DirtiesContext
    public void findAllByRackIdAndLevelNumberTest() {

        when(repo.findAllByRackIdAndLevelNumber(1L, LevelNumber.FIRST)).thenReturn(books);
        List<Book> resultBooks = service.findAllByRackIdAndLevelNumber(1L, LevelNumber.FIRST);

        Assert.assertEquals(books, resultBooks);
    }

    @Test
    public void findAllByRackIdTest() {
        when(repo.findAllByRackId(1L)).thenReturn(books);
        List<Book> resultBooks = service.findAllByRackId(1L);

        Assert.assertEquals(books, resultBooks);
    }
    @Test
    public void findAllByLevelNumberTest() {
        when(repo.findBooksByLevelNumber(LevelNumber.FIRST)).thenReturn(books);
        List<Book> resultBooks = service.findAllByLevelNumber(LevelNumber.FIRST);

        Assert.assertEquals(books, resultBooks);
    }

    @Test
    public void searchBookByNameTest() {
        when(repo.findByName("book1")).thenReturn(book);
        Book resultBook = service.searchBookByName("book1");

        Assert.assertEquals(book, resultBook);

    }
    @Test
    public void addBookTest() {
        when(repo.save(book)).thenReturn(book);
        Book resultBook = service.addBook(book);

        Assert.assertEquals(book, resultBook);
    }
    @Test
    public void removeBookTest() {
        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);

        doNothing().when(repo).deleteById(valueCapture.capture());
        service.removeBook(1L);
        Assert.assertEquals(Long.valueOf(1), valueCapture.getValue());
    }

    @Test
    public void updateBootTest() {
        when(repo.findById(1L)).thenReturn(Optional.of(book));
        Book updateBook = new Book();
        updateBook.setName("newName");
        book.setName("newName");
        when(repo.save(book)).thenReturn(book);

        Book updatedBook = service.updateBook(updateBook, 1L);

        Assert.assertEquals(book, updatedBook);
    }
}
