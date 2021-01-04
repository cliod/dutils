package com.wobangkj.api;

import com.wobangkj.exception.SecretException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
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

	private static final FileStorageJwt INSTANCE = new FileStorageJwt();

	@Getter
	@Setter
	private String filename;

	@SneakyThrows
	private FileStorageJwt() {
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
