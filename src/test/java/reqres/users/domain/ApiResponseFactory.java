package reqres.users.domain;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ApiResponseFactory {

    private static final Faker faker = new Faker();

    public static ApiResponse createRandomApiResponse() {
        return new ApiResponse(
                faker.number().randomDigit(),
                faker.number().randomDigit(),
                faker.number().randomNumber(),
                faker.number().randomDigit(),
                createRandomUsers(),
                createRandomSupport()
        );
    }

    private static List<User> createRandomUsers() {
        // 5 random users
        return IntStream.range(0, 5)
                .mapToObj(i -> createRandomUser())
                .collect(Collectors.toList());
    }

    private static User createRandomUser() {
        return new User(
                faker.number().randomDigit(),
                faker.internet().emailAddress(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().avatar()
        );
    }

    private static Support createRandomSupport() {
        return new Support(
                faker.internet().url(),
                faker.lorem().sentence()
        );
    }
}