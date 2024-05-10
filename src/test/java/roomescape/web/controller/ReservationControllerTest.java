package roomescape.web.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import java.time.LocalDate;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.infrastructure.authentication.AuthService;
import roomescape.infrastructure.authentication.AuthenticationRequest;
import roomescape.support.IntegrationTestSupport;
import roomescape.web.controller.request.CreateReservationWebRequest;

class ReservationControllerTest extends IntegrationTestSupport {

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("전체 예약 목록을 조회한다.")
    void getAll() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @DisplayName("사용자가 예약을 생성한다.")
    void create() {
        LocalDate date = nextDate();
        CreateReservationWebRequest request = new CreateReservationWebRequest(date.toString(), 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", startsWith("/reservations/"))
                .body("name", is("어드민"))
                .body("date", is(date.toString()));
    }

    @Test
    @DisplayName("예약 날짜는 필수이다.")
    void validateDate() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body("{}")
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("details.message", hasItem("예약 날짜는 필수입니다."));
    }

    @Test
    @DisplayName("예약 날짜는 올바른 형식이어야 한다.")
    void validateDateFormat() {
        String invalidDate = "date";
        CreateReservationWebRequest invalidRequest = new CreateReservationWebRequest(invalidDate, 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body(invalidRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("details.message", hasItem("올바른 날짜 형태가 아닙니다."));
    }

    @Test
    @DisplayName("예약 시간 ID는 필수이다.")
    void validateTimeId() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body("{}")
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("details.message", hasItem("예약 시간 ID는 필수입니다."));
    }

    @Test
    @DisplayName("예약 시간 ID는 0보다 커야 한다.")
    void nonPositiveTimeId() {
        Long invalidTimeId = 0L;
        String date = nextDate().toString();
        CreateReservationWebRequest invalidRequest = new CreateReservationWebRequest(date, invalidTimeId, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body(invalidRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("details.message", hasItem("0보다 커야 합니다"));
    }

    @Test
    @DisplayName("존재하지 않는 시간 ID에 대한 예약을 할 수 없다.")
    void nonExistTimeId() {
        Long nonExistTimeId = 4L;
        String date = nextDate().toString();
        CreateReservationWebRequest invalidRequest = new CreateReservationWebRequest(date, nonExistTimeId, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body(invalidRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("message", is("해당되는 예약 시간이 없습니다."));
    }

    @Test
    @DisplayName("예약 테마 ID는 필수이다.")
    void validateThemeId() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body("{}")
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("details.message", hasItem("예약 테마 ID는 필수입니다."));
    }

    @Test
    @DisplayName("예약 테마 ID는 0보다 커야 한다.")
    void nonPositiveThemeId() {
        Long invalidThemeId = 0L;
        String date = nextDate().toString();
        CreateReservationWebRequest invalidRequest = new CreateReservationWebRequest(date, 1L, invalidThemeId);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body(invalidRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("details.message", hasItem("0보다 커야 합니다"));
    }

    @Test
    @DisplayName("존재하지 않는 테마 ID에 대한 예약을 할 수 없다.")
    void nonExistThemeId() {
        Long nonExistThemeId = 3L;
        String date = nextDate().toString();
        CreateReservationWebRequest invalidRequest = new CreateReservationWebRequest(date, 1L, nonExistThemeId);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body(invalidRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("message", is("해당되는 테마가 없습니다."));
    }

    @Test
    @DisplayName("중복 예약을 생성할 수 없다.")
    void duplicated() {
        String date = nextDate().toString();
        CreateReservationWebRequest request = new CreateReservationWebRequest(date, 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("message", is("중복된 예약이 존재합니다."));
    }

    @Test
    @DisplayName("지나간 시간에 대한 예약을 할 수 없다.")
    void previousDateTime() {
        String previousDate = previousDate().toString();
        CreateReservationWebRequest request = new CreateReservationWebRequest(previousDate, 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", givenToken())
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("message", is("이미 지나간 시간에 대한 예약을 할 수 없습니다."));
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void delete() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    private String givenToken() {
        return authService.authenticate(new AuthenticationRequest("admin@test.com", "password"));
    }
}
