package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.service.request.ThemeRequest;
import roomescape.support.IntegrationTestSupport;

/*
 * 테스트 데이터베이스 예약 초기 데이터
 * {ID=1, NAME=브라운, DATE=2023-05-04, TIME={ID=1, START_AT="10:00"}, THEME={ID=1, NAME="레벨1 탈출"}}
 * {ID=2, NAME=엘라, DATE=2023-05-04, TIME={ID=2, START_AT="11:00"}, THEME={ID=1, NAME="레벨1 탈출"}}
 * {ID=3, NAME=릴리, DATE=2023-08-05, TIME={ID=2, START_AT="11:00"}, THEME={ID=1, NAME="레벨1 탈출"}}
 */
class ThemeServiceTest extends IntegrationTestSupport {

    @Autowired
    private ThemeService target;

    @Test
    @DisplayName("신규 테마를 생성할 수 있다.")
    void createTheme() {
        ThemeRequest request = new ThemeRequest("name", "description", "thumbnail");

        assertThatCode(() -> target.createTheme(request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("테마를 사용하는 예약이 존재하면, 삭제하지 않는다.")
    void cantDelete() {
        Long id = 1L;

        assertThatThrownBy(() -> target.deleteTheme(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당 테마를 사용하는 예약이 존재합니다.");
    }
}
