package com.wobangkj.impl;

import com.wobangkj.api.IService;
import com.wobangkj.api.ITkMapper;
import com.wobangkj.api.ServiceImpl;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.*;
import com.wobangkj.utils.BeanUtils;
import com.wobangkj.utils.RefUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * 默认实现
 *
 * @author cliod
 * @since 9/4/20 11:07 AM
 */
public class TkServiceImpl<D extends ITkMapper<T>, T> extends ServiceImpl<T> implements IService<T> {

	protected D dao;

	public TkServiceImpl(D dao) {
		this.dao = dao;
	}

	protected TkServiceImpl() {
	}

	/**
	 * 获取Dao
	 *
	 * @return 通用dao
	 */
	@Override
	public D getDao() {
		return this.dao;
	}

	/**
	 * 设置Dao
	 *
	 * @param dao dao对象
	 */
	@Resource
	public void setDao(D dao) {
		this.dao = dao;
	}

	/**
	 * 新增数据
	 *
	 * @param t 实例对象
	 * @return 实例对象
	 */
	@Override
	public T insert(T t) {
		this.dao.insertSelective(t);
		return t;
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public boolean deleteById(Long id) {
		return this.dao.deleteById(id) > 0;
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public boolean deleteById(Object id) {
		return this.dao.deleteById(id) > 0;
	}

	/**
	 * 查询
	 *
	 * @param t         条件
	 * @param condition 分页
	 * @return 列表
	 */
	@Override
	@SneakyThrows
	public Pager<T> queryAll(T t, Condition condition) {
		// 构建查询条件对象
		Example example = Example.builder(t.getClass()).build();
		// 排序
		if (StringUtils.isNotEmpty(condition.getOrder())) {
			example.setOrderByClause(condition.getOrder());
		} else {
			// 默认id倒序
			example.setOrderByClause("id desc");
		}
		// 模糊匹配
		if (StringUtils.isNotEmpty(condition.getKey())) {
			if (Objects.isNull(this.fieldCache)) {
				this.fieldCache = Columns.of(t.getClass());
			}
			Example.Criteria criteria = example.createCriteria();
			for (String column : this.fieldCache.getLikeColumns()) {
				if (StringUtils.isEmpty(column)) {
					continue;
				}
				criteria.orLike(column, condition.getLikeKey());
			}
			if (!example.getOredCriteria().contains(criteria)) {
				example.and(criteria);
			}
		}
		// 关键词匹配
		Map<String, Object> fieldValues = RefUtils.getFieldValues(t);
		if (!BeanUtils.isEmpty(fieldValues)) {
			Example.Criteria criteria = example.createCriteria();
			if (Objects.isNull(this.fieldCache)) {
				this.fieldCache = Columns.of(t.getClass());
			}
			for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
				if (Arrays.asList(this.fieldCache.getColumns()).contains(entry.getKey()) && Objects.nonNull(entry.getValue())) {
					criteria.andEqualTo(entry.getKey(), entry.getValue());
				}
			}
			if (!example.getOredCriteria().contains(criteria)) {
				example.and(criteria);
			}
		}
		if (!BeanUtils.isEmpty(condition.getMatch())) {
			Example.Criteria criteria = example.createCriteria();
			for (Map.Entry<String, Object> entry : condition.getMatch().entrySet()) {
				criteria.andEqualTo(entry.getKey(), entry.getValue());
			}
			if (!example.getOredCriteria().contains(criteria)) {
				example.and(criteria);
			}
		}
		// 范围匹配
		if (!BeanUtils.isEmpty(condition.getAmong())) {
			Example.Criteria criteria = example.createCriteria();
			for (Among<?> among : condition.getAmong()) {
				if (among instanceof DateAmong) {
					if (Objects.isNull(among.getCeiling())) {
						criteria.andGreaterThanOrEqualTo(among.getColumn(), ((DateAmong) among).getDateFloor());
					} else if (Objects.isNull(among.getFloor())) {
						criteria.andLessThan(among.getColumn(), ((DateAmong) among).getDateCeiling());
					} else {
						criteria.andBetween(among.getColumn(), ((DateAmong) among).getDateFloor(), ((DateAmong) among).getDateCeiling());
					}
					continue;
				}
				if (Objects.isNull(among.getCeiling())) {
					criteria.andGreaterThanOrEqualTo(among.getColumn(), among.getFloor());
				} else if (Objects.isNull(among.getFloor())) {
					criteria.andLessThan(among.getColumn(), among.getCeiling());
				} else {
					criteria.andBetween(among.getColumn(), among.getFloor(), among.getCeiling());
				}
			}
			if (!example.getOredCriteria().contains(criteria)) {
				example.and(criteria);
			}
		}
		// 原生条件查询，会有sql注入的风险
		if (!BeanUtils.isEmpty(condition.getQueries())) {
			Example.Criteria criteria = example.createCriteria();
			String ao = condition.getQueries().get(0).getRelated();
			for (Query query : condition.getQueries()) {
				if (query.getRelated().equalsIgnoreCase("or")) {
					criteria.orCondition(query.getQuery());
				} else {
					criteria.andCondition(query.getQuery());
				}
			}
			if (!example.getOredCriteria().contains(criteria)) {
				if (ao.equalsIgnoreCase("or")) {
					example.or(criteria);
				} else {
					example.and(criteria);
				}
			}
		}
		long count = this.dao.selectCountByExample(example);
		if (count == 0) {
			return Pager.empty();
		}
		return Pager.of(count, condition, this.dao.selectByExampleAndRowBounds(example, new RowBounds(condition.getOffset(), condition.getLimit())));
	}
}
