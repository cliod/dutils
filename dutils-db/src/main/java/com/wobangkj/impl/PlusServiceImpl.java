package com.wobangkj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wobangkj.api.IPlusMapper;
import com.wobangkj.api.IService;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Columns;
import com.wobangkj.domain.Pageable;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Objects;

/**
 * 默认实现
 *
 * @author cliod
 * @since 11/24/20 1:44 PM
 */
public class PlusServiceImpl<M extends BaseMapper<T>, T> extends com.wobangkj.api.ServiceImpl<T> implements IService<T> {

	protected ServiceImpl<M, T> service;

	/**
	 * 获取Dao
	 *
	 * @return 通用dao
	 */
	@Override
	public IPlusMapper<T> getDao() {
		return PlusProvider.apply(this.service.getBaseMapper());
	}

	/**
	 * 新增数据
	 *
	 * @param t 实例对象
	 * @return 实例对象
	 */
	@Override
	public T insert(T t) {
		this.service.save(t);
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
		return this.service.removeById(id);
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
		Page<T> page = new Page<>(pageable.getMybatisPage(), pageable.getLimit());
		if (StringUtils.isNotEmpty(pageable.getOrder())) {
			String[] orders = pageable.getOrder().split(",");
			for (String order : orders) {
				String[] f = order.split(" ");
				if (f.length == 1) {
					page.addOrder(OrderItem.asc(f[0]));
				} else if (f.length > 1) {
					String ff = f[1].trim();
					if (ff.toLowerCase(Locale.ROOT).equals("desc")) {
						page.addOrder(OrderItem.desc(ff));
					} else {
						page.addOrder(OrderItem.asc(ff));
					}
				}
			}
		}
		QueryWrapper<T> wrapper = new QueryWrapper<>(t);
		if (StringUtils.isNotEmpty(pageable.getKey())) {
			Columns columns = fieldCacheMaps.get(t.hashCode());
			if (Objects.isNull(columns)) {
				columns = Columns.of(t.getClass());
				fieldCacheMaps.put(t.hashCode(), columns);
			}
			for (String column : columns.getColumns()) {
				wrapper.like(column, pageable.getKey());
			}
		}
		Page<T> res = this.service.page(page.addOrder(OrderItem.desc("id")), wrapper);
		return Pager.of(res.getTotal(), pageable, res.getRecords());
	}
}
