package com.wobangkj.api;

import com.wobangkj.exception.SecretException;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.crypto.KeyGenerator;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 */
public class FileStorageJwt extends StorageJwt implements Signable {

	private static final FileStorageJwt INSTANCE;

	static {
		try {
			INSTANCE = new FileStorageJwt();
		} catch (NoSuchAlgorithmException e) {
			throw new SecretException((EnumTextMsg) () -> "初始化失败", e);
		}
	}

	@Getter
	@Setter
	private String filename;

	private FileStorageJwt() throws NoSuchAlgorithmException {
		this("jwt.secret.key");
	}

	protected FileStorageJwt(String filename) throws NoSuchAlgorithmException {
		super();
		this.filename = filename;
	}

	public FileStorageJwt(KeyGenerator generator, String filename) throws NoSuchAlgorithmException {
		super(generator);
		this.filename = filename;
	}

	public static @NotNull FileStorageJwt getInstance() {
		return INSTANCE;
	}

	@Deprecated
	public static @NotNull FileStorageJwt getInstance(String filename) {
		FileStorageJwt jwt = INSTANCE;
		jwt.setFilename(filename);
		jwt.initialize();
		return jwt;
	}

	@Deprecated
	public static @NotNull FileStorageJwt init() {
		return INSTANCE;
	}

	@Override
	protected byte[] getSecret() throws SecretException {
		byte[] bytes;
		try (FileInputStream fis = new FileInputStream(this.filename)) {
			bytes = new byte[Math.toIntExact(fis.available())];
			int i = fis.read(bytes);
			if (i > 0) {
				return bytes;
			}
		} catch (IOException e) {
			throw new SecretException(217, e);
		}
		return bytes;
	}

	@Override
	protected void setSecret(byte[] data) throws SecretException {
		if (data != null) {
			try (FileOutputStream fos = new FileOutputStream(this.filename)) {
				fos.write(data, 0, data.length);
				fos.flush();
			} catch (Exception e) {
				throw new SecretException(217, e);
			}
		}
	}
}
