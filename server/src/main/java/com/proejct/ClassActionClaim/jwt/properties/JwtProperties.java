package com.proejct.ClassActionClaim.jwt.properties;

/**
 * JWT 기본 설정값
 * ex: 만료 시간 등
 */
public class JwtProperties {
    public static final Integer SIGNUP_AUTH_EXPIRATION_TIME = 180000; // 3 Minutes
    public static final String SIGNUP_AUTH_COOKIE_NAME = "JWT-SIGNUP-AUTH";
}
