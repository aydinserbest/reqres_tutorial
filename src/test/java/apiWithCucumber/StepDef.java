package apiWithCucumber;

import bddTraders.ClientAsClassType;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;

import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.delete;
import static net.serenitybdd.rest.SerenityRest.given;


public class StepDef {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:9000/api";
    }
    String id;
    @Given("a client exists with the following details")
    public void aClientExistsWithTheFollowingDetai(DataTable dataTable) {

        Map<String, String> clientDetailsMap = dataTable.asMaps(String.class, String.class).get(0);

        ClientAsClassType client = new ClientAsClassType();
        client.setFirstName(clientDetailsMap.get("firstName"));
        client.setLastName(clientDetailsMap.get("lastName"));
        client.setEmail(clientDetailsMap.get("email"));
        Response res = given().contentType("application/json")
                .body(client)
                .when().post("/client");

        System.out.println("Response: " + res.asString());

        id = SerenityRest.lastResponse().jsonPath().getString("id"); }
    @When("I delete the client")
    public void iDeleteTheClient() {
        delete("/client/{id}", id);
    }
    @Then("the client should no longer exist in the system")
    public void theClientShouldNoLongerExistInTheSystem() {
        SerenityRest.get("/client/{id}", id)
                .then().statusCode(404);

    }
}
