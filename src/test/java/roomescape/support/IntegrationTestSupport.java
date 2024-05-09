package roomescape.support;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/reset_test_data.sql")
public abstract class IntegrationTestSupport {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected int countRow(String tableName) {
        return JdbcTestUtils.countRowsInTable(jdbcTemplate, tableName);
    }

    protected void cleanUp(String tableName) {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, tableName);
    }

    protected LocalDate today() {
        return LocalDate.parse("2024-05-01");
    }

    protected LocalDate nextDate() {
        return LocalDate.parse("2024-05-02");
    }

    protected LocalDate previousDate() {
        return LocalDate.parse("2024-04-30");
    }

    protected LocalTime previousTime() {
        return LocalTime.parse("00:00");
    }

    @Configuration
    static class TestClockConfig {

        @Primary
        @Bean(name = "testClock")
        public Clock clock() {
            Instant instant = Instant.parse("2024-05-01T00:30:00.00Z");
            ZoneId zoneId = ZoneId.of("UTC");

            return Clock.fixed(instant, zoneId);
        }
    }
}
