package org.sever.group.testingtaskrest.util;

/**
 * @author Orlov Diga
 */
public final class LogMessage {
    public static final String RECEIVED_GET_BOOK_REQUEST = "Received a GET request to get book with id {}.";
    public static final String SHELF_BOOKS_NOT_FOUND = "Books on {} shelf not found.";
    public static final String SHELF_BOOKS_FOUND = "Books on {} shelf found.";
    public static final String RECEIVED_GET_RACK_BOOKS_REQUEST = "Received a GET request to get all books on the one rack with id {}.";
    public static final String RACK_BOOKS_NOT_FOUND = "Books on rack with id {} was not found.";
    public static final String RACK_BOOKS_FOUND = "Books on rack with id {} was found.";
    public static final String RECEIVED_GET_SHELF_BOOKS_REQUEST = "Received a GET request to get all books on {} shelves.";
    public static final String RECEIVED_GET_ALL_BOOKS_REQUEST = "Received a GET request to get all books on {} shelf to on rack with id {}.";
    public static final String ALL_BOOKS_NOT_FOUND = "Books on rack with id {} on {} shelf were not found.";
    public static final String ALL_BOOKS_FOUND = "Books on rack with id {} on {} shelf were found. Found books count: {}";
    public static final String RECEIVED_POST_ADD_BOOK = "Received a POST request to add book.";
    public static final String SAVE_BOOK_FAILED = "Saving book with name {} failed.";
    public static final String SAVE_BOOK_OK = "Saving book with name {} successful.";
    public static final String RECEIVED_DELETE_BOOK_REQUEST = "Received a DELETE request to remove book with id {}.";
    public static final String RECEIVED_PUT_BOOK_REQUEST = "Received a PUT request to update book with id {}.";
    public static final String UPDATE_BOOK_FAILED = "Update book with id {} was failed.";
    public static final String UPDATE_BOOK_OK = "Update book with id {} was successful";
    public static final String RECEIVED_POST_SEARCH_BOOK_REQST = "Received a POST request to search book with name {}.";
    public static final String BOOK_NOT_FOUND = "Book with name: {} not found.";
    public static final String BOOK_FOUND = "Book with name: {} found and sent.";
}
