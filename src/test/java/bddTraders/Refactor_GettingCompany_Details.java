package bddTraders;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class Refactor_GettingCompany_Details {
    @Before
    public void setup_Rest_config() {
        RestAssured.baseURI = "http://localhost:9000/api";
    }
    @Test
    public void getCompanyDetails() {
        RestAssured.get("/stock/aapl/company")
                .then().statusCode(200)
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }
}
