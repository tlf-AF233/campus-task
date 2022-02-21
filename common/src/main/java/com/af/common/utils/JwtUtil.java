package com.af.common.utils;

import com.af.common.constant.TokenConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * @author Tanglinfeng
 * @date 2022/2/15 0:18
 */
public class JwtUtil {

    private static final String secret = TokenConstants.SECRET;

    /**
     * 一天
     */
    protected static final long MILLS_DAY = 24 * 60 * 60 * 1000L;

    /**
     * 生成 token
     * @param claims
     * @return
     */
    public static String createToken(Map<String, Object> claims) {
        Date currentDate = new Date();
        // 7天过期
        Date expirationDate = new Date(currentDate.getTime() + MILLS_DAY * 7);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从token中解析声明
     * @param token
     * @return
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(getToken(token))
                .getBody();
    }

    /**
     * token是否过期
     * @param token
     * @return
     */
    public static boolean isExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }

    /**
     * 获取裁剪后的token
     * @param token
     * @return
     */
    public static String getToken(String token) {
        return token.replaceFirst(TokenConstants.TOKEN_PREFIX, "");
    }

    public static Long getUserId(String token) {
        Claims claims = parseToken(token);
        return (Long) claims.get(TokenConstants.CLAIMS_USER_ID);
    }

}
