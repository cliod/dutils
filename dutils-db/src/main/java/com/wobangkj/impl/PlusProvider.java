package com.wobangkj.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wobangkj.api.IPlusMapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * plus-dao转换
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-05 10:17:38
 */
public class PlusProvider<T> implements IPlusMapper<T> {

	protected BaseMapper<T> mapper;

	public PlusProvider(BaseMapper<T> mapper) {
		this.mapper = mapper;
	}

	/**
	 * 将plus通用mybatis的mapper转为IDao
	 *
	 * @param mapper 通用mapper
	 * @param <T>    泛型
	 * @return IDao对象
	 */
	public static <T> IPlusMapper<T> apply(BaseMapper<T> mapper) {
		return new PlusProvider<>(mapper);
	}

	/**
	 * 插入一条记录
	 *
	 * @param entity 实体对象
	 */
	@Override
	public int insert(T entity) {
		return mapper.insert(entity);
	}

	/**
	 * 根据 ID 删除
	 *
	 * @param id 主键ID
	 */
	@Override
	public int deleteById(Serializable id) {
		return mapper.deleteById(id);
	}

	/**
	 * 根据 columnMap 条件，删除记录
	 *
	 * @param columnMap 表字段 map 对象
	 */
	@Override
	public int deleteByMap(Map<String, Object> columnMap) {
		return mapper.deleteByMap(columnMap);
	}

	/**
	 * 根据 entity 条件，删除记录
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
	 */
	@Override
	public int delete(Wrapper<T> queryWrapper) {
		return mapper.delete(queryWrapper);
	}

	/**
	 * 删除（根据ID 批量删除）
	 *
	 * @param idList 主键ID列表(不能为 null 以及 empty)
	 */
	@Override
	public int deleteBatchIds(Collection<? extends Serializable> idList) {
		return mapper.deleteBatchIds(idList);
	}

	/**
	 * 根据 ID 修改
	 *
	 * @param entity 实体对象
	 */
	@Override
	public int updateById(T entity) {
		return mapper.updateById(entity);
	}

	/**
	 * 根据 whereEntity 条件，更新记录
	 *
	 * @param entity        实体对象 (set 条件值,可以为 null)
	 * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
	 */
	@Override
	public int update(T entity, Wrapper<T> updateWrapper) {
		return mapper.update(entity, updateWrapper);
	}

	/**
	 * 根据 ID 查询
	 *
	 * @param id 主键ID
	 */
	@Override
	public T selectById(Serializable id) {
		return mapper.selectById(id);
	}

	/**
	 * 查询（根据ID 批量查询）
	 *
	 * @param idList 主键ID列表(不能为 null 以及 empty)
	 */
	@Override
	public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
		return mapper.selectBatchIds(idList);
	}

	/**
	 * 查询（根据 columnMap 条件）
	 *
	 * @param columnMap 表字段 map 对象
	 */
	@Override
	public List<T> selectByMap(Map<String, Object> columnMap) {
		return mapper.selectByMap(columnMap);
	}

	/**
	 * 根据 entity 条件，查询一条记录
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	@Override
	public T selectOne(Wrapper<T> queryWrapper) {
		return mapper.selectOne(queryWrapper);
	}

	/**
	 * 根据 Wrapper 条件，查询总记录数
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	@Override
	public Integer selectCount(Wrapper<T> queryWrapper) {
		return mapper.selectCount(queryWrapper);
	}

	/**
	 * 根据 entity 条件，查询全部记录
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	@Override
	public List<T> selectList(Wrapper<T> queryWrapper) {
		return mapper.selectList(queryWrapper);
	}

	/**
	 * 根据 Wrapper 条件，查询全部记录
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	@Override
	public List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper) {
		return mapper.selectMaps(queryWrapper);
	}

	/**
	 * 根据 Wrapper 条件，查询全部记录
	 * <p>注意： 只返回第一个字段的值</p>
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	@Override
	public List<Object> selectObjs(Wrapper<T> queryWrapper) {
		return mapper.selectObjs(queryWrapper);
	}

	/**
	 * 根据 entity 条件，查询全部记录（并翻页）
	 *
	 * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	@Override
	public <E extends IPage<T>> E selectPage(E page, Wrapper<T> queryWrapper) {
		return mapper.selectPage(page, queryWrapper);
	}

	/**
	 * 根据 Wrapper 条件，查询全部记录（并翻页）
	 *
	 * @param page         分页查询条件
	 * @param queryWrapper 实体对象封装操作类
	 */
	@Override
	public <E extends IPage<Map<String, Object>>> E selectMapsPage(E page, Wrapper<T> queryWrapper) {
		return mapper.selectMapsPage(page, queryWrapper);
	}
}
