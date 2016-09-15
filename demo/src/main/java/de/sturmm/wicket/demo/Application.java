package de.sturmm.wicket.demo;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.Collections;

/**
 * Created by sturmm on 12.09.16.
 */
@SpringBootApplication
@EnableRedisHttpSession(redisNamespace = "demo")
public class Application {

    @Bean
    public WebApplication wicketApplication() {
        return new WicketApplication();
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new RedisSerializer<Object>() {

            private final FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

            @Override
            public byte[] serialize(Object o) throws SerializationException {
                return conf.asByteArray(o);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                return conf.asObject(bytes);
            }
        };
    }

    @Bean
    public FilterRegistrationBean wicketRegistrar() {
        final FilterRegistrationBean rb = new FilterRegistrationBean();

        final WicketFilter wicketFilter = new WicketFilter(wicketApplication());
        wicketFilter.setFilterPath("/");

        rb.setFilter(wicketFilter);
        rb.setUrlPatterns(Collections.singletonList("/*"));

        return rb;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
