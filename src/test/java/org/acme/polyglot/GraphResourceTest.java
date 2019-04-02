package org.acme.polyglot;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class GraphResourceTest {

  @Test
  public void testHelloEndpoint() {
    given()
        .when().get("/graph")
        .then()
        .statusCode(200)
        .body(containsString("<svg xmlns='http://www.w3.org/2000/svg'"));
  }

}
