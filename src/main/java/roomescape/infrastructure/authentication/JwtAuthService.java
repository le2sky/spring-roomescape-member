package roomescape.infrastructure.authentication;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import roomescape.domain.Member;
import roomescape.infrastructure.persistence.MemberRepository;
import roomescape.service.auth.AuthService;
import roomescape.service.auth.AuthenticatedProfile;
import roomescape.service.auth.AuthenticationRequest;
import roomescape.service.auth.UnauthorizedException;

@Primary
@Component
class JwtAuthService implements AuthService {

    private static final String SECRET_KEY_VALUE = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY_VALUE.getBytes());

    private final MemberRepository memberRepository;

    public JwtAuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public String authenticate(AuthenticationRequest request) {
        String email = request.email();
        Member member = memberRepository
                .findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("올바른 인증 정보를 입력해주세요."));

        return Jwts.builder()
                .subject(member.getId().toString())
                .claim("name", member.getName().value())
                .signWith(KEY)
                .compact();
    }

    @Override
    public AuthenticatedProfile authorize(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(KEY)
                .build();
        try {
            Claims payload = parser.parseSignedClaims(token)
                    .getPayload();
            String name = payload.get("name", String.class);
            return new AuthenticatedProfile(name);
        } catch (JwtException e) {
            throw new UnauthorizedException();
        }
    }
}
