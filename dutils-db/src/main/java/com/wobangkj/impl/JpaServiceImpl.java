package com.wobangkj.impl;

import com.wobangkj.api.IRepository;
import com.wobangkj.api.IService;
import com.wobangkj.api.ServiceImpl;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Pageable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 默认实现
 *
 * @author cliod
 * @since 9/4/20 11:07 AM
 */
public class JpaServiceImpl<D extends IRepository<T>, T> extends ServiceImpl<T> implements IService<T> {

	protected D dao;

	public JpaServiceImpl(D dao) {
		this.dao = dao;
	}

	protected JpaServiceImpl() {
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

	@Resource
	public void setDao(D dao) {
		this.dao = dao;
	}

	@Override
	public T insert(T t) {
		this.dao.insert(t);
		return t;
	}

	@Override
	public boolean deleteById(Long id) {
		this.dao.deleteById(id);
		return true;
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
		List<Order> orders = new ArrayList<>();
		if (StringUtils.isNotEmpty(pageable.getOrder())) {
			String[] orderStr = pageable.getOrder().split(",");
			for (String order : orderStr) {
				String[] f = order.split(" ");
				if (f.length == 1) {
					orders.add(Order.asc(f[0]));
				} else if (f.length > 1) {
					String ff = f[1].trim();
					if (ff.toLowerCase(Locale.ROOT).equals("desc")) {
						orders.add(Order.desc(ff));
					} else {
						orders.add(Order.asc(ff));
					}
				}
			}
		}
		orders.add(Order.desc("id"));
		Sort sort = Sort.by(orders);
		Example<T> example = Example.of(t);
		Page<T> page = this.dao.findAll(example, PageRequest.of(pageable.getJpaPage(), pageable.getSize(), sort));
		return Pager.of(page.getTotalElements(), pageable, page.getContent());
	}
}
