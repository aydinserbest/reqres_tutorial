package bddTraders;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@ExtendWith(SerenityJUnit5Extension.class)
public class WhenCreatingANewClientAsRecordType {

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

       ClientAsRecordType newClientAsRecordType = new ClientAsRecordType("michael", "white", "blue@gmail.com");
        given().body(newClientAsRecordType)
                //.contentType("application/json")  OR:
                .header("Content-Type", "application/json")
                .when().post("/client")
                .then()
                .statusCode(200)
                .and().body("id", not(equalTo(0)))
                .and().body("email", equalTo("blue@gmail.com"));
    }
    @Test
    @DisplayName("sending jsonpath and retrieving data with record object")
    public void each_new_client_should_have_a_unique_id3() {

        ClientAsRecordType newClientAsRecordType = new ClientAsRecordType("michael", "white", "blue@gmail.com");
        ClientAsRecordType response = given().body(newClientAsRecordType)
                //.contentType("application/json")  OR:
                .header("Content-Type", "application/json")
                .when().post("/client")
                .as(ClientAsRecordType.class);//this is a domain object;

        System.out.println(response.firstName());
        assertThat(response.firstName()).isEqualTo("michael");
    }
    @Test
    @DisplayName("sending jsonpath and retrieving data with class object")
    public void each_new_client_should_have_a_unique_id4() {

        ClientAsClassType newClient = new ClientAsClassType("michael", "white", "blue@gmail.com");
        newClient.setFirstName("John"); // You can update fields using setters.
        ClientAsClassType response =
                given().body(newClient)
                //.contentType("application/json")  OR:
                .header("Content-Type", "application/json")
                .when().post("/client")
                .as(ClientAsClassType.class);//this is a domain object;

        System.out.println(response.getFirstName());
        assertThat(response.getFirstName()).isEqualTo("John");
    }
    @Test
    @DisplayName("sending jsonpath and retrieving data with map ")
    @SuppressWarnings("unchecked") // This line suppresses the warning
    public void each_new_client_should_have_a_unique_id5() {
        Map<String,Object> newClient = new HashMap<>();
        newClient.put("firstName", "michael");
        newClient.put("lastName", "white");
        newClient.put("email", "blue@gmail.com");


        Map<String,Object> response = given().body(newClient)
                //.contentType("application/json")  OR:
                .header("Content-Type", "application/json")
                .when().post("/client")
                .then().statusCode(200)
                .and().extract().body()
                .as(Map.class);//this is a domain object;

        System.out.println(response.get("firstName"));
        assertThat(response.get("firstName")).isEqualTo("michael");
    }
}
/*
            we can write without using body() and then() extract()
                when().post("/client")
                .as(Map.class);//this is a domain object;

 */

