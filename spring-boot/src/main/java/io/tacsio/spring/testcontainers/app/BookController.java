package io.tacsio.spring.testcontainers.app;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateBookRequest request) {
        var book = bookRepository.save(request.toModel());

        return ResponseEntity.ok(book);
    }
}
