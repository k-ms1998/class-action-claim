package com.proejct.ClassActionClaim.jwt.properties;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;

import java.security.Key;
import java.util.Date;

/**
 * JWT 토큰을 생성하거나 Parsing 하는 메소드를 제공합니다
 */
@Slf4j
public class JwtUtils {

    /**
     * 서버에서 토큰의 Subject 찾기
     */
    public static String getSubjectFromToken(String token) {
        // JWT Token 에서 AuthCode 가져오기
        try {
            return Jwts.parserBuilder()
                    .setSigningKeyResolver(KeyResolver.instance) // == new KeyResolver(); KeyResolver 에서 resolveSigningKey 를 통해 Token 의 Secret Key 반환 -> Secret Key 로 Signature 검증
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();      // Token 을 생성할때 Subject 로 생성했기 때문에 Token 에서 Email 을 가져오기 위해서는 Subject 반환

        } catch (Exception e) {
            throw new ExpiredJwtException(null, null, "JWT Token Expired");
        }
    }

    /**
     * 유저(User)가 넘어오면, 유저(User)에 해당하는 토큰(Token) 생성
     *
     * HEADER: alg, kid (alg = Algorithm; kid = KEY ID)
     * PAYLOAD : sub, iat, exp (sub == subject; iat == 토큰 발행 시간, IssuedAt; exp == 토큰 만료 시간, Expiration)
     * SIGNATURE : JwtKey.getRandomKey 로 구한 Secret Key 값을 HS512로 해싱
     *
     */
    public static String createToken(String subject) {
        Date iatDate = new Date();
        Date expDate = new Date(iatDate.getTime() + JwtProperties.SIGNUP_AUTH_EXPIRATION_TIME);
        Pair<String, Key> key = JwtKey.getRandomKey(); // <KeyID, Secret_Key>

        // JWT Token 생성
        return Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) // HEADER; key.getFirst == Key ID
                .setClaims(Jwts.claims().setSubject(subject))     // PAYLOAD
                .setIssuedAt(iatDate)                             // PAYLOAD
                .setExpiration(expDate)                           // PAYLOAD
                .signWith(key.getSecond())                        // SIGNATURE; key.getSecond() == Secret Key
                .compact();
    }
}
