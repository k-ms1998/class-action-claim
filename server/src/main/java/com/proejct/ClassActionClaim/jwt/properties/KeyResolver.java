package com.proejct.ClassActionClaim.jwt.properties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;

import java.security.Key;

/**
 * JwsHeader 를 통해 Signature 검증에 필요한 Key 가져오기
 */
public class KeyResolver extends SigningKeyResolverAdapter {
    public static KeyResolver instance = new KeyResolver();

    /**
     * Header 에서 KeyId 가져오기
     * @param header
     * @param claims
     * @return
     */
    @Override
    public Key resolveSigningKey(JwsHeader header, Claims claims) {
        String keyId = header.getKeyId();
        if (keyId == null) {
            return null;
        }

        return JwtKey.getKey(keyId);
    }
}
