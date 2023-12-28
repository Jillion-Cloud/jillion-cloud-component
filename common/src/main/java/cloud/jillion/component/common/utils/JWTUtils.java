package cloud.jillion.component.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leanderlee
 * @since 1.0.0
 */
@Slf4j
public class JWTUtils {
    /**
     * 默认过期时间 3600秒后
     */
    private static final int DEFAULT_SIGN_EXPIRES = 3600;

    public static String createToken(@Validated TokenInfo tokenInfo) {
        JWTCreator.Builder builder = JWT.create()
                .withIssuer(tokenInfo.getIssuer())
                .withSubject(tokenInfo.getSubject())
                .withIssuedAt(null == tokenInfo.getIssuedAt() ? new Date() : tokenInfo.getIssuedAt())
                .withExpiresAt(null == tokenInfo.getExpiresAt() ? generateExpiresAt() : tokenInfo.getExpiresAt())
                .withJWTId(tokenInfo.getJwtId());
        if (null != tokenInfo.getExtClaims() && !tokenInfo.getExtClaims().isEmpty()) {
            tokenInfo.getExtClaims().forEach((k, v) -> {
                if (v instanceof Boolean) {
                    builder.withClaim(k, (Boolean) v);
                } else if (v instanceof Integer) {
                    builder.withClaim(k, (Integer) v);
                } else if (v instanceof Long) {
                    builder.withClaim(k, (Long) v);
                } else if (v instanceof Double) {
                    builder.withClaim(k, (Double) v);
                } else if (v instanceof String) {
                    builder.withClaim(k, (String) v);
                } else if (v instanceof Date) {
                    builder.withClaim(k, (Date) v);
                } else if (v instanceof List<?>) {
                    builder.withClaim(k, (List<?>) v);
                } else if (v instanceof Map<?,?>) {
                    builder.withClaim(k, (Map<String, ?>) v);
                } else {
                    builder.withClaim(k, JSONUtils.toJson(v));
                }
            });
        }
        return builder.sign(Algorithm.HMAC512(tokenInfo.getSign()));
    }

    public static Boolean validateToken(String token, String sign) {
        try {
            JWT.require(Algorithm.HMAC512(sign)).build().verify(token);
            return true;
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Jwt Token validate failed: ", e);
            }
        }
        return false;
    }

    public static TokenInfo getTokenInfo(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        TokenInfo.TokenInfoBuilder builder = TokenInfo.builder();
        builder.issuer(decodedJWT.getIssuer());
        builder.subject(decodedJWT.getSubject());
        builder.issuedAt(decodedJWT.getIssuedAt());
        builder.expiresAt(decodedJWT.getExpiresAt());
        builder.jwtId(decodedJWT.getId());
        HashMap<String, Object> extClaims = new HashMap<>();
        Map<String, Claim> claims = decodedJWT.getClaims();
        if (null != claims && !claims.isEmpty()) {
            claims.forEach((k, v) -> {
                if (k.equals("iss") || k.equals("sub") || k.equals("exp") || k.equals("iat") || k.equals("jti")) {
                    return;
                }
                extClaims.put(k, v.as(Object.class));
            });
        }
        builder.extClaims(extClaims);
        return builder.build();
    }

    private static Date generateExpiresAt() {
        return new Date(new Date().getTime() + DEFAULT_SIGN_EXPIRES);
    }

    @Data
    @Builder
    public static class TokenInfo implements Serializable {

        @NotBlank
        private String issuer;

        @NotBlank
        private String subject;

        private Date issuedAt;

        private Date expiresAt;

        @NotBlank
        private String jwtId;

        @NotBlank
        private String sign;

        private HashMap<String, Object> extClaims;
    }
}
