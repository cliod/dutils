package com.wobangkj.api;

/**
 * 旧版序列化
 *
 * @author cliod
 * @since 11/28/20 3:09 PM
 */
@Deprecated
public interface Session extends SessionSerializable {

	/**
	 * toString 方法,一定的重写toString()方法
	 *
	 * @return json
	 */
	@Override
	String toString();

}
