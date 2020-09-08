package org.sever.group.testingtaskrest.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.sever.group.testingtaskrest.api.dto.BookNameDTO;
import org.sever.group.testingtaskrest.domain.Book;
import org.sever.group.testingtaskrest.domain.util.LevelNumber;
import org.sever.group.testingtaskrest.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookStoreControllerRestTest {

    private static final String CONTENT_TYPE = "application/json";

    private final MockMvc mockMvc;
    private final BookRepo repo;
    private final ObjectMapper mapper;

    public Book book1;
    public List<Book> books;

    @Autowired
    public BookStoreControllerRestTest(MockMvc mockMvc, BookRepo repo, ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.repo = repo;
        this.mapper = mapper;

        Book bookOne = new Book();
        bookOne.setName("book1");
        bookOne.setAuthor("author1");
        bookOne.setRackId(1L);
        bookOne.setLevelNumber(LevelNumber.THIRD);

        Book book2 = new Book();
        book2.setName("book2");
        book2.setAuthor("author2");
        book2.setRackId(1L);
        book2.setLevelNumber(LevelNumber.FIRST);

        Book book3 = new Book();
        book3.setName("book3");
        book3.setAuthor("author4");
        book3.setRackId(1L);
        book3.setLevelNumber(LevelNumber.SECOND);

        Book savedBook1 = repo.save(bookOne);
        Book savedBook2 = repo.save(book2);
        Book savedBook3 = repo.save(book3);

        this.book1 = savedBook1;
        this.books = Arrays.asList(savedBook1, savedBook2, savedBook3);
    }

    @Test
    @DirtiesContext
    void getBookTest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/bookStore/books/" + book1.getId())
                        .contentType(CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals(toJson(book1), result.getResponse().getContentAsString());
    }

    @Test
    @DirtiesContext
    void getAllBooksByRackTest() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/bookStore/books/rack/1")
                        .contentType(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals(toListJson(books), result.getResponse().getContentAsString());
    }

    @Test
    @DirtiesContext
    void getAllBooksByLevelTest() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/bookStore/books/level/FIRST")
                        .contentType(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andReturn();

        List<Book> expectedResult = books.stream()
                .filter(it -> it.getLevelNumber().equals(LevelNumber.FIRST))
                .collect(Collectors.toList());

        Assert.assertEquals(toListJson(expectedResult), result.getResponse().getContentAsString());
    }

    @Test
    @DirtiesContext
    void getAllBooksByRackAndByLevelTest() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/bookStore/books/rack/1/level/FIRST")
                        .contentType(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andReturn();

        List<Book> expectedResult = books.stream()
                .filter(it -> it.getLevelNumber().equals(LevelNumber.FIRST) &&
                              it.getRackId().equals(1L))
                .collect(Collectors.toList());

        Assert.assertEquals(toListJson(expectedResult), result.getResponse().getContentAsString());
    }

    @Test
    @DirtiesContext
    void updateBookTest() throws Exception {
        book1.setName("newName");
        String jsonBook1 = toJson(book1);

        MvcResult result = mockMvc
                .perform(put("/bookStore/books/" + book1.getId() + "/update")
                        .contentType(CONTENT_TYPE)
                        .content(jsonBook1))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals(jsonBook1, result.getResponse().getContentAsString());
    }

    @Test
    @DirtiesContext
    void addBookTest() throws Exception {
        Book newBook = new Book();
        newBook.setName("newBookName");
        newBook.setAuthor("newBookAuthor");
        newBook.setRackId(2L);
        newBook.setLevelNumber(LevelNumber.THIRD);
        String jsonNewBook = toJson(newBook);


        MvcResult result = mockMvc
                .perform(post("/bookStore/books/")
                        .contentType(CONTENT_TYPE)
                        .content(jsonNewBook))
                .andExpect(status().isCreated())
                .andReturn();

        Book book = mapper.readValue(result.getResponse().getContentAsString(), Book.class);
        Assert.assertTrue(book.equalsWithoutId(newBook));
    }

    @Test
    @DirtiesContext
    void searchByNameTest() throws Exception {
        BookNameDTO nameDTO = new BookNameDTO();
        nameDTO.setName(book1.getName());

        MvcResult result = mockMvc
                .perform(post("/bookStore/books/search")
                        .contentType(CONTENT_TYPE)
                        .content(mapper.writeValueAsString(nameDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals(toJson(book1), result.getResponse().getContentAsString());
    }

    private String toJson(Book book) throws JsonProcessingException {
        return mapper.writeValueAsString(book);
    }

    private String toListJson(List<Book> books) throws JsonProcessingException {
        return mapper.writeValueAsString(books);
    }
}
