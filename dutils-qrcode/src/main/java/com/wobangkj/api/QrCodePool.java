package com.wobangkj.api;

import cn.hutool.core.map.FixedLinkedHashMap;

import java.util.Objects;

/**
 * qrCode
 *
 * @author cliod
 * @since 8/22/20 1:23 PM
 */
public class QrCodePool {
	private final FixedLinkedHashMap<String, QrCode> caches;

	public QrCodePool(int capacity) {
		caches = new FixedLinkedHashMap<>(capacity);
	}

	public QrCode get(Object key) {
		QrCode qrCode = caches.get(String.valueOf(key));
		if (Objects.isNull(qrCode)) {
			qrCode = FastQrCode.getInstance();
			caches.put(String.valueOf(key), qrCode);
			return qrCode;
		}
		return qrCode;
	}
}
