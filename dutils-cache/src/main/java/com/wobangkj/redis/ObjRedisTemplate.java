package com.wobangkj.redis;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis object implement
 *
 * @author cliod
 * @since 10/10/20 2:57 PM
 */
@Deprecated
public class ObjRedisTemplate extends RedisTemplate<Object, Object> {

	/**
	 * Constructs a new <code>StringRedisTemplate</code> instance. {@link #setConnectionFactory(RedisConnectionFactory)}
	 * and {@link #afterPropertiesSet()} still need to be called.
	 */
	public ObjRedisTemplate() {
		setKeySerializer(RedisSerializer.byteArray());
		setValueSerializer(RedisSerializer.byteArray());
		setHashKeySerializer(RedisSerializer.byteArray());
		setHashValueSerializer(RedisSerializer.byteArray());
	}

	/**
	 * Constructs a new <code>StringRedisTemplate</code> instance ready to be used.
	 *
	 * @param connectionFactory connection factory for creating new connections
	 */
	public ObjRedisTemplate(RedisConnectionFactory connectionFactory) {
		this();
		setConnectionFactory(connectionFactory);
		afterPropertiesSet();
	}

	@Override
	protected @NotNull RedisConnection preProcessConnection(@NotNull RedisConnection connection, boolean existingConnection) {
		return new DefaultStringRedisConnection(connection);
	}
}
