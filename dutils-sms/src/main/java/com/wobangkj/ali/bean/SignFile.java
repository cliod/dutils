package com.wobangkj.ali.bean;

import com.aliyuncs.dysmsapi.model.v20170525.AddSmsSignRequest;
import com.aliyuncs.dysmsapi.model.v20170525.ModifySmsSignRequest;
import lombok.Data;

/**
 * 签名证明文件
 *
 * @author cliod
 * @version 1.0
 * @since 2021-04-22 13:13:57
 */
@Data
public class SignFile {
	/**
	 * 文件Base64编码
	 */
	private String fileContents;
	/**
	 * 文件后缀名
	 */
	private String fileSuffix;

	/**
	 * 转为添加的签名文件
	 *
	 * @return 签名文件
	 */
	public AddSmsSignRequest.SignFileList toAddSignFile() {
		AddSmsSignRequest.SignFileList file = new AddSmsSignRequest.SignFileList();
		file.setFileContents(this.fileContents);
		file.setFileSuffix(this.fileSuffix);
		return file;
	}

	/**
	 * 转为修改的签名文件
	 *
	 * @return 签名文件
	 */
	public ModifySmsSignRequest.SignFileList toModifySignFile() {
		ModifySmsSignRequest.SignFileList file = new ModifySmsSignRequest.SignFileList();
		file.setFileContents(this.fileContents);
		file.setFileSuffix(this.fileSuffix);
		return file;
	}
}
