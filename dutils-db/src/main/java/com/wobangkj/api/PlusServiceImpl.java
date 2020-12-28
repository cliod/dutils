package com.wobangkj.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wobangkj.bean.Pageable;
import com.wobangkj.bean.Pager;

/**
 * 默认实现
 *
 * @author cliod
 * @since 11/24/20 1:44 PM
 */
public class PlusServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IService<T> {
	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public T queryById(Long id) {
		return this.getById(id);
	}

	/**
	 * 通过参数查询单条数据
	 *
	 * @param t 参数
	 * @return 实例对象
	 */
	@Override
	public T queryOne(T t) {
		return this.getOne(new QueryWrapper<>(t));
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
		Page<T> page = new Page<>(pageable.getMybatisPage(), pageable.getLimit());
		Page<T> pages = this.page(page.addOrder(OrderItem.desc("id")), new QueryWrapper<>(t));
		return Pager.of(pages.getTotal(), pageable, pages.getRecords());
	}

	/**
	 * 新增数据
	 *
	 * @param t 实例对象
	 * @return 实例对象
	 */
	@Override
	public T insert(T t) {
		this.save(t);
		return t;
	}

	/**
	 * 修改数据
	 *
	 * @param t 实例对象
	 * @return 实例对象
	 */
	@Override
	public T update(T t) {
		this.updateById(t);
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
		return this.removeById(id);
	}

	/**
	 * 查找行数
	 *
	 * @param t 实例对象
	 * @return 实例行数
	 */
	@Override
	public long count(T t) {
		return this.count(new QueryWrapper<>(t));
	}
}
