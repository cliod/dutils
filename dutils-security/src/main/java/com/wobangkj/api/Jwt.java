package com.wobangkj.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.wobangkj.utils.BeanUtils;
import com.wobangkj.utils.JsonUtils;
import lombok.SneakyThrows;
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
    protected static final String ISSUER = "_user";
    /**
     * 有效载荷
     */
    protected static final String PAYLOAD = "payload";
    /**
     * 加密算法 可以抽象到环境变量中配置
     */
    protected static final String MAC_NAME = "HMacSHA256";
    protected static Jwt INSTANCE = new Jwt();
    /**
     * 校验类
     */
    protected JWTVerifier verifier;
    /**
     * 加密算法
     */
    protected Algorithm algorithm;
    /**
     * 秘钥生成器
     */
    protected KeyGenerator keyGenerator;
    /**
     * 是否初始化
     */
    private boolean isInitialize;

    @SneakyThrows
    protected Jwt() {
        isInitialize = false;
        keyGenerator = KeyGenerator.getInstance(MAC_NAME);
    }

    @SneakyThrows
    public static @NotNull Jwt getInstance() {
        Jwt jwt = INSTANCE;
        jwt.initialize();
        jwt.isInitialize = true;
        return jwt;
    }

    @Deprecated
    public static @NotNull Jwt init() throws NoSuchAlgorithmException {
        Jwt jwt = INSTANCE;
        jwt.initialize(KeyGenerator.getInstance(MAC_NAME));
        jwt.isInitialize = true;
        return jwt;
    }

    /**
     * 加密，传入一个对象和有效期
     */
    public <T> String sign(T obj, long duration, @NotNull TimeUnit unit) {
        if (!isInitialize) {
            throw new RuntimeException("未初始化");
        }
        JWTCreator.Builder builder = JWT.create();
        long now = System.currentTimeMillis();
        long accumulate = now + unit.toMillis(duration);
        builder.withExpiresAt(new Date(accumulate));
        builder.withIssuedAt(new Date(now));
        builder.withIssuer(ISSUER);
        builder.withClaim(PAYLOAD, JsonUtils.toJson(obj));
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
        if (!isInitialize) {
            throw new RuntimeException("未初始化");
        }
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
        return JsonUtils.fromJson(value.asString(), clazz);
    }

    protected void initialize(KeyGenerator generator) throws NoSuchAlgorithmException {
        if (!BeanUtils.isNull(generator)) {
            this.keyGenerator = generator;
        }
        if (null == this.keyGenerator) {
            this.keyGenerator = KeyGenerator.getInstance(MAC_NAME);
        }
        this.initialize();
    }

    protected void initialize() {
        SecretKey secretKey = keyGenerator.generateKey();
        algorithm = Algorithm.HMAC256(secretKey.getEncoded());
        /*
         * 校验器 用于生成 JWTVerifier 校验器
         */
        Verification verification = JWT.require(algorithm);
        verifier = verification.build();
    }
}
