package reqres.users;

import io.restassured.RestAssured;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SerenityJUnit5Extension.class)
class WhenSearchingForUsers {


    @Nested
    @DisplayName("When listing the known users from /api/users endpoint")
    class WhenListingTheUsers{
        @Test
        @DisplayName("a query with no parameters should return all the users")
        void getAllUsers(){
            int statusCode = RestAssured.get("https://reqres.in/api/users").statusCode();

            assertThat(statusCode).isEqualTo(200);
        }
        @Test
        @DisplayName("a query with no parameters should return all the users")
        void getAllUsers2(){
            RestAssured.get("https://reqres.in/api/users")
                    .then().statusCode(200)
                                    .body("page", equalTo(1))
                    .body("per_page", equalTo(6))
                    .body("total_pages", equalTo(2));

            List<String> userEmails = RestAssured.get("https://reqres.in/api/users").jsonPath().get("data.email");

            assertThat(userEmails).doesNotHaveDuplicates()
                    .allMatch(email -> email.endsWith("@reqres.in"));
        }
        @Test
        @DisplayName("should return the correct page numbers and pagination details")
        void checkPagination(){

            List<String> userEmails = RestAssured.get("https://reqres.in/api/users").jsonPath().get("data.email");

            assertThat(userEmails).doesNotHaveDuplicates()
                    .allMatch(email -> email.endsWith("@reqres.in"));
            //the difference with below is:
            // chain assert.
        }
        @Test
        @DisplayName("should return the correct page numbers and pagination details")
        void checkPagination2(){
            // all data-all json arrays into List<Map<String, Object>>
            List<Map<String, Object>> data = RestAssured.get("https://reqres.in/api/users").jsonPath().get("data");
            for (Map<String, Object > user : data) {
                String firstName = (String)user.get("first_name");
                String lastName = (String)user.get("last_name");
                int id = (Integer)user.get("id");
                String email = (String) user.get("email");

                System.out.printf("User %s %s (ID: %s) has email: %s\n", firstName, lastName, id, email);
            }
            // only emails into List<String>
            List<String> userEmails = RestAssured.get("https://reqres.in/api/users").jsonPath().get("data.email");
            userEmails.forEach(System.out::println);

            System.out.println(userEmails.get(0));

            assertThat(userEmails).doesNotHaveDuplicates();
            assertThat(userEmails).allMatch(email -> email.endsWith("@reqres.in"));
            }
            /*
            1- The string "\n" appends a line break at the end of the output.

            2- Each %s placeholder in the format string is substituted in order with the arguments provided.
            For example, the first %s is replaced by firstName, the second %s is replaced by lastName, and so forth.

            3-
                %s: Inserts a string value.
                %d: Inserts an integer value.
                %f: Inserts a floating-point value.
                %n: Inserts a newline character.
             */


    }

    //EVALUATE THE METHOD BELOW LATER:
    /*
    @Test
    @DisplayName("should return the correct page numbers and pagination details")
    void checkPagination(){
        RestAssured.get("https://reqres.in/api/users").then().body("data.email", Matchers.hasSize(6));
        List<Map<String, Object>> data = RestAssured.get("https://reqres.in/api/users").jsonPath().get("data");

        JSONObject json = new JSONObject(data); //tek bir json objectsi var, yaniltici olabilir!!!
        System.out.println(json.toString(4)); //4 girinti ver demek

        JSONArray jsonArray2 = new JSONArray(data);
        for (Map<String, Object> map : data) {
            jsonArray2.put(new JSONObject(map));
        }
        System.out.println(jsonArray2.toString(4));

//        System.out.println(data.get(0).get("id"));
//        System.out.println(data.size());
//        System.out.println(data.get(0).get("first_name"));


        //bu JSON dizisini bir String değişkeninde sakladığımızı ve
        // ardından bu diziyi bir JSONArray nesnesine dönüştürdüğümüzü varsayacağız:

        String jsonString = "{\n" +
                "        \"last_name\": \"Bluth\",\n" +
                "        \"id\": 1,\n" +
                "        \"avatar\": \"https://reqres.in/img/faces/1-image.jpg\",\n" +
                "        \"first_name\": \"George\",\n" +
                "        \"email\": \"george.bluth@reqres.in\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"last_name\": \"Weaver\",\n" +
                "        \"id\": 2,\n" +
                "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\",\n" +
                "        \"first_name\": \"Janet\",\n" +
                "        \"email\": \"janet.weaver@reqres.in\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"last_name\": \"Wong\",\n" +
                "        \"id\": 3,\n" +
                "        \"avatar\": \"https://reqres.in/img/faces/3-image.jpg\",\n" +
                "        \"first_name\": \"Emma\",\n" +
                "        \"email\": \"emma.wong@reqres.in\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"last_name\": \"Holt\",\n" +
                "        \"id\": 4,\n" +
                "        \"avatar\": \"https://reqres.in/img/faces/4-image.jpg\",\n" +
                "        \"first_name\": \"Eve\",\n" +
                "        \"email\": \"eve.holt@reqres.in\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"last_name\": \"Morris\",\n" +
                "        \"id\": 5,\n" +
                "        \"avatar\": \"https://reqres.in/img/faces/5-image.jpg\",\n" +
                "        \"first_name\": \"Charles\",\n" +
                "        \"email\": \"charles.morris@reqres.in\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"last_name\": \"Ramos\",\n" +
                "        \"id\": 6,\n" +
                "        \"avatar\": \"https://reqres.in/img/faces/6-image.jpg\",\n" +
                "        \"first_name\": \"Tracey\",\n" +
                "        \"email\": \"tracey.ramos@reqres.in\"\n" +
                "    }";

        JSONArray jsonArray = new JSONArray(jsonString);
        //Bu dizinin içerisinde döngü kullanarak gezebilir ve her bir nesnenin özelliklerine erişebiliriz:
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            int id = jsonObject.getInt("id");
            String firstName = jsonObject.getString("first_name");
            String lastName = jsonObject.getString("last_name");

            System.out.println("ID: " + id);
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("-------------------------");
        }
        //Ayrıca, JSON dizisine yeni bir JSONObject ekleyebiliriz:
        JSONObject newJsonObject = new JSONObject();
        newJsonObject.put("id", 3);
        newJsonObject.put("first_name", "Thomas");
        newJsonObject.put("last_name", "Jefferson");

        jsonArray.put(newJsonObject);


    }

     */
}

