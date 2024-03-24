package apiWithCucumber;

import bddTraders.ClientAsRecordType;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.delete;
import static net.serenitybdd.rest.SerenityRest.given;


public class StepDef {
    String id;
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:9000/api";
    }


    @DataTableType
    public ClientAsRecordType clientAsRecordType(Map<String, String> entry) {
        return new ClientAsRecordType(
                entry.get("firstName"),
                entry.get("lastName"),
                entry.get("email")
        );
    }
    @Given("a client exists with the following details")
    public void aClientExistsWithTheFollowingDetails(DataTable dataTable) {
        //ClientAsRecordType client = dataTable.asList(ClientAsRecordType.class).get(0);
        List<ClientAsRecordType> client = dataTable.asList(ClientAsRecordType.class);


        Response res = given().contentType("application/json")
                .body(client.get(0))
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
