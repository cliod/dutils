package com.wobangkj.api;

import com.wobangkj.exception.SecretException;
import com.wobangkj.utils.HexUtils;
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

	protected Connection conn;

	public DBStorageJwt() throws NoSuchAlgorithmException {
	}

	public DBStorageJwt(KeyGenerator generator) throws NoSuchAlgorithmException {
		super(generator);
	}

	public DBStorageJwt instance(String password) {
		try {
			this.password = password;
			this.initialize();
		} catch (NoSuchAlgorithmException e) {
			throw new SecretException((EnumTextMsg) e::getMessage, e);
		}
		return this;
	}

	public DBStorageJwt instance(String username, String password, String... param) {
		try {
			if (StringUtils.isNotEmpty(username)) {
				this.username = username;
			}
			this.password = password;
			if (param != null && param.length > 0) {
				this.host = param[0];
				if (param.length > 1) {
					this.port = Integer.parseInt(param[1]);
					if (param.length > 2) {
						this.dbName = param[2];
						if (param.length > 3) {
							this.tableName = param[3];
						}
					}
				}
			}
			this.initialize();
		} catch (NoSuchAlgorithmException e) {
			throw new SecretException((EnumTextMsg) e::getMessage, e);
		}
		return this;
	}

	public DBStorageJwt instance(Map<String, String> properties) {
		try {
			if (StringUtils.isNotEmpty(properties.get("username"))) {
				this.username = properties.get("username");
			}
			if (StringUtils.isNotEmpty(properties.get("password"))) {
				this.password = properties.get("password");
			}
			if (StringUtils.isNotEmpty(properties.get("dbName"))) {
				this.dbName = properties.get("dbName");
			}
			if (StringUtils.isNotEmpty(properties.get("tableName"))) {
				this.tableName = properties.get("tableName");
			}
			if (StringUtils.isNotEmpty(properties.get("host"))) {
				this.host = properties.get("host");
			}
			if (NumberUtils.isDigits(properties.get("port"))) {
				this.port = Integer.parseInt(properties.get("port"));
			}
			this.initialize();
		} catch (NoSuchAlgorithmException e) {
			throw new SecretException((EnumTextMsg) e::getMessage, e);
		}
		return this;
	}

	/**
	 * 是否允许自动初始化
	 *
	 * @return 是否允许自动初始化
	 */
	@Override
	protected boolean enableInitialize() {
		return StringUtils.isNotEmpty(this.password);
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
				sql = "UPDATE " + tableName + " set secret_key = '" + HexUtils.bytes2Hex(data) + "' WHERE 1=1";
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
			ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `" + tableName + "`( secret_key varchar(64) DEFAULT '' COMMENT '秘钥' ) COMMENT 'jwt秘钥'");
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

	private String query() throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM " + tableName + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(sql);
		if (!rs.next()) {
			return "";
		}
		return rs.getString("secret_key");
	}
}
