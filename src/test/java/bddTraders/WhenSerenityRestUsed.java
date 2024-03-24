package bddTraders;

import io.restassured.RestAssured;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.delete;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SerenityJUnit5Extension.class)
public class WhenSerenityRestUsed {
    @BeforeEach
    public void setup_Rest_config() {
        RestAssured.baseURI = "http://localhost:9000/api";
    }
    @Test
    public void getCompanyDetails() {
        SerenityRest.get("/stock/aapl/company")
                .then().statusCode(200);
        //the assertion will be seen in the serenity report
        Ensure.that("the industry is correctly defined for AAPL",
                response -> response.body("companyName", equalTo("Apple, Inc.")))
                .andThat("the exchange should be the NASDAQ",
                        response -> response.body("sector", equalTo("Electronic Technology")));

        /*
        instead of:
        .body("companyName", equalTo("Apple, Inc.");
         */

    }
    @Test
    @DisplayName("lastResponse() method of serenity.bdd")
    public void deleteClient() {
        Map<String, Object> sarahJane = aClient();

        given().contentType("application/json")
                .body(sarahJane)
                .when().post("/client");

        //store id
        String id = SerenityRest.lastResponse().jsonPath().get("id").toString();

        //delete the client
        delete("/client/{id}", id);
        //check the status code 404
        SerenityRest.get("/client/{id}", id)
                .then().statusCode(404);
    }

    @NotNull
    private static Map<String, Object> aClient() {
        Map<String, Object> client = new HashMap<>();
        client.put("firstName", "michael");
        client.put("lastName", "white");
        client.put("email", "blue@gmail.com");
        return client;
    }
}
