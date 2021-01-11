package com.wobangkj.handler;

import com.wobangkj.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Mybatis 自定义枚举类型转换
 *
 * @author cliod
 * @since 2020-06-21
 */
public abstract class BaseJsonTypeHandler<E extends Serializable> extends BaseTypeHandler<E> {

	/**
	 * 往数据库设置
	 *
	 * @param ps        数据库执行
	 * @param i         第几个参数
	 * @param parameter 参数
	 * @param jdbcType  参数类型
	 * @throws SQLException sql异常
	 */
	@Override
	public final void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		if (Objects.nonNull(parameter)) ps.setString(i, JsonUtils.toJson(parameter));
		else ps.setString(i, null);
	}

	@Override
	public final E getNullableResult(@NotNull ResultSet resultSet, String s) throws SQLException {
		return this.get(resultSet.getString(s));
	}

	@Override
	public final E getNullableResult(@NotNull ResultSet resultSet, int i) throws SQLException {
		return this.get(resultSet.getString(i));
	}

	@Override
	public final E getNullableResult(@NotNull CallableStatement callableStatement, int i) throws SQLException {
		return this.get(callableStatement.getString(i));
	}

	/**
	 * 返回映射的对象
	 *
	 * @param json 字符串
	 * @return 对象
	 */
	protected abstract E get(String json);

	protected E get(String json, Class<E> clazz) {
		return JsonUtils.fromJson(json, clazz);
	}
}
