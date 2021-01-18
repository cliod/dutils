package com.wobangkj.api;

import com.wobangkj.domain.Pageable;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import static com.wobangkj.api.Response.DELETE;
import static com.wobangkj.api.Response.ok;

/**
 * 基础controller
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-18 11:24:10
 */
public abstract class BaseController<T> implements IController<T> {

	private IService<T> service;

	@Resource
	public void setService(IService<T> service) {
		this.service = service;
	}

	/**
	 * 通过查询单条数据
	 *
	 * @param t 参数
	 * @return 单条数据
	 */
	@Override
	public Object get(T t) {
		T data = this.service.queryOne(t);
		Assert.notNull(data, "暂无数据");
		return ok(data);
	}

	/**
	 * 查询多条数据
	 *
	 * @param t        参数
	 * @param pageable 分页、排序，模糊查询
	 * @return 列表
	 */
	@Override
	public Object list(T t, Pageable pageable) {
		return ok(this.service.queryAll(t, pageable));
	}

	/**
	 * 添加一条数据
	 *
	 * @param t 数据
	 * @return 结果
	 */
	@Override
	public Object add(T t) {
		return ok(this.service.insert(t));
	}

	/**
	 * 编辑一条数据
	 *
	 * @param t 数据
	 * @return 结果
	 */
	@Override
	public Object edit(T t) {
		return ok(this.service.update(t));
	}

	/**
	 * 删除一条数据
	 *
	 * @param id id
	 * @return 结果
	 */
	@Override
	public Object del(Long id) {
		return this.service.deleteById(id) ? ok() : DELETE;
	}
}
