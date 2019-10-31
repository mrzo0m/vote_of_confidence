package io.voteofconf.frontend.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

public class FilterChainPostProcessor implements BeanPostProcessor {

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (beanName.contains("springSecurityFilterChain")) {
            if (!(bean instanceof FilterChainProxy)) return bean;

            DefaultSecurityFilterChain fc = (DefaultSecurityFilterChain) ((FilterChainProxy)bean).getFilterChains().get(0);
            List<Filter> filters = fc.getFilters();

            // Modify the filter list as you choose here.
//            List<Filter> newFilters = ...

            List<Filter> newFilters = new ArrayList<>(filters);
            org.springframework.security.web.access.ExceptionTranslationFilter filterToRemove = (org.springframework.security.web.access.ExceptionTranslationFilter)
                    filters.stream()
                            .filter(filter -> filter instanceof org.springframework.security.web.access.ExceptionTranslationFilter)
                            .findFirst()
                            .orElse(null);

            int position = newFilters.indexOf(filterToRemove);
            newFilters.set(position,
                    new ExceptionTranslationFilter(filterToRemove.getAuthenticationEntryPoint() != null ?
                            filterToRemove.getAuthenticationEntryPoint() : new OAuth2AuthenticationEntryPoint(),
                            new HttpSessionRequestCache()));

            return new FilterChainProxy(new DefaultSecurityFilterChain(fc.getRequestMatcher(), newFilters));
        }

        return bean;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}