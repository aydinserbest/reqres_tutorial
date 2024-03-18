package reqres.users;

import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reqres.users.domain.ApiResponse;
import reqres.users.domain.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestWithDomainObjects {
    @Test
    @DisplayName("should get response with domain objects")
    void getAllUsersWithDomainObject() {
        ApiResponse apiResponse = SerenityRest.get("https://reqres.in/api/users").as(ApiResponse.class);

        List<String> userEmails = apiResponse.data().stream().map(User::email).toList();

        /*
        List<String> userEmails = new ArrayList<>();
        for (User user : userData) {
            userEmails2.add(user.email());
        }
        */



        assertThat(userEmails).doesNotHaveDuplicates()
                .allMatch(email -> email.endsWith("@reqres.in"));

        List<User> userData = apiResponse.data();
        assertThat(userData).hasSize(6);

        User firstUser = userData.get(0);
        System.out.println(firstUser.first_name());
    }
}
