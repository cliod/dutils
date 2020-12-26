package com.wobangkj.api;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import javax.crypto.KeyGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 */
public class FileStorageJwt extends StorageJwt implements Signable {

	private static final FileStorageJwt INSTANCE = new FileStorageJwt("secret.key");
	@Getter
	@Setter
	private String filename;

	protected FileStorageJwt(String filename) {
		super();
		this.filename = filename;
	}

	@SneakyThrows
	public static @NotNull FileStorageJwt getInstance() {
		FileStorageJwt jwt = INSTANCE;
		jwt.initialize();
		return jwt;
	}

	@SneakyThrows
	public static @NotNull FileStorageJwt getInstance(String filename) {
		FileStorageJwt jwt = INSTANCE;
		jwt.setFilename(filename);
		jwt.initialize();
		return jwt;
	}

	@Deprecated
	public static @NotNull FileStorageJwt init() throws NoSuchAlgorithmException {
		FileStorageJwt jwt = INSTANCE;
		jwt.initialize(KeyGenerator.getInstance(MAC_NAME));
		return jwt;
	}

	@Override
	protected byte[] getSecret() {
		File file = new File(filename);
		byte[] bytes = new byte[Math.toIntExact(file.length())];
		try (FileInputStream fis = new FileInputStream(file)) {
			int i = fis.read(bytes);
			if (i > 0) {
				return bytes;
			}
		} catch (Exception ignore) {}
		return bytes;
	}

	@Override
	protected void setSecret(byte[] data) {
		if (data != null) {
			File file = new File(filename);
			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(data, 0, data.length);
				fos.flush();
			} catch (Exception ignore) {}
		}
	}
}
