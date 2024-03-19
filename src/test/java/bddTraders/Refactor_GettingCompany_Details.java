package bddTraders;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class Refactor_GettingCompany_Details {
    @BeforeEach
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
    @Test
    @DisplayName("using path param")
    public void getCompanyDetails2() {
        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/company")
                .then()
                .statusCode(200)
                .body("companyName", equalTo("Apple, Inc."));
    }
    @Test
    @DisplayName("using path param short version - add path param to get method")
    public void getCompanyDetails3() {
        RestAssured
                .get("/stock/{symbol}/company", "aapl")
                .then()
                .statusCode(200)
                .body("companyName", equalTo("Apple, Inc."));
        //or

        //"http://localhost:9000/api/stock/{symbol}/company", "aapl";
    }
    @Test
    @DisplayName("using query param")
    public void getCompanyDetails4() {
        Response response = RestAssured
                .given()
                .queryParam("symbols", "fb")
                .when().get("/news");

        List<Map<String, Object>> data = response.getBody().jsonPath().getList("$");

        response.then().statusCode(200);
        response.prettyPrint();
    }
    @Test
    public void getCompanyDetails5() {
        Response data = RestAssured
                .get("https://reqres.in/api/users");
        System.out.println(data.asPrettyString());

        List<Map<String, Object>> data2 = data.getBody().jsonPath().get("data");

    }
}
