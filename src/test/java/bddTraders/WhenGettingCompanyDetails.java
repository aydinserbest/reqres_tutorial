package bddTraders;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class WhenGettingCompanyDetails {
    @Test
    void getCompanyDetails() {
        RestAssured.get("http://localhost:9000/api/stock/aapl/company")
                .then()
                .statusCode(200)
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }
    @Test
    void getCompanyDetails2() {
        RestAssured.baseURI = "http://localhost:9000/api";
        RestAssured.get("/stock/aapl/company")
                .then()
                .statusCode(200)
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }

}
