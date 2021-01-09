package com.wobangkj.impl;

import com.wobangkj.api.IMapper;
import com.wobangkj.api.IService;
import com.wobangkj.api.ServiceImpl;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Columns;
import com.wobangkj.domain.Pageable;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.entity.Example;

import java.util.Objects;

/**
 * 默认实现
 *
 * @author cliod
 * @since 9/4/20 11:07 AM
 */
public class TkServiceImpl<D extends IMapper<T>, T> extends ServiceImpl<T> implements IService<T> {

	protected final D dao;

	public TkServiceImpl(D dao) {
		this.dao = dao;
	}

	@Override
	public D getDao() {
		return this.dao;
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
		long count = this.dao.selectCountByExample(example);
		if (count == 0) {
			return Pager.empty();
		}
		return Pager.of(count, pageable, this.dao.selectByExampleAndRowBounds(example, new RowBounds(pageable.getOffset(), pageable.getLimit())));
	}
}
