package cn.microboat.core.utils;

import cn.microboat.core.constant.SecurityConstants;
import cn.microboat.core.constant.TokenConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Map;

/**
 * @author zhouwei
 */
public class JwtUtils {

    /**
     * 一定要超过 32 个字符
     */
    private static final String SECRET = TokenConstants.SECRET + TokenConstants.SECRET;


    /**
     * 生成加密 Key
     *
     * @return SecretKey
     */
    public static SecretKey generalKeyByDecoders() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    /**
     * 从数据声明中生成令牌
     *
     * @param claims 数据声明
     * @return token 令牌
     */
    public static String createToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(generalKeyByDecoders()).compact();
    }

    /**
     * 从令牌中解析出数据声明
     *
     * @param token 令牌
     * @return Claims 数据声明
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(generalKeyByDecoders()).build().parseClaimsJws(token).getBody();
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param token 令牌
     * @return 用户id
     */
    public static String getUserKey(String token) {
        Claims claims = parseToken(token);
        return ConvertUtils.toStr(claims.get(SecurityConstants.USER_KEY), "");
    }

    /**
     * 根据令牌获取用户Id
     *
     * @param token 令牌
     * @return 用户id
     */
    public static String getUserId(String token) {
        Claims claims = parseToken(token);
        return ConvertUtils.toStr(claims.get(SecurityConstants.DETAILS_USER_ID), "");
    }

    /**
     * 根据令牌获取用户名
     *
     * @param token 令牌
     * @return 用户id
     */
    public static String getUserName(String token) {
        Claims claims = parseToken(token);
        return ConvertUtils.toStr(claims.get(SecurityConstants.DETAILS_USERNAME), "");
    }
}
