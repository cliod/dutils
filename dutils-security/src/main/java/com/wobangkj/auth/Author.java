package com.wobangkj.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 授权者
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-04 11:42:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户ip
	 */
	private String ip;
	/**
	 * 角色，数值越小，权限越大
	 */
	private Integer role;
	/**
	 * json格式自定义数据
	 */
	private String data;
}
