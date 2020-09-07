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

import static org.sever.group.testingtaskrest.util.LogMessage.*;

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
        LOG.info(RECEIVED_GET_BOOK_REQUEST, id);

        Book book = bookService.findById(id);

        return (book == null) ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) :
                                new ResponseEntity<>(convertToDTO(book), HttpStatus.OK);
    }

    @GetMapping("/rack/{rackId}")
    public ResponseEntity<List<BookDTO>> getBooksByRack(@PathVariable("rackId") Long id) {
        LOG.info(RECEIVED_GET_RACK_BOOKS_REQUEST, id);

        List<Book> foundBooks = bookService.findAllByRackId(id);

        if (foundBooks == null || foundBooks.isEmpty()) {
            LOG.info(RACK_BOOKS_NOT_FOUND, id);
            return new ResponseEntity<>(convertTotListDTO(foundBooks), HttpStatus.NO_CONTENT);
        }
        LOG.info(RACK_BOOKS_FOUND, id);
        return new ResponseEntity<>(convertTotListDTO(foundBooks), HttpStatus.OK);

    }

    @GetMapping("/level/{number}")
    public ResponseEntity<List<BookDTO>> getBooksByLevel(@PathVariable("number") LevelNumber number) {
        LOG.info(RECEIVED_GET_SHELF_BOOKS_REQUEST, number.toString());

        List<Book> books = bookService.findAllByLevelNumber(number);

        if (books == null || books.isEmpty()) {
            LOG.info(SHELF_BOOKS_NOT_FOUND, number);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        LOG.info(SHELF_BOOKS_FOUND, number);
        return ResponseEntity.ok(convertTotListDTO(books));
    }

    @GetMapping("/rack/{rackId}/level/{number}")
    public ResponseEntity<List<BookDTO>> getBooksByRackAndLevel(@PathVariable("rackId") Long rackId,
                                                                 @PathVariable("number") LevelNumber number) {
        LOG.info(RECEIVED_GET_ALL_BOOKS_REQUEST,
                number.toString(), rackId);

        List<Book> foundedBooks =
                bookService.findAllByRackIdAndLevelNumber(rackId, number);

        if (foundedBooks == null || foundedBooks.isEmpty()) {
            LOG.info(ALL_BOOKS_NOT_FOUND,
                    rackId, number.toString());
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        LOG.info(ALL_BOOKS_FOUND,
                foundedBooks.size(),
                rackId,
                number.toString());
        return new ResponseEntity<>(convertTotListDTO(foundedBooks), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBookByRackAndLevel(@RequestBody BookDTO bookDTO) {
        LOG.info(RECEIVED_POST_ADD_BOOK);

        Book savedBook = bookService.addBook(convertToEntity(bookDTO));

        if (savedBook == null) {
            LOG.info(SAVE_BOOK_FAILED, bookDTO.getName());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        LOG.info(SAVE_BOOK_OK, savedBook.getName());
        return new ResponseEntity<>(convertToDTO(savedBook), HttpStatus.CREATED);
    }

    @DeleteMapping("{bookId}")
    public void removeBookById(@PathVariable("bookId") Long id) {
        LOG.info(RECEIVED_DELETE_BOOK_REQUEST, id);

        bookService.removeBook(id);
    }

    @PutMapping("/{bookId}/update")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO,
                                              @PathVariable("bookId") Long bookId) {
        LOG.info(RECEIVED_PUT_BOOK_REQUEST, bookId);
        Book updatedBook = bookService.updateBook(convertToEntity(bookDTO), bookId);

        if (updatedBook == null) {
            LOG.info(UPDATE_BOOK_FAILED, bookId);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            LOG.info(UPDATE_BOOK_OK, bookId);
            return new ResponseEntity<>(convertToDTO(updatedBook), HttpStatus.OK);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<BookDTO> searchBookByName(@RequestBody BookNameDTO name) {
        LOG.info(RECEIVED_POST_SEARCH_BOOK_REQST, name);

        Book foundBook = bookService.searchBookByName(name.getName());

        if (foundBook == null) {
            LOG.info(BOOK_NOT_FOUND, name);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        LOG.info(BOOK_FOUND, name);
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
