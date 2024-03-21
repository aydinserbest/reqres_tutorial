package bddTraders;


import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Refactor_GettingCompany_Details_Test {
    @BeforeEach
    public void setup_Rest_config() {
        baseURI = "http://localhost:9000/api";
    }
    @Test
    public void getCompanyDetails() {
        get("/stock/aapl/company")
                .then().statusCode(200)
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }
    @Test
    @DisplayName("using path param")
    public void getCompanyDetails2() {
        given()
                .pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/company")
                .then()
                .statusCode(200)
                .body("companyName", equalTo("Apple, Inc."));
    }
    @Test
    @DisplayName("using path param short version - add path param to get method")
    public void getCompanyDetails3() {
        get("/stock/{symbol}/company", "aapl")
                .then()
                .statusCode(200)
                .body("companyName", equalTo("Apple, Inc."));
        //or

        //"http://localhost:9000/api/stock/{symbol}/company", "aapl";
    }
    @Test
    @DisplayName("using query param")
    public void getCompanyDetails4() {
        Response response = given()
                .queryParam("symbols", "fb")
                .when().get("/news");

        response.then().statusCode(200)
                .body("related", everyItem(containsString("FB")));
       // response.prettyPrint();
        //List<Map<String, Object>> data = response.getBody().jsonPath().getList("$");
    }
    @Test
    public void getCompanyDetails5() {
        Response data = get("https://reqres.in/api/users");
        System.out.println(data.asPrettyString());

        List<Map<String, Object>> data2 = data.getBody().jsonPath().get("data");
    }
    @Test
    @DisplayName("using static import for RestAssured")
    public void staticRestAssured(){
        //import static io.restassured.RestAssured.*;
        given().get("https://reqres.in/api/users")
                .then()
                .statusCode(200);
    }
}
