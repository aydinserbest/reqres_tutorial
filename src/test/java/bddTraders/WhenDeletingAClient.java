package bddTraders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class WhenDeletingAClient {
    @BeforeEach
    public void setupBaseURI() {
        baseURI = "http://localhost:9000/api";
    }

    @Test
    public void deleteClient() {
        Map<String, Object> client = new HashMap<>();
        client.put("firstName", "michael");
        client.put("lastName", "white");
        client.put("email", "blue@gmail.com");

        String id = given().contentType("application/json")
                .body(client)
                .when().post("/client")
                .jsonPath().getString("id");

        //delete the client

        //we do not declare given-when
        //we used the short version of path param in the same parentheses

        delete("/client/{id}", id);
        // long version of pathParam
        /*
        given().pathParam("id", id)
                .delete("/client/{id}");  //when is not necessary to declare

         */

        //when I delete the client, I get a 404
        //then the client should no longer exist
        //short version of pathParam
        get("/client/{id}", id)
                .then().statusCode(404);

        //or
        /*
        given().pathParam("id", id)
                .when().get("/client/{id}")
                .then().statusCode(404);

         */


    }

    @Test
    @DisplayName("using Map.of to create client and getString method for id ")
    public void deleteClient_Refactor() {
        Map<String, Object> client = Map.of("firstName", "michael", "lastName", "white", "email", "blue@gmail.com");

        /*
        OR:
        Map<String, Object> client = new HashMap<>();
        client.put("firstName", "michael");
        client.put("lastName", "white");
        client.put("email", "blue@gmail.com");

         */
        String id = getString(client);

        //delete the client
        delete("/client/{id}", id);

        //when I delete the client, I get a 404 and the client should no longer exist
        get("/client/{id}", id)
                .then().statusCode(404);

    }
    @Test
    @DisplayName("using Map to create client and put method to update client")
    public void should_be_able_to_update(){
        Map<String, Object> client = new HashMap<>();
        client.put("firstName", "michael");
        client.put("lastName", "white");
        client.put("email", "blue@gmail.com");
        //given a client exists
        String id = getString(client);
        //when i update the email address of a client
        client.put("email", "red@gmail.com");
        given().pathParam("id", id)
                .contentType("application/json")
                .body(client)
                .when().put("/client/{id}")
                .then().statusCode(200);

        given().get("/client/{id}", id)
                .then()
                .body("email", equalTo("red@gmail.com"));
    }
    @Test
    @DisplayName("using setter to update ClientAsClassType object")
    public void should_be_able_to_update2(){
        ClientAsClassType client = new ClientAsClassType("michael", "white", "blue@gmail.com");
        //given a client exists
        String id = getString(client);
        //when i update the email address of a client
        client.setEmail("red@gmail.com");
        given().pathParam("id", id)
                .contentType("application/json")
                .body(client)
                .when().put("/client/{id}")
                .then().statusCode(200);

        given().get("/client/{id}", id)
                .then()
                .body("email", equalTo("red@gmail.com"));

    }
    @Test
    @DisplayName("using Map to update ClientAsClassType object")
    public void should_be_able_to_update3(){
        ClientAsClassType client = new ClientAsClassType("michael", "white", "blue@gmail.com");
        //given a client exists
        String id = getString(client);
        //when i update the email address of a client
        Map<String, Object> clientUpdate = new HashMap<>();
        clientUpdate.put("email", "red@gmail.com");
        given().pathParam("id", id)
                .contentType("application/json")
                .body(clientUpdate)
                .when().put("/client/{id}")
                .then().statusCode(200);

        given().get("/client/{id}", id)
                .then()
                .body("email", equalTo("red@gmail.com"));

    }

    private static String getString(Map<String, Object> client) {
        return given().contentType("application/json")
                .body(client)
                .when().post("/client")
                .jsonPath().getString("id");
    }
    private static String getString(ClientAsClassType client) {
        return given().contentType("application/json")
                .body(client)
                .when().post("/client")
                .jsonPath().getString("id");
    }
}

        /*
        A Map that you create using HashMap is mutable, meaning you can add a new element, update an existing one,
        or perform a deletion operation later on.
    For example, you can use the code below to change an existing email value:
            client.put("email", "red@gmail.com");
    Similarly, you can use the following code to add a new element:
            client.put("age", 30);
    Using this code, you can update the value of the specified key on the Map, or add a new key-value pair.
    This dynamic nature of HashMaps is quite useful when working with data that constantly changes.

    Alternatively, as mentioned below, you can create a Map in one line and populate it with values.
     Map<String, String> client = Map.of("firstName", "michael", "lastName", "white", "email", "blue@gmail.com");

     However, the maps created with this method are immutable,
     which means you cannot add or remove elements later on.
    If you want your Map to be subject to changes, you should continue using your earlier block of code.
    When you are stating your information fully, and it will not change,
    the latter method will be more useful and readable.
             */