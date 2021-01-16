package com.wobangkj.impl;

import com.wobangkj.api.IRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * Jpa-dao转化
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-16 15:10:38
 */
public class JpaProvider<T> implements IRepository<T> {

	protected JpaRepository<T, Long> repository;

	public JpaProvider(JpaRepository<T, Long> repository) {
		this.repository = repository;
	}

	/**
	 * 将plus通用mybatis的mapper转为IDao
	 *
	 * @param mapper 通用mapper
	 * @param <T>    泛型
	 * @return IDao对象
	 */
	public static <T> JpaProvider<T> apply(JpaRepository<T, Long> mapper) {
		return new JpaProvider<>(mapper);
	}

	@Override
	public @NotNull List<T> findAll() {
		return this.repository.findAll();
	}

	@Override
	public @NotNull List<T> findAll(@NotNull Sort sort) {
		return this.repository.findAll(sort);
	}

	/**
	 * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
	 *
	 * @param pageable 分页参数
	 * @return a page of entities
	 */
	@Override
	public @NotNull Page<T> findAll(@NotNull Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	@Override
	public @NotNull List<T> findAllById(@NotNull Iterable<Long> longs) {
		return this.repository.findAllById(longs);
	}

	/**
	 * Returns the number of entities available.
	 *
	 * @return the number of entities.
	 */
	@Override
	public long count() {
		return this.repository.count();
	}

	/**
	 * Deletes the entity with the given id.
	 *
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
	 */
	@Override
	public void deleteById(@NotNull Long id) {
		this.repository.deleteById(id);
	}

	/**
	 * Deletes a given entity.
	 *
	 * @param entity must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given entity is {@literal null}.
	 */
	@Override
	public void delete(@NotNull T entity) {
		this.repository.delete(entity);
	}

	/**
	 * Deletes the given entities.
	 *
	 * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
	 * @throws IllegalArgumentException in case the given {@literal entities} or one of its entities is {@literal null}.
	 */
	@Override
	public void deleteAll(@NotNull Iterable<? extends T> entities) {
		this.repository.deleteAll(entities);
	}

	/**
	 * Deletes all entities managed by the repository.
	 */
	@Override
	public void deleteAll() {
		this.repository.deleteAll();
	}

	/**
	 * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
	 * entity instance completely.
	 *
	 * @param entity must not be {@literal null}.
	 * @return the saved entity; will never be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
	 */
	@Override
	public <S extends T> @NotNull S save(@NotNull S entity) {
		return this.repository.save(entity);
	}

	@Override
	public <S extends T> @NotNull List<S> saveAll(@NotNull Iterable<S> entities) {
		return this.repository.saveAll(entities);
	}

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
	@Override
	public @NotNull Optional<T> findById(@NotNull Long id) {
		return this.repository.findById(id);
	}

	/**
	 * Returns whether an entity with the given id exists.
	 *
	 * @param id must not be {@literal null}.
	 * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
	@Override
	public boolean existsById(@NotNull Long id) {
		return this.repository.existsById(id);
	}

	/**
	 * Flushes all pending changes to the database.
	 */
	@Override
	public void flush() {
		this.repository.flush();
	}

	/**
	 * Saves an entity and flushes changes instantly.
	 *
	 * @param entity 实体对象
	 * @return the saved entity
	 */
	@Override
	public <S extends T> @NotNull S saveAndFlush(@NotNull S entity) {
		return this.repository.saveAndFlush(entity);
	}

	/**
	 * Deletes the given entities in a batch which means it will create a single {@link Query}. Assume that we will clear
	 * the {@link EntityManager} after the call.
	 *
	 * @param entities 实体对象列表
	 */
	@Override
	public void deleteInBatch(@NotNull Iterable<T> entities) {
		this.repository.deleteInBatch(entities);
	}

	/**
	 * Deletes all entities in a batch call.
	 */
	@Override
	public void deleteAllInBatch() {
		this.repository.deleteAllInBatch();
	}

	/**
	 * Returns a reference to the entity with the given identifier. Depending on how the JPA persistence provider is
	 * implemented this is very likely to always return an instance and throw an
	 * {@link EntityNotFoundException} on first access. Some of them will reject invalid identifiers
	 * immediately.
	 *
	 * @param id must not be {@literal null}.
	 * @return a reference to the entity with the given identifier.
	 * @see EntityManager#getReference(Class, Object) for details on when an exception is thrown.
	 */
	@Override
	public @NotNull T getOne(@NotNull Long id) {
		return this.repository.getOne(id);
	}

	/**
	 * Returns a single entity matching the given {@link Example} or {@literal null} if none was found.
	 *
	 * @param example must not be {@literal null}.
	 * @return a single entity matching the given {@link Example} or {@link Optional#empty()} if none was found.
	 * @throws IncorrectResultSizeDataAccessException if the Example yields more than one result.
	 */
	@Override
	public <S extends T> @NotNull Optional<S> findOne(@NotNull Example<S> example) {
		return this.repository.findOne(example);
	}

	@Override
	public <S extends T> @NotNull List<S> findAll(@NotNull Example<S> example) {
		return this.repository.findAll(example);
	}

	@Override
	public <S extends T> @NotNull List<S> findAll(@NotNull Example<S> example, @NotNull Sort sort) {
		return this.repository.findAll(example, sort);
	}

	/**
	 * Returns a {@link Page} of entities matching the given {@link Example}. In case no match could be found, an empty
	 * {@link Page} is returned.
	 *
	 * @param example  must not be {@literal null}.
	 * @param pageable can be {@literal null}.
	 * @return a {@link Page} of entities matching the given {@link Example}.
	 */
	@Override
	public <S extends T> @NotNull Page<S> findAll(@NotNull Example<S> example, @NotNull Pageable pageable) {
		return this.repository.findAll(example, pageable);
	}

	/**
	 * Returns the number of instances matching the given {@link Example}.
	 *
	 * @param example the {@link Example} to count instances for. Must not be {@literal null}.
	 * @return the number of instances matching the {@link Example}.
	 */
	@Override
	public <S extends T> long count(@NotNull Example<S> example) {
		return this.repository.count(example);
	}

	/**
	 * Checks whether the data store contains elements that match the given {@link Example}.
	 *
	 * @param example the {@link Example} to use for the existence check. Must not be {@literal null}.
	 * @return {@literal true} if the data store contains elements that match the given {@link Example}.
	 */
	@Override
	public <S extends T> boolean exists(@NotNull Example<S> example) {
		return this.repository.exists(example);
	}
}
