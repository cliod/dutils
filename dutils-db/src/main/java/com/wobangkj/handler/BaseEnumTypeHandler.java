package com.wobangkj.handler;

import com.wobangkj.api.EnumType;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.jetbrains.annotations.NotNull;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mybatis 自定义枚举类型转换
 *
 * @author cliod
 * @since 2020-06-21
 */
@Slf4j
public abstract class BaseEnumTypeHandler<E extends EnumType> extends BaseTypeHandler<E> {

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
		int code;
		if (parameter != null) {
			code = parameter.getCode();
		} else {
			code = 0;
		}
		if (jdbcType == null) {
			ps.setInt(i, code);
		} else {
			ps.setObject(i, code, jdbcType.TYPE_CODE);
		}
	}

	@Override
	public final E getNullableResult(@NotNull ResultSet resultSet, String s) throws SQLException {
		return this.get(resultSet.getInt(s));
	}

	@Override
	public final E getNullableResult(@NotNull ResultSet resultSet, int i) throws SQLException {
		return this.get(resultSet.getInt(i));
	}

	@Override
	public final E getNullableResult(@NotNull CallableStatement callableStatement, int i) throws SQLException {
		return this.get(callableStatement.getInt(i));
	}

	/**
	 * 获取枚举结果对象
	 *
	 * @param v 获取
	 * @return 枚举对象
	 */
	protected abstract E get(Integer v);
}
