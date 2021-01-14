package com.wobangkj.impl;

import com.wobangkj.api.IMapper;
import com.wobangkj.api.IService;
import com.wobangkj.api.ServiceImpl;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.*;
import com.wobangkj.utils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * 默认实现
 *
 * @author cliod
 * @since 9/4/20 11:07 AM
 */
public class TkServiceImpl<D extends IMapper<T>, T> extends ServiceImpl<T> implements IService<T> {

	protected D dao;

	public TkServiceImpl(D dao) {
		this.dao = dao;
	}

	protected TkServiceImpl() {
	}

	@Override
	public D getDao() {
		return this.dao;
	}

	@Resource
	public void setDao(D dao) {
		this.dao = dao;
	}

	@Override
	public T insert(T t) {
		this.dao.insertSelective(t);
		return t;
	}

	@Override
	public boolean deleteById(Long id) {
		return this.dao.deleteById(id) > 0;
	}

	/**
	 * 查询
	 *
	 * @param t        条件
	 * @param pageable 分页
	 * @return 列表
	 */
	@Override
	public Pager<T> queryAll(T t, Pageable pageable) {
		if (StringUtils.isEmpty(pageable.getKey()) && StringUtils.isEmpty(pageable.getOrder())) {
			return super.queryAll(t, pageable);
		}
		Example example = Example.builder(t.getClass()).build();
		if (StringUtils.isNotEmpty(pageable.getOrder())) {
			example.setOrderByClause(pageable.getOrder());
		} else {
			example.setOrderByClause("id desc");
		}
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(pageable.getKey())) {
			Columns columns = fieldCacheMaps.get(t.hashCode());
			if (Objects.isNull(columns)) {
				columns = Columns.of(t.getClass());
				fieldCacheMaps.put(t.hashCode(), columns);
			}
			for (String column : columns.getColumns()) {
				if (StringUtils.isEmpty(column)) {
					continue;
				}
				criteria.andLike(column, pageable.getKey());
			}
		}
		if (!BeanUtils.isEmpty(pageable.getMatch())) {
			for (Map.Entry<String, Object> entry : pageable.getMatch().entrySet()) {
				criteria.andEqualTo(entry.getKey(), entry.getValue());
			}
		}
		if (!BeanUtils.isEmpty(pageable.getAmong())) {
			for (Among<?> among : pageable.getAmong()) {
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
		}
		// 原生条件，会有sql注入的风险
		if (!BeanUtils.isEmpty(pageable.getQueries())) {
			for (Query query : pageable.getQueries()) {
				if (query.getRelated().equals("or")) {
					criteria.orCondition(query.getQuery());
				} else {
					criteria.andCondition(query.getQuery());
				}
			}
		}
		long count = this.dao.selectCountByExample(example);
		if (count == 0) {
			return Pager.empty();
		}
		return Pager.of(count, pageable, this.dao.selectByExampleAndRowBounds(example, new RowBounds(pageable.getOffset(), pageable.getLimit())));
	}
}
