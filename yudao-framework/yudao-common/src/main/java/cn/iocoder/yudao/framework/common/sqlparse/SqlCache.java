package cn.iocoder.yudao.framework.common.sqlparse;

import java.util.concurrent.Callable;

/**
 * sql缓存接口，由外部实现
 */
public interface SqlCache<K, V> {

    /**
     * 从缓存中获取值，如果没有的话，通过loader获取缓存的值，并将该值缓存起来
     * @param key
     * @param loader
     * @return
     */
    V get(K key, Callable<? extends V> loader) throws Exception;

}
