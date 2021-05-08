package io.spinach.vertx.web.client.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class VertxWebClientResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/vertx-web-client")
                .then()
                .statusCode(200)
                .body(is("Hello vertx-web-client"));
    }
}
