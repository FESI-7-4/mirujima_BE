package com.todo.mirujima_be.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {

//    @Value("${spring.data.redis.host}")
//    private String host;
//    @Value("${spring.data.redis.port}")
//    private Integer port;
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        // TLS를 활성화한 LettuceClientConfiguration 생성
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//                .useSsl()
//                .disablePeerVerification() // AWS에서는 기본적으로 CA 인증서 검증이 필요 없음
//                .build();
//
//        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);
//
//        return new LettuceConnectionFactory(redisConfig, clientConfig);
//    }
//
//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration() {
//        ObjectMapper objectMapper = new ObjectMapper()
//                .registerModule(new JavaTimeModule())
//                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//                .activateDefaultTyping(
//                        BasicPolymorphicTypeValidator.builder()
//                                .allowIfBaseType("java.util.List")
//                                .allowIfBaseType(Object.class)
//                                .build(),
//                        ObjectMapper.DefaultTyping.EVERYTHING
//                );
//
//        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
//
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofHours(1)) // 캐시 TTL 설정
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
//    }
//
//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, RedisCacheConfiguration redisCacheConfiguration) {
//        return RedisCacheManager
//                .builder(redisConnectionFactory)
//                .cacheDefaults(redisCacheConfiguration).build();
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        var template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(redisConnectionFactory);
//
//        ObjectMapper objectMapper = new ObjectMapper()
//                .registerModule(new JavaTimeModule())
//                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//                .activateDefaultTyping(
//                        BasicPolymorphicTypeValidator.builder()
//                                .allowIfBaseType("java.util.List")
//                                .allowIfBaseType(Object.class)
//                                .build(),
//                        ObjectMapper.DefaultTyping.EVERYTHING
//                );
//
//        // GenericJackson2JsonRedisSerializer Serializable 각각 클래스가 구현해야 하므로 Jackson 사용
//        var serializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
//        template.setDefaultSerializer(serializer);
//        return template;
//    }
}
