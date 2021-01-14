package com.wobangkj.api;

import com.wobangkj.domain.Pageable;
import com.wobangkj.bean.Pager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 通用Jpa, SpringBootJpa实现
 *
 * @author cliod
 * @since 9/16/20 9:23 AM
 */
public interface IRepository<T> extends IDao<T>, JpaRepository<T, Long> {
	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	default T queryById(Long id) {
		return this.findById(id).orElse(null);
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t 实例对象
	 * @return 对象列表
	 */
	@Override
	default T queryOne(T t) {
		return this.findOne(Example.of(t)).orElse(null);
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t 实例对象
	 * @return 对象列表
	 */
	@Override
	default List<T> queryAll(T t) {
		return this.findAll(Example.of(t), Sort.by("id").descending());
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t      实例对象
	 * @param offset 分页
	 * @param limit  分页
	 * @return 对象列表
	 */
	@Override
	default List<T> queryAllLimit(T t, int offset, int limit) {
		Pageable pageable = Pageable.of(offset, limit);
		return this.queryAllPage(t, pageable).getData();
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t        实例对象
	 * @param pageable 分页
	 * @return 对象列表
	 */
	@Override
	default Pager<T> queryAllPage(T t, Pageable pageable) {
		Page<T> page = this.findAll(Example.of(t), PageRequest.of(pageable.getJpaPage(), pageable.getSize()));
		return Pager.of(page.getTotalElements(), pageable, page.getContent());
	}

	/**
	 * 修改数据
	 *
	 * @param t 实例对象
	 * @return 影响行数
	 */
	@Override
	default int update(T t) {
		this.save(t);
		return 1;
	}

	/**
	 * 添加数据
	 *
	 * @param t 实例对象
	 * @return 影响行数
	 */
	default int insert(T t) {
		this.save(t);
		return 1;
	}

	/**
	 * 查找个数
	 *
	 * @param t 对象
	 * @return 行数
	 */
	@Override
	default long count(T t) {
		return this.count(Example.of(t));
	}
}
