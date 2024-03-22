package bddTraders;

import io.restassured.RestAssured;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
                response -> response.body("companyName", equalTo("Apple, Inc.")));

        /*
        instead of:
        .body("companyName", equalTo("Apple, Inc.");
         */

    }
}
