package com.wobangkj.api;

import com.wobangkj.cache.Cacheables;
import com.wobangkj.exception.SecretException;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 */
public class CacheStorageJwt extends StorageJwt implements Signable {

	@Getter
	@Setter
	protected Cacheables cache;

	protected CacheStorageJwt(Cacheables cache) throws NoSuchAlgorithmException {
		super();
		this.cache = cache;
	}

	public CacheStorageJwt(KeyGenerator generator, Cacheables cache) throws NoSuchAlgorithmException {
		super(generator);
		this.cache = cache;
	}

	/**
	 * 是否允许自动初始化
	 *
	 * @return 是否允许自动初始化
	 */
	@Override
	protected boolean enableInitialize() {
		return true;
	}

	@Override
	protected byte[] getSecret() throws SecretException {
		Cache.@NotNull ValueWrapper secret = this.cache.get("jwt.secret.key");
		return (byte[]) secret.get();
	}

	@Override
	protected void setSecret(byte[] data) throws SecretException {
		if (data != null) {
			this.cache.set("jwt.secret.key", data);
		}
	}
}
