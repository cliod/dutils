package com.wobangkj.cache;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * lru缓存
 *
 * @author cliod
 * @since 7/10/20 3:48 PM
 */
public class LruCache<K, V> {
	private final int maxSize;
	/**
	 * hash
	 */
	private final Map<K, CacheNode<K, V>> cache;
	private final CacheNode<K, V> first;
	private final CacheNode<K, V> last;

	public LruCache(int size) {
		this.maxSize = size;
		cache = new HashMap<>(size);
		// 虚拟头节点
		first = new CacheNode<>();
		//虚拟尾节点
		last = new CacheNode<>();
		// 虚拟节点用于帮助操作

		// 构建成双向列表
		first.next = last;
		last.pre = first;
	}

	public V get(K key) {
		CacheNode<K, V> node = cache.get(key);
		if (Objects.isNull(node)) {
			return null;
		}
		// 如果存在, 移动双向列表表头 (用户进行淘汰尾部)
		this.moveToHead(node);
		return node.value;
	}

	public void put(K key, V value) {
		CacheNode<K, V> node = cache.get(key);
		if (Objects.isNull(node)) {
			// 如果不存在, 插入在表头
			// 当数据满了的时候, 删除表尾部数据
			node = new CacheNode<>();
			node.key = key;
			node.value = value;
			if (maxSize <= cache.size()) {
				// 从双向列表删除
				popTail();
				// 从hash删除
				cache.remove(key);
			}
			// 添加到hash
			cache.put(key, node);
			// 插入到双向列表
			addNode(node);
		} else {
			node.value = value;
			// 插入后移动到表头
			this.moveToHead(node);
		}
	}

	/**
	 * 删除并返回最后一个数据
	 *
	 * @return 节点数据
	 */
	private CacheNode<K, V> popTail() {
		// 获取尾部节点: 虚拟节点的前一个节点
		CacheNode<K, V> node = last.pre;
		removeNode(node);
		return node;
	}

	/**
	 * 删除一个节点
	 *
	 * @param node 节点
	 */
	private void removeNode(@NotNull CacheNode<K, V> node) {
		node.pre.next = node.next;
		node.next.pre = node.pre;
	}

	/**
	 * 添加到头节点
	 *
	 * @param node 节点
	 */
	private void addNode(@NotNull CacheNode<K, V> node) {
		node.pre = first;
		node.next = first.next;
		first.next.pre = node;
		first.next = node;
	}

	private void moveToHead(CacheNode<K, V> node) {
		// 删除原有此节点
		removeNode(node);
		// 在头部添加此节点
		addNode(node);
	}

	/**
	 * 双向列表
	 *
	 * @param <K> key类型
	 * @param <V> value类型
	 */
	@Getter
	protected static class CacheNode<K, V> {
		private K key;
		private V value;
		private CacheNode<K, V> pre;
		private CacheNode<K, V> next;
	}
}
