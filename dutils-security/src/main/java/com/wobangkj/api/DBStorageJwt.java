package com.wobangkj.api;

import com.wobangkj.exception.SecretException;
import com.wobangkj.utils.HexUtils;
import com.wobangkj.utils.RefUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 */
public class DBStorageJwt extends StorageJwt implements Signable {

	protected String dbName = "jwt";
	protected String username = "root";
	protected String password = "";
	protected String host = "localhost";
	protected int port = 3306;
	protected String tableName = "secret";
	protected String columnName = "secret_key";

	protected Connection conn;

	public DBStorageJwt() throws NoSuchAlgorithmException {
	}

	public DBStorageJwt(KeyGenerator generator) throws NoSuchAlgorithmException {
		super(generator);
	}

	/**
	 * 初始化
	 *
	 * @param password 密码，非空
	 * @return DB_JWT实例
	 */
	public DBStorageJwt instance(String password) {
		try {
			this.password = password;
			this.initialize();
		} catch (NoSuchAlgorithmException e) {
			throw new SecretException((EnumTextMsg) e::getMessage, e);
		}
		return this;
	}

	/**
	 * 初始化
	 *
	 * @param username   用户名
	 * @param password   密码，非空
	 * @param properties 其他参数，顺序为："host", "port", "dbName", "tableName", "columnName"
	 * @return DB_JWT实例
	 */
	public DBStorageJwt instance(String username, String password, String... properties) {
		try {
			if (StringUtils.isNotEmpty(username)) {
				this.username = username;
			}
			this.password = password;
			this.setParam(new String[]{"host", "port", "dbName", "tableName", "columnName"}, properties);
			this.initialize();
		} catch (NoSuchAlgorithmException e) {
			throw new SecretException((EnumTextMsg) e::getMessage, e);
		}
		return this;
	}

	/**
	 * 初始化
	 *
	 * @param properties 参数，可以为："username", "password", "host", "port", "dbName", "tableName", "columnName"
	 * @return DB_JWT实例
	 */
	public DBStorageJwt instance(Map<String, String> properties) {
		try {
			this.setParam(new String[]{"username", "password", "host", "port", "dbName", "tableName", "columnName"}, properties);
			this.initialize();
		} catch (NoSuchAlgorithmException e) {
			throw new SecretException((EnumTextMsg) e::getMessage, e);
		}
		return this;
	}

	/**
	 * 是否允许（密码为空）初始化
	 *
	 * @return 默认不允许
	 */
	protected boolean isEnable() {
		return false;
	}

	/**
	 * 是否允许自动初始化
	 *
	 * @return 是否允许自动初始化
	 */
	@Override
	protected boolean enableInitialize() {
		return StringUtils.isNotEmpty(this.password) || this.isEnable();
	}

	@Override
	protected byte[] getSecret() throws SecretException {
		try {
			String res = this.query();
			if (res == null || "".equals(res)) {
				return null;
			}
			return HexUtils.hex2Bytes(res);
		} catch (SQLException e) {
			throw new SecretException((EnumTextMsg) e::getMessage, e);
		}
	}

	@Override
	protected void setSecret(byte[] data) throws SecretException {
		try {
			String res = this.query();
			String sql;
			if (res == null || "".equals(res)) {
				sql = "INSERT INTO " + tableName + " VALUES ('" + HexUtils.bytes2Hex(data) + "')";
			} else {
				sql = "UPDATE " + tableName + " set " + columnName + " = '" + HexUtils.bytes2Hex(data) + "' WHERE 1=1";
			}
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new SecretException((EnumTextMsg) e::getMessage, e);
		}
	}

	/**
	 * 初始化
	 *
	 * @param generator 秘钥生成器
	 * @throws NoSuchAlgorithmException 没有这样的算法异常
	 */
	@Override
	protected void initialize(KeyGenerator generator) throws NoSuchAlgorithmException {
		if (this.enableInitialize()) {
			Map<String, Object> param = new HashMap<>();
			param.put("useSSL", false);
			param.put("allowPublicKeyRetrieval", true);
			param.put("serverTimezone", "UTC");
			this.conn = this.initializeDB(param);
			super.initialize(generator);
		}
	}

	@Override
	protected void initialize() throws NoSuchAlgorithmException {
		if (this.enableInitialize()) {
			Map<String, Object> param = new HashMap<>();
			param.put("useSSL", false);
			param.put("allowPublicKeyRetrieval", true);
			param.put("serverTimezone", "UTC");
			this.conn = this.initializeDB(param);
			super.initialize();
		}
	}

	protected Connection initializeDB(Map<String, Object> param) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String paramStr = this.paramStr(param);
			String connSql = String.format("jdbc:mysql://%s:%d/?%s", host, port, paramStr);
			Connection conn = DriverManager.getConnection(connSql, username, password);
			PreparedStatement ps = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS `" + dbName + "`");
			ps.execute();

			connSql = String.format("jdbc:mysql://%s:%d/%s?%s", host, port, dbName, paramStr);
			conn = DriverManager.getConnection(connSql, username, password);
			ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `" + tableName + "`( " + columnName + " varchar(64) DEFAULT '' COMMENT '秘钥' ) COMMENT 'jwt秘钥'");
			ps.execute();
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			throw new SecretException((EnumTextMsg) e::getMessage, e);
		} catch (Exception e) {
			throw new SecretException((EnumTextMsg) () -> "位置异常", e);
		}
	}

	protected String paramStr(Map<String, Object> param) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : param.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	protected String query() throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM " + tableName + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(sql);
		return rs.next() ? rs.getString(columnName) : "";
	}

	@SneakyThrows
	protected void setParam(String[] fieldNames, String... properties) {
		for (int i = 0; i < fieldNames.length; i++) {
			if (properties.length > i + 1) {
				if (StringUtils.isEmpty(properties[i])) {
					continue;
				}
				String fieldName = fieldNames[i];
				if ("port".equals(fieldName)) {
					// 特殊处理
					if (NumberUtils.isDigits(properties[i])) {
						RefUtils.setFieldValue(this, fieldName, Integer.parseInt(properties[i]));
					}
				} else {
					RefUtils.setFieldValue(this, fieldName, properties[i]);
				}
			}
		}
	}

	@SneakyThrows
	protected void setParam(String[] fieldNames, Map<String, String> properties) {
		for (String fieldName : fieldNames) {
			if (StringUtils.isNotEmpty(properties.get(fieldName))) {
				if (StringUtils.isEmpty(properties.get(fieldName))) {
					continue;
				}
				if ("port".equals(fieldName)) {
					// 特殊处理
					if (NumberUtils.isDigits(properties.get(fieldName))) {
						RefUtils.setFieldValue(this, fieldName, Integer.parseInt(properties.get(fieldName)));
					}
				} else {
					RefUtils.setFieldValue(this, fieldName, properties.get(fieldName));
				}
			}
		}
	}
}
