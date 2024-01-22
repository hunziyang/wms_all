package com.yang.cloud.wms_all.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final String SECRET = "SECRET_WMS";
    public static final long EXPIRE_TIME = 8 * 60 * 60 * 1000;

    public static String sign(String username) {
        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);
        Date expireDate = new Date(now + EXPIRE_TIME);
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "hs256");
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT
                .create()
                .withHeader(header)
                .withSubject(username)
                .withIssuedAt(nowDate)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

}
