package de.sturmm;

import de.sturmm.wicket.WicketApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.Collections;

/**
 * Created by sturmm on 12.09.16.
 */
@SpringBootApplication
@EnableRedisHttpSession(redisNamespace = "test", redisFlushMode = RedisFlushMode.IMMEDIATE)
public class Application {

    @Bean
    public WebApplication wicketApplication() {
        return new WicketApplication();
    }

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        return new JdkSerializationRedisSerializer();
    }

    @Bean
    public FilterRegistrationBean wicketRegistrar() {
        final FilterRegistrationBean rb = new FilterRegistrationBean();

        final WicketFilter wicketFilter = new WicketFilter(wicketApplication());
        wicketFilter.setFilterPath("/");

        rb.setFilter(wicketFilter);
        rb.setUrlPatterns(Collections.singletonList("/*"));
        rb.setOrder(Ordered.LOWEST_PRECEDENCE);

        return rb;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
