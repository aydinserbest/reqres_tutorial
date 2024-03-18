package reqres.users;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SerenityJUnit5Extension.class)
class HowToUseListOrMap {


    @Nested
    @DisplayName("When listing the known users from /api/users endpoint")
    class WhenListingTheUsers {
        @Test
        @DisplayName(" list or map usage")
        void assignData() {
            // 1-
            List<Map<String, String>> users = SerenityRest.get("https://reqres.in/api/users").jsonPath().get("data");
            assertThat(users).hasSize(6);

            // 2-
            List<String> userEmails = SerenityRest.get("https://reqres.in/api/users").jsonPath().get("data.email");

            assertThat(userEmails).doesNotHaveDuplicates()
                    .allMatch(email -> email.endsWith("@reqres.in"));

            //3-

            Map<String, Object> firstUser = SerenityRest.get("https://reqres.in/api/users").jsonPath().get("data[0]");

            firstUser.forEach((key, value) -> System.out.println(key + ": " + value));

            Object email = firstUser.get("email");
            System.out.println(email);

            String firstName = SerenityRest.get("https://reqres.in/api/users").jsonPath().get("data[0].first_name");
            System.out.println(firstName);


        }
    }
}