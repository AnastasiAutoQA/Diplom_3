package before_after;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class APIConfigForUserData {
    protected static final String URI = "https://stellarburgers.nomoreparties.site";
    protected static final String USER_URI = URI + "/api/auth/";

    protected RequestSpecification getHeader() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(URI)
                .build();
    }

    @Step("Регистрация пользователя")
    public ValidatableResponse registerNewUserResponse(String name, String email, String password) {
        String json = "{\"name\": \"" + name + "\",\"email\": \"" + email + "\", \"password\":\"" + password + "\"}";;
        return given()
                .spec(getHeader())
                .body(json)
                .when()
                .post(USER_URI + "register")
                .then().log().all();
    }

    @Step("Авторизация пользователя и получение его токена")
    public String loginAndExtractAccessToken(String email, String password) {
        String json = "{\"email\": \"" + email + "\", \"password\":\"" + password + "\"}";
        String accessToken = given().log().all()
                .spec(getHeader())
                .body(json)
                .when()
                .post(USER_URI + "login")
                .then().log().all().extract().body().path("accessToken");
        return accessToken;
    }

    @Step("Удаление пользователя по его accessToken")
    public ValidatableResponse deleteUser(String accessToken) {
        return given().log().all()
                .spec(getHeader())
                .auth().oauth2(accessToken.replace("Bearer ", "")) // аутентификация по accessToken при выполнении запроса на удаление
                .delete(USER_URI + "user")
                .then().log().all();
    }
}