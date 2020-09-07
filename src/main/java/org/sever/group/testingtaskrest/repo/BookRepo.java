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
    List<Book> findAllByRackIdAndLevelNumber(Long rackId, LevelNumber number);
    List<Book> findAllByRackId(Long rackId);
    List<Book> findBooksByLevelNumber(LevelNumber number);

}
