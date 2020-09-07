package org.sever.group.testingtaskrest;

import org.modelmapper.ModelMapper;
import org.sever.group.testingtaskrest.domain.Book;
import org.sever.group.testingtaskrest.domain.util.LevelNumber;
import org.sever.group.testingtaskrest.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class TestingTaskRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingTaskRestApplication.class, args);
	}

	@Autowired
	private BookRepo bookRepo;

	@Bean
    public ModelMapper mapper() {
	    return new ModelMapper();
    }

    @Bean
    @Transactional
    public CommandLineRunner execute() {
        return args -> {
            Book book1 = new Book();
            book1.setName("book1");
            book1.setAuthor("author1");
            book1.setRackId(1L);
            book1.setLevelNumber(LevelNumber.THIRD);

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

            Book book4 = new Book();
            book4.setName("book4");
            book4.setAuthor("author1");
            book4.setRackId(2L);
            book4.setLevelNumber(LevelNumber.THIRD);

            Book book5 = new Book();
            book5.setName("book5");
            book5.setAuthor("author3");
            book5.setRackId(3L);
            book5.setLevelNumber(LevelNumber.SECOND);

            Book book6 = new Book();
            book6.setName("book6");
            book6.setAuthor("author2");
            book6.setRackId(2L);
            book6.setLevelNumber(LevelNumber.FIRST);

            bookRepo.save(book1);
            bookRepo.save(book2);
            bookRepo.save(book3);
            bookRepo.save(book4);
            bookRepo.save(book5);
            bookRepo.save(book6);
        };
    }
}
