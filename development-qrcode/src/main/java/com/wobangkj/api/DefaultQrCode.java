package com.wobangkj.api;

/**
 * qrCode
 *
 * @author cliod
 * @since 8/22/20 1:23 PM
 */
public class DefaultQrCode extends BaseQrCode {

	public DefaultQrCode() {
	}

	public DefaultQrCode(String content) {
		setContent(content);
	}

	public static DefaultQrCode getInstance() {
		return new DefaultQrCode();
	}

	public static DefaultQrCode getInstance(String content) {
		return new DefaultQrCode(content);
	}
}
