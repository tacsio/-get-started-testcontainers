package io.tacsio.spring.testcontainers.app;

public record CreateBookRequest(String name) {

    public Book toModel() {
        var book = new Book();
        book.setName(name);

        return book;
    }
}
