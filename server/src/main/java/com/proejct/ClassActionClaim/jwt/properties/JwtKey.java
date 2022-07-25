package com.proejct.ClassActionClaim.jwt.properties;

import io.jsonwebtoken.security.Keys;
import org.springframework.data.util.Pair;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;
import java.util.Random;

/**
 * JWT Key 관리 & 제공 & 조회
 *
 * Key Rolling을 지원합니다
 */
public class JwtKey {
    /**
     * (Kid == Key Id)
     * Kid-Key List 외부로 절대 유출되어서는 안됩니다.
     */
    private static final Map<String, String> SECRET_KEY_SET = Map.of(
            "key1", "SpringSecurityJWTPracticeProjectIsSoGoodAndThisProjectIsSoFunSpringSecurityJWTPracticeProjectIsSoGoodAndThisProjectIsSoFun",
            "key2", "GoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurity",
            "key3", "HelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurity"
    );
    private static final String[] KID_SET = SECRET_KEY_SET.keySet().toArray(new String[0]); // SECRET_KEY_SET 의 Key 값들을 저장; KID == Kid ID
    private static Random randomIndex = new Random();

    /**
     * SECRET_KEY_SET 에서 랜덤한 KEY 가져오기
     *
     * @return kid와 key Pair
     */
    public static Pair<String, Key> getRandomKey() {
        String kid = KID_SET[randomIndex.nextInt(KID_SET.length)]; // KID_SET(== SECRET_KEY_SET 의 Keys) 길이 내에서 랜덤 값을 받아서, 해당 인덱스 값의 Key 반환 
        String secretKey = SECRET_KEY_SET.get(kid); // 반환 받은 Key 값의 Value 반환

        return Pair.of(kid, Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * kid 로 Key 찾기
     *
     * @param kid
     * @return
     */
    public static Key getKey(String kid) {
        String key = SECRET_KEY_SET.getOrDefault(kid, null);

        if (key == null) {
            return null;
        }

        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }
}
