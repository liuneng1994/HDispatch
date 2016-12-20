package hdispatch.core.dispatch.cache;

import hdispatch.core.dispatch.utils.Constants;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;
import java.util.Set;

/**
 * Created by liuneng on 2016/12/15.
 */
public class String2ObjectCache implements Cache {
    private String name;
    private RedisTemplate redisTemplate;
    public String2ObjectCache(String name, RedisConnectionFactory connectionFactory) {
        this.name = name;
        this.redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        RedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.afterPropertiesSet();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return redisTemplate;
    }

    @Override
    public ValueWrapper get(Object key) {
        return toWrapper(redisTemplate.boundHashOps(Constants.CACHE_NAMESPACE+name).get(key));
    }

    @Override
    public <T> T get(Object key, Class<T> aClass) {
        return (T) redisTemplate.boundHashOps(Constants.CACHE_NAMESPACE+name).get(key);
    }

    @Override
    public void put(Object key, Object value) {
        redisTemplate.boundHashOps(Constants.CACHE_NAMESPACE+name).put(key,value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return toWrapper(redisTemplate.boundHashOps(Constants.CACHE_NAMESPACE+name).putIfAbsent(key,value));
    }

    @Override
    public void evict(Object key) {
        redisTemplate.boundHashOps(Constants.CACHE_NAMESPACE+name).delete(key);
    }

    @Override
    public void clear() {
        redisTemplate.boundHashOps(Constants.CACHE_NAMESPACE+name).delete(redisTemplate.boundHashOps(Constants.CACHE_NAMESPACE+name).keys());
    }

    private ValueWrapper toWrapper(Object o) {
        return new SimpleValueWrapper(o);
    }


}
