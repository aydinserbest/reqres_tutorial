package bddTraders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class WhenCreatingANewClient {

    @BeforeEach
    public void setupBaseURI() {
        baseURI = "http://localhost:9000/api";
    }

    @Test
    @DisplayName("sending body with string")
    public void each_new_client_should_have_a_unique_id() {

        //this is a text block
        String newClient = """
                {
                  "firstName": "john",
                  "lastName": "white",
                  "email": "blue@gmail.com"
                }""";

        given().body(newClient)
                //.contentType("application/json")  OR:
                .header("Content-Type", "application/json")
                .when().post("/client")
                .then()
                .statusCode(200)
                .and().body("id", not(equalTo(0)))
                .and().body("email", equalTo("blue@gmail.com"));
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
    @Test
    @DisplayName("sending body with domain object")
    public void each_new_client_should_have_a_unique_id2() {

       Client newClient = new Client("michael", "white", "blue@gmail.com");
        given().body(newClient)
                //.contentType("application/json")  OR:
                .header("Content-Type", "application/json")
                .when().post("/client")
                .then()
                .statusCode(200)
                .and().body("id", not(equalTo(0)))
                .and().body("email", equalTo("blue@gmail.com"));
    }
    @Test
    @DisplayName("retrieving data with domain object")
    public void each_new_client_should_have_a_unique_id3() {

        Client newClient = new Client("michael", "white", "blue@gmail.com");
        Client response = given().body(newClient)
                //.contentType("application/json")  OR:
                .header("Content-Type", "application/json")
                .when().post("/client")
                .as(Client.class);//this is a domain object;

        System.out.println(response.firstName());
        assertThat(response.firstName()).isEqualTo("michael");
    }
}
