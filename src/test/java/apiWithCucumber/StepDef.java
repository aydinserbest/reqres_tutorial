package apiWithCucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static net.serenitybdd.rest.SerenityRest.delete;



public class StepDef {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:9000/api";
    }
    String id;
    @Given("a client exists with the following details")
    public void aClientExistsWithTheFollowingDetails(DataTable dataTable) {
        List<Map<String, String>> clientDetails = dataTable.asMaps(String.class, String.class);
        Map<String, String> client = clientDetails.get(0);
        System.out.println(client.get("email"));

        given().contentType("application/json")
                .body(client)
                .when().post("/client");
        id = SerenityRest.lastResponse().jsonPath().get("id").toString();
    }
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
