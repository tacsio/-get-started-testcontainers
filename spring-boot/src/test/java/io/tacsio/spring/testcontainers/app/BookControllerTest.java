package io.tacsio.spring.testcontainers.app;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.tacsio.spring.testcontainers.config.TestcontainersConfiguration;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private BookRepository bookRepository;

    private Faker faker = new Faker();


    @BeforeEach
    void setup() {
        RestAssured.port = port;
        bookRepository.deleteAll();
    }

    @Test
    void index() {
        var book = new Book();
        book.setName(faker.book().title());
        bookRepository.save(book);

        given().contentType(ContentType.JSON)
                .when().get("/books")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", Matchers.hasItem(book.getName()));

    }

    @Test
    void create() {
        Assertions.assertThat(bookRepository.count()).isEqualTo(0L);

        var requestBody = new CreateBookRequest("Test Containers");

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/books")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", Matchers.equalTo("Test Containers"))
                .body("id", Matchers.notNullValue());

        Assertions.assertThat(bookRepository.count()).isEqualTo(1L);
    }
}