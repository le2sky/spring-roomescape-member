package roomescape.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.Member;
import roomescape.domain.Name;
import roomescape.support.IntegrationTestSupport;

class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository target;

    @Test
    @DisplayName("모든 멤버 데이터를 가져온다.")
    void findAll() {
        List<Member> members = target.findAll();

        assertThat(members).hasSize(1);
    }

    @Test
    @DisplayName("멤버 데이터가 존재하지 않으면 빈 리스트를 반환한다.")
    void empty() {
        cleanUp("reservation");
        cleanUp("member");

        List<Member> members = target.findAll();

        assertThat(members).isEmpty();
    }

    @Test
    @DisplayName("이메일로 사용자를 조회할 수 있다.")
    void findByEmail() {
        Optional<Member> findMember = target.findByEmail("admin@test.com");

        assertThat(findMember)
                .map(Member::getName)
                .map(Name::value)
                .isNotEmpty()
                .get()
                .isEqualTo("어드민");
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 사용자를 조회한다.")
    void notFoundByEmail() {
        Optional<Member> findMember = target.findByEmail("unknown@email.com");

        assertThat(findMember).isEmpty();
    }

    @Test
    @DisplayName("특정 멤버 id의 데이터를 조회한다.")
    void findById() {
        Optional<Member> findMember = target.findById(1L);

        assertThat(findMember)
                .map(Member::getName)
                .map(Name::value)
                .isNotEmpty()
                .get()
                .isEqualTo("어드민");
    }

    @Test
    @DisplayName("존재하지 않는 멤버 id로 데이터를 조회한다.")
    void notFoundById() {
        Optional<Member> findMember = target.findById(3L);

        assertThat(findMember).isEmpty();
    }
}
