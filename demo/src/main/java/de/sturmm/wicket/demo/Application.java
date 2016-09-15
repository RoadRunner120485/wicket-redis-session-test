package de.sturmm.wicket.demo;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.session.data.redis.RedisFlushMode;
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
