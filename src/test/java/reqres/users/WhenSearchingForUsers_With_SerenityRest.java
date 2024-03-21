package reqres.users;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("When searching for users")
class WhenSearchingForUsers_With_SerenityRest {


    @Nested
    @DisplayName("When listing the known users from /api/users endpoint")
    class WhenListingTheUsers {

        @Test
        @DisplayName("a query with no parameters should return all the users")
        void getAllUsers() {
            List<String> userEmails = SerenityRest.get("https://reqres.in/api/users").jsonPath().get("data.email");

            assertThat(userEmails).doesNotHaveDuplicates()
                    .allMatch(email -> email.endsWith("@reqres.in"));

            SerenityRest.get("https://reqres.in/api/users").jsonPath().get("data[0]");
            List<Map<String, String>> users = SerenityRest.get("https://reqres.in/api/users").jsonPath().get("data");
            assertThat(users).hasSize(6);
        }

        @Test
        @DisplayName("should return the correct page numbers and pagination details")
        void checkPagination() {
            SerenityRest.get("https://reqres.in/api/users")
                    .then().statusCode(200)
                    .body("page", equalTo(1))
                    .body("per_page", equalTo(6))
                    .body("total_pages", equalTo(2));
        }
        @Test
        @DisplayName("should show the output in pretty format")
        void prettyOutput() {
            //Response response = SerenityRest.get("https://reqres.in/api/users");
            RestAssured.get("https://reqres.in/api/users").prettyPrint();

             Response response = RestAssured.get("https://reqres.in/api/users");
           // System.out.println(response.asString());
            System.out.println(response.asPrettyString());

        }
    }
}