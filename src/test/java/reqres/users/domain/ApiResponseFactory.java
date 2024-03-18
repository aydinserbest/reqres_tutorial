package reqres.users.domain;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ApiResponseFactory {

    private static final Faker faker = new Faker();

    public static ApiResponse createRandomApiResponse() {
        return new ApiResponse(
                faker.number().randomDigit(),
                faker.number().randomDigit(),
                faker.number().randomDigit(),
                faker.number().randomDigit(),
                createRandomUsers(),
                createRandomSupport()
        );
    }

    private static List<UserDetail> createRandomUsers() {
        // 5 random users
        return IntStream.range(0, 5)
                .mapToObj(i -> createRandomUserDetail())
                .collect(Collectors.toList());
    }

    private static UserDetail createRandomUserDetail() {
        return new UserDetail(
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

    public static UserCreate createRandomUserToCreate() {
        return new UserCreate(
                faker.name().firstName(),
                faker.job().position()
        );
    }

    public static UserCreateResponse createRandomUserCreateResponse() {
        UserCreate user = createRandomUserToCreate();
        return new UserCreateResponse(
                user.name(),
                user.job(),
                Integer.toString(faker.number().randomDigit()),
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );
    }
}