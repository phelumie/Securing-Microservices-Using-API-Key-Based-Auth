//package com.ajisegiri.apigateway.repo;
//
//import com.ajisegiri.apigateway.model.hashes.Auth;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.NoRepositoryBean;
//
//@NoRepositoryBean
//public interface RedisCrudRepository<T, ID> extends CrudRepository<Auth, String> {
//
//    @Autowired
//    @Qualifier("redisTemplate")
//    RedisTemplate<String, Auth> redisTemplate = null;
//
//    @Override
//    default <S extends T> S save(S entity) {
//        redisTemplate.opsForValue().set(entity.getId().toString(), entity);
//        return entity;
//    }
//
//    @Override
//    default <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
//        entities.forEach(entity -> redisTemplate.opsForValue().set(entity.getId().toString(), entity));
//        return entities;
//    }
//
//    @Override
//    default Optional<T> findById(ID id) {
//        return Optional.ofNullable(redisTemplate.opsForValue().get(id.toString()));
//    }
//
//    @Override
//    default boolean existsById(ID id) {
//        return redisTemplate.hasKey(id.toString());
//    }
//
//    @Override
//    default Iterable<T> findAll() {
//        return redisTemplate.opsForValue().multiGet(redisTemplate.keys("*"));
//    }
//
//    @Override
//    default Iterable<T> findAllById(Iterable<ID> ids) {
//        return redisTemplate.opsForValue().multiGet(redisTemplate.keys("*"));
//    }
//
//    @Override
//    default long count() {
//        return redisTemplate.keys("*").size();
//    }
//
//    @Override
//    default void deleteById(ID id) {
//        redisTemplate.delete(id.toString());
//    }
//
//    @Override
//    default void delete(T entity) {
//        redisTemplate.delete(entity.getId().toString());
//    }
//
//    @Override
//    default void deleteAll(Iterable<? extends T> entities) {
//        entities.forEach(entity -> redisTemplate.delete(entity.getId().toString()));
//    }
//
//    @Override
//    default void deleteAll() {
//        redisTemplate.delete(redisTemplate.keys("*"));
//    }
//}
