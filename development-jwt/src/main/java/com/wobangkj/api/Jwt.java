package com.wobangkj.api;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.wobangkj.utils.BeanUtils;
import org.jetbrains.annotations.NotNull;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 * package : com.wobangkj.util
 */
public class Jwt {

    /**
     * 发布者 后面一块去校验
     */
    private static final String ISSUER = "_user";

    private static final String PAYLOAD = "payload";
    /**
     * 加密算法 可以抽象到环境变量中配置
     */
    private static final String MAC_NAME = "HMacSHA256";
    /**
     * 校验类
     */
    private JWTVerifier verifier;
    /**
     * 加密算法
     */
    private Algorithm algorithm;
    /**
     * 秘钥生成器
     */
    private KeyGenerator keyGenerator;

    @NotNull
    public static Jwt init() throws NoSuchAlgorithmException {
        Jwt jwt = new Jwt();
        jwt.init(KeyGenerator.getInstance(MAC_NAME));
        return jwt;
    }

    /**
     * 加密，传入一个对象和有效期
     */
    public <T> String sign(T obj, long duration, @NotNull TimeUnit unit) {
        JWTCreator.Builder builder = JWT.create();
        long now = System.currentTimeMillis();
        long accumulate = now + unit.toMillis(duration);
        builder.withExpiresAt(new Date(accumulate));
        builder.withIssuedAt(new Date(now));
        builder.withIssuer(ISSUER);
        builder.withClaim(PAYLOAD, JSON.toJSONString(obj));
        return builder.sign(algorithm);
    }

    /**
     * 加密，传入一个对象和有效期
     */
    public <T> String sign(T obj, long duration) {
        return sign(obj, duration, TimeUnit.SECONDS);
    }

    /**
     * 解密，传入一个加密后的token字符串和解密后的类型
     */
    public Map<?, ?> unsign(String jwt) {
        return this.unsign(jwt, Map.class);
    }

    /**
     * 解密
     *
     * @param jwt   jwt密匙
     * @param clazz 类
     * @param <T>   类型
     * @return 结果对象
     */
    public <T> T unsign(String jwt, Class<T> clazz) {
        final DecodedJWT claims = verifier.verify(jwt);
        Date date = claims.getExpiresAt();
        if (BeanUtils.isNull(date)) {
            return null;
        }
        long exp = date.getTime();
        long now = System.currentTimeMillis();
        if (exp < now) {
            return null;
        }
        Map<String, Claim> payload = claims.getClaims();
        Claim value = payload.get(PAYLOAD);
        if (BeanUtils.isNull(value)) {
            return null;
        }
        return JSON.parseObject(value.asString(), clazz);
    }

    private void init(KeyGenerator generator) throws NoSuchAlgorithmException {
        if (!BeanUtils.isNull(generator)) {
            this.keyGenerator = generator;
        }
        if (null == this.keyGenerator) {
            this.keyGenerator = KeyGenerator.getInstance(MAC_NAME);
        }
        SecretKey secretKey = keyGenerator.generateKey();
        algorithm = Algorithm.HMAC256(secretKey.getEncoded());
        /*
         * 校验器 用于生成 JWTVerifier 校验器
         */
        Verification verification = JWT.require(algorithm);
        verifier = verification.build();
    }
}
