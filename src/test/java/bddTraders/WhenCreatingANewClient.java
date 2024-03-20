package bddTraders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class WhenCreatingANewClient {

    @BeforeEach
    public void setupBaseURI() {
        baseURI = "http://localhost:9000/api";
    }

    @Test
    public void each_new_client_should_have_a_unique_id() {
        String newClient = "{\n" +
                "  \"firstName\": \"john\",\n" +
                "  \"lastName\": \"white\",\n" +
                "  \"email\": \"blue\"\n" +
                "}";

        given().body(newClient)
                //.contentType("application/json")  OR:
                .header("Content-Type", "application/json")
                .when().post("/client")
                .then()
                .statusCode(200)
                .and().body("id", not(equalTo(0)));
    }
    /*
    if we do not mention that we are sending json data with:
    contentType("application/json")
    it gives 415 error code
     */
    /*
    not and equalTo should come from Matchers.hamcrest
    from the same library
    if one comes from a different library it gives error
     */
}
