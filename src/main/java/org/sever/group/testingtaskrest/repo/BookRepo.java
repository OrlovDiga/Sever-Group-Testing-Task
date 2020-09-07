package org.sever.group.testingtaskrest.repo;

import org.sever.group.testingtaskrest.domain.Book;
import org.sever.group.testingtaskrest.domain.util.LevelNumber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Orlov Diga
 */
@Repository
public interface BookRepo extends CrudRepository<Book, Long> {

    Book findByName(String name);
    List<Book> findAllByShelvingIdAndLevelNumber(Long shelvingId, LevelNumber number);
    List<Book> findAllByShelvingId(Long shelvingId);
    List<Book> findBooksByLevelNumber(LevelNumber number);

}
