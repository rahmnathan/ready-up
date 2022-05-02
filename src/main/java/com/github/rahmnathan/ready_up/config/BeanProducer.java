package com.github.rahmnathan.ready_up.config;

import com.github.rahmnathan.ready_up.web.filter.CorrelationIdFilter;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.boot.actuate.metrics.data.MetricsRepositoryMethodInvocationListener;
import org.springframework.boot.actuate.metrics.data.RepositoryTagsProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;

@Configuration
@AllArgsConstructor
@EnableAspectJAutoProxy
public class BeanProducer {
    private final ServiceConfig serviceConfig;

    @Bean
    public FilterRegistrationBean<CorrelationIdFilter> loggingFilter(){
        FilterRegistrationBean<CorrelationIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorrelationIdFilter());
        registrationBean.addUrlPatterns("/ready-up/*");
        return registrationBean;
    }

    // Attempt to fix metrics bug https://github.com/spring-projects/spring-boot/issues/26630
    @Bean
    public static MetricsRepositoryMethodInvocationListener metricsRepositoryMethodInvocationListener(
            MetricsProperties metricsProperties, @Lazy MeterRegistry registry, RepositoryTagsProvider tagsProvider) {
        MetricsProperties.Data.Repository properties = metricsProperties.getData().getRepository();
        return new MetricsRepositoryMethodInvocationListener(registry, tagsProvider, properties.getMetricName(),
                properties.getAutotime());
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}
