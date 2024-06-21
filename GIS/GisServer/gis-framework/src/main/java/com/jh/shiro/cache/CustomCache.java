package com.jh.shiro.cache;

import com.jh.config.JwtConfig;
import com.jh.constant.Constants;
import com.jh.domain.SysUser;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 重写Shiro的Cache保存读取
 */
public class CustomCache<K, V> implements Cache<K, V> {

    private RedisTemplate<String, Object> redisTemplate;

    public CustomCache(RedisTemplate<String, Object>  redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 缓存的key名称获取为shiro:cache:account
     *
     * @param key
     * @return java.lang.String
     */
    private String getKey(Object key) {
        SysUser user = (SysUser) ((SimplePrincipalCollection)key).asList().get(0);
        //return Constants.PREFIX_SHIRO_CACHE + user.getLoginName() + "_" + user.getSalt();
        return Constants.PREFIX_SHIRO_CACHE + user.getLoginName();
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        return redisTemplate.opsForValue().get(getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 设置Redis的Shiro缓存
        try {
            redisTemplate.opsForValue().set(getKey(key), value, JwtConfig.getShiroCacheExpireTime(), TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        redisTemplate.delete(getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        // TODO Auto-generated method stub

    }

    /**
     * 获取所有的key
     */
    @Override
    public Set<K> keys() {
        return null;
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection<V> values() {
        // TODO Auto-generated method stub
        return null;
    }
}
