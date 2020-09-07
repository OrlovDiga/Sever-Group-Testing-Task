package org.sever.group.testingtaskrest.api.controller;

import org.modelmapper.ModelMapper;
import org.sever.group.testingtaskrest.api.dto.BookDTO;
import org.sever.group.testingtaskrest.api.dto.BookNameDTO;
import org.sever.group.testingtaskrest.domain.Book;
import org.sever.group.testingtaskrest.domain.util.LevelNumber;
import org.sever.group.testingtaskrest.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Orlov Diga
 */
@RestController
@RequestMapping(value = "/bookStore/books",
        produces = "application/json",
        consumes = "application/json")
public class BookStoreController {

    private static final Logger LOG = LoggerFactory.getLogger(BookStoreController.class);

    private final BookService bookService;
    private final ModelMapper mapper;

    @Autowired
    public BookStoreController(BookService bookService, ModelMapper mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("bookId") Long id) {
        LOG.info("Received a GET request to get book with id {}.", id);

        Book book = bookService.findById(id);

        return (book == null) ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) :
                                new ResponseEntity<>(convertToDTO(book), HttpStatus.OK);
    }

    @GetMapping("/shelving/{shelvingId}")
    public ResponseEntity<List<BookDTO>> getBooksByShelving(@PathVariable("shelvingId") Long id) {
        LOG.info("Received a GET request to get all books on the one shelving with id {}.", id);

        List<Book> foundBooks = bookService.findAllByShelvingId(id);

        if (foundBooks == null || foundBooks.isEmpty()) {
            LOG.info("Books on shelving with id {} was not found.", id);
            return new ResponseEntity<>(convertTotListDTO(foundBooks), HttpStatus.OK);
        }
        LOG.info("Books on shelving with id {} was found.", id);
        return new ResponseEntity<>(convertTotListDTO(foundBooks), HttpStatus.OK);

    }

    @GetMapping("/level/{number}")
    public ResponseEntity<List<BookDTO>> getBooksByLevel(@PathVariable("number") LevelNumber number) {
        LOG.info("Received a GET request to get all books on {} shelves.", number.toString());

        List<Book> books = bookService.findAllByLevelNumber(number);

        if (books == null || books.isEmpty()) {
            LOG.info("Books on {} shelf not found.", number);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        LOG.info("Books on {} shelf found.", number);
        return ResponseEntity.ok(convertTotListDTO(books));
    }

    @GetMapping("/shelving/{shelvingId}/level/{number}")
    public ResponseEntity<List<BookDTO>> getBooksByShelvingAndLevel(@PathVariable("shelvingId") Long shelvingId,
                                                                 @PathVariable("number") LevelNumber number) {
        LOG.info("Received a GET request to get all books on {} shelf to on shelving with id {}.",
                number.toString(), shelvingId);

        List<Book> foundedBooks =
                bookService.findAllByShelvingIdAndLevelNumber(shelvingId, number);

        if (foundedBooks == null || foundedBooks.isEmpty()) {
            LOG.info("Books on shelving with id {} on {} shelf were not found.",
                    shelvingId, number.toString());
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        LOG.info("Books on shelving with id {} on {} shelf were found. Found books count: {}",
                foundedBooks.size(),
                shelvingId,
                number.toString());
        return new ResponseEntity<>(convertTotListDTO(foundedBooks), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBookByShelvingAndLevel(@RequestBody BookDTO bookDTO) {
        LOG.info("Received a POST request to add book.");

        Book savedBook = bookService.addBook(convertToEntity(bookDTO));

        if (savedBook == null) {
            LOG.info("Saving book with name {} failed.", bookDTO.getName());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        LOG.info("Saving book with name {} successful.", savedBook.getName());
        return new ResponseEntity<>(convertToDTO(savedBook), HttpStatus.CREATED);
    }

    @DeleteMapping("{bookId}")
    public void removeBookById(@PathVariable("bookId") Long id) {
        LOG.info("Received a DELETE request to remove book with id {}.", id);

        bookService.removeBook(id);
    }

    @PutMapping("/{bookId}/update")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO, @PathVariable("bookId") Long bookId) {
        LOG.info("Received a PUT request to update book with id {}.", bookId);
        Book updatedBook = bookService.updateBook(convertToEntity(bookDTO), bookId);

        if (updatedBook == null) {
            LOG.info("Update book with id {} was failed.", bookId);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            LOG.info("Update book with id {} was successful", bookId);
            return new ResponseEntity<>(convertToDTO(updatedBook), HttpStatus.OK);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<BookDTO> searchBookByName(@RequestBody BookNameDTO name) {
        LOG.info("Received a POST request to search book with name {}.", name);

        Book foundBook = bookService.searchBookByName(name.getName());

        if (foundBook == null) {
            LOG.info("Book with name: {} not found.", name);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        LOG.info("Book with name: {} found and sent.", name);
        return new ResponseEntity<>(convertToDTO(foundBook), HttpStatus.OK);
    }

    private Book convertToEntity(BookDTO bookDTO) {
        return convert(bookDTO, Book.class);
    }

    private BookDTO convertToDTO(Book book) {
        return convert(book, BookDTO.class);
    }

    private List<BookDTO> convertTotListDTO(List<Book> books) {
        return convertList(books, BookDTO.class);
    }

    private <S, T> T convert(S source, Class<T> targetClass) {
        return mapper.map(source, targetClass);
    }

    private  <S, T> List<T> convertList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
