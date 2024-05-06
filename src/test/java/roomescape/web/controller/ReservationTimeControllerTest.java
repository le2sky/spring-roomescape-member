package roomescape.web.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.service.request.ReservationTimeRequest;
import roomescape.support.IntegrationTestSupport;

/*
 * 테스트 데이터베이스 시간 초기 데이터
 * {ID=1, START_AT=10:00}
 * {ID=2, START_AT=11:00}
 * {ID=3, START_AT=13:00}
 */
class ReservationTimeControllerTest extends IntegrationTestSupport {

    @Test
    @DisplayName("전체 시간 목록을 조회한다.")
    void getAll() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    /*
     * 테스트 데이터베이스 예약 초기 데이터
     * {ID=1, NAME=브라운, DATE=2023-05-04, TIME={ID=1, START_AT="10:00"}, THEME={ID=1, NAME="레벨1 탈출"}}
     * {ID=2, NAME=엘라, DATE=2023-05-04, TIME={ID=2, START_AT="11:00"}, THEME={ID=1, NAME="레벨1 탈출"}}
     * {ID=3, NAME=릴리, DATE=2023-08-05, TIME={ID=2, START_AT="11:00"}, THEME={ID=1, NAME="레벨1 탈출"}}
     */
    @Test
    @DisplayName("예약 가능 시간 목록을 조회한다.")
    void availableTimes() {
        RestAssured.given().log().all()
                .when().get("/times/available?date=2023-05-04&themeId=1")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3))
                .body("alreadyBooked", contains(true, true, false));
    }

    @Test
    @DisplayName("예약 가능 시간 목록 조회 시 주어지는 날짜는 올바른 형식이어야 한다.")
    void validateDateFormat() {
        String invalidDate = "2023-05-044";

        RestAssured.given().log().all()
                .when().get("/times/available?date=" + invalidDate + "&themeId=1")
                .then().log().all()
                .statusCode(400)
                .body("details.message", hasItem("올바른 날짜 형태가 아닙니다."));
    }

    @Test
    @DisplayName("예약 가능 시간 목록 조회 시 주어지는 테마 ID는 0보다 커야 한다.")
    void validateThemeId() {
        String nonPositive = "0";

        RestAssured.given().log().all()
                .when().get("/times/available?date=2023-05-04&themeId=" + nonPositive)
                .then().log().all()
                .statusCode(400)
                .body("details.message", hasItem("0보다 커야 합니다"));
    }

    @Test
    @DisplayName("예약 시간을 생성한다.")
    void create() {
        ReservationTimeRequest request = new ReservationTimeRequest("18:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", startsWith("/times/"))
                .body("startAt", is("18:00"));
    }

    @Test
    @DisplayName("중복 예약 시간은 생성할 수 없다.")
    void duplicated() {
        ReservationTimeRequest request = new ReservationTimeRequest("11:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(400)
                .body("message", is("해당 예약 시간이 존재합니다."));
    }

    @Test
    @DisplayName("생성할 예약 시간 값은 필수이다.")
    void validateStartAt() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body("{}")
                .when().post("/times")
                .then().log().all()
                .statusCode(400)
                .body("details.message", hasItem("예약 시간은 필수입니다."));
    }

    @Test
    @DisplayName("생성할 예약 시간은 올바른 형식이어야 한다.")
    void validateStartAtFormat() {
        ReservationTimeRequest invalidRequest = new ReservationTimeRequest("102:33");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when().post("/times")
                .then().log().all()
                .statusCode(400)
                .body("details.message", hasItem("올바른 시간 형태가 아닙니다."));
    }

    @Test
    @DisplayName("예약 시간을 삭제한다.")
    void delete() {
        RestAssured.given().log().all()
                .when().delete("/times/3")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("사용되고 있는 시간은 삭제할 수 없다.")
    void usedDelete() {
        RestAssured.given().log().all()
                .when().delete("/times/2")
                .then().log().all()
                .statusCode(400)
                .body("message", is("해당 시간을 사용하고 있는 예약이 존재합니다."));
    }
}
