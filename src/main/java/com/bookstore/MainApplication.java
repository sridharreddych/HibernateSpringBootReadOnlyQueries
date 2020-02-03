package com.bookstore;

import com.bookstore.entity.Author;
import com.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            System.out.println("Avoid:\n------\n");
            Author authorRW = bookstoreService.fetchAuthorReadWriteMode();
            authorRW.setAge(authorRW.getAge() + 1);
            bookstoreService.updateAuthor(authorRW);

            System.out.println("\n\n=============================\n\n");
            
            System.out.println("Recommended:\n-----------\n");
            Author authorRO = bookstoreService.fetchAuthorReadOnlyMode();
            authorRO.setAge(authorRO.getAge() + 1);
            bookstoreService.updateAuthor(authorRO);
        };
    }
}



/*
 * 
 * Use Read-Only Entity Whenever You Plan To Propagate Entity Changes To The Database In A Future Persistent Context

Description: This application highlights the difference betweeen loading entities in read-write vs. read-only mode. If you plan to modify the entities in a future Persistent Context then fetch them as read-only in the current Persistent Context.

Key points:

in the current Persistent Context, fetch entities in read-only mode
modifiy the entities in the current Persistent Context or in detached state (the potential modifications done in the current Persistent Context will not be propagated to the database at flush time)
in a subsequent Persistent Context, merge the detached entity and propagate changes to the database
Note: If you never plan to modify the fetched result set then use DTO (e.g., Spring projection), not read-only entities.
 * 
 * 
 */
