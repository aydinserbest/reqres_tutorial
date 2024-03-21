package reqres.users;

import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reqres.users.domain.ApiResponse;
import reqres.users.domain.UserDetail;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test with domain objects")
public class TestWithDomainObjects {
    @Test
    @DisplayName("should get response with domain objects")
    void getAllUsersWithDomainObject() {
        ApiResponse apiResponse = SerenityRest.get("https://reqres.in/api/users").as(ApiResponse.class);

        List<String> userEmails = apiResponse.data().stream().map(UserDetail::email).toList();

        /*
        List<String> userEmails = new ArrayList<>();
        for (User user : userData) {
            userEmails2.add(user.email());
        }
        */


        assertThat(userEmails).doesNotHaveDuplicates()
                .allMatch(email -> email.endsWith("@reqres.in"));

        List<UserDetail> userDetailData = apiResponse.data();
        assertThat(userDetailData).hasSize(6);

        UserDetail firstUserDetail = userDetailData.get(0);
        System.out.println(firstUserDetail.first_name());
    }

//    @Test
//    @DisplayName("should send response with domain objects")
//    void shouldCreateUser() {
//        // Given
//        UserCreate userToCreate = ApiResponseFactory.createRandomUserToCreate();
//
//        // When
//        UserCreateResponse userCreateResponse = ApiResponseFactory.createRandomUserCreateResponse();
//        // Then
//        assertNotNull(userCreateResponse);
//        assertEquals(userToCreate.name(), userCreateResponse.name());
//        assertEquals(userToCreate.job(), userCreateResponse.job());
//        assertNotNull(userCreateResponse.id());
//        assertNotNull(userCreateResponse.createdAt());
//
//    }
}





