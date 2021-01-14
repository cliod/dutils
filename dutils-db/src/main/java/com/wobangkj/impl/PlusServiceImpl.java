package com.wobangkj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wobangkj.api.IPlusMapper;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.*;
import com.wobangkj.utils.BeanUtils;
import com.wobangkj.utils.RefUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Locale;
import java.util.Objects;

/**
 * 默认实现
 *
 * @author cliod
 * @since 11/24/20 1:44 PM
 */
public class PlusServiceImpl<M extends BaseMapper<T>, T> extends com.wobangkj.api.ServiceImpl<T> implements com.wobangkj.api.IService<T> {

	protected IService<T> service;

	public PlusServiceImpl(IService<T> service) {
		this.service = service;
	}

	protected PlusServiceImpl() {
	}

	/**
	 * 获取Dao
	 *
	 * @return 通用dao
	 */
	@Override
	public IPlusMapper<T> getDao() {
		return PlusProvider.apply(this.service.getBaseMapper());
	}

	@Resource
	public void setService(IService<T> service) {
		this.service = service;
	}

	public void setService(M service) throws ReflectiveOperationException {
		this.service = new ServiceImpl<>();
		RefUtils.setFieldValue(this.service, "baseMapper", service);
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
		if (!BeanUtils.isEmpty(pageable.getMatch())) {
			wrapper.allEq(pageable.getMatch());
		}
		if (!BeanUtils.isEmpty(pageable.getAmong())) {
			for (Among<?> among : pageable.getAmong()) {
				if (among instanceof DateAmong) {
					if (Objects.isNull(among.getCeiling())) {
						wrapper.ge(among.getColumn(), ((DateAmong) among).getDateFloor());
					} else if (Objects.isNull(among.getFloor())) {
						wrapper.lt(among.getColumn(), ((DateAmong) among).getDateCeiling());
					} else {
						wrapper.between(among.getColumn(), ((DateAmong) among).getDateFloor(), ((DateAmong) among).getDateCeiling());
					}
					continue;
				}
				if (Objects.isNull(among.getCeiling())) {
					wrapper.ge(among.getColumn(), among.getFloor());
				} else if (Objects.isNull(among.getFloor())) {
					wrapper.lt(among.getColumn(), among.getCeiling());
				} else {
					wrapper.between(among.getColumn(), among.getFloor(), among.getCeiling());
				}
			}
		}
		// 原生条件，会有sql注入的风险
		if (!BeanUtils.isEmpty(pageable.getQueries())) {
			for (Query query : pageable.getQueries()) {
				if (query.getRelated().equals("or")) {
					wrapper.or().apply(query.getQuery());
				} else {
					wrapper.apply(query.getQuery());
				}
			}
		}
		Page<T> res = this.service.page(page.addOrder(OrderItem.desc("id")), wrapper);
		return Pager.of(res.getTotal(), pageable, res.getRecords());
	}
}
