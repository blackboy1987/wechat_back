package com.igomall.config;

import com.igomall.listener.CacheEventListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class EhCacheConfig {

    @Bean
    public EhCacheCacheManager cacheManager(@Qualifier("ehCacheManager") EhCacheManagerFactoryBean ehCacheManager){
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(ehCacheManager.getObject());

        return ehCacheCacheManager;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManager(){
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

    @Bean
    public CacheEventListener cacheEventListener1 (){
        return new CacheEventListener();
    }

    @Bean
    public EhCacheFactoryBean articleCacheCacheManager(){
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(ehCacheManager().getObject());
        ehCacheFactoryBean.setCacheName("articleHits");
        Set<net.sf.ehcache.event.CacheEventListener> cacheEventListeners = new HashSet<>();
        cacheEventListeners.add(cacheEventListener1());
        ehCacheFactoryBean.setCacheEventListeners(cacheEventListeners);
        return ehCacheFactoryBean;
    }

    @Bean
    public EhCacheFactoryBean courseCacheCacheManager(){
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(ehCacheManager().getObject());
        ehCacheFactoryBean.setCacheName("courseHits");
        Set<net.sf.ehcache.event.CacheEventListener> cacheEventListeners = new HashSet<>();
        cacheEventListeners.add(cacheEventListener1());
        ehCacheFactoryBean.setCacheEventListeners(cacheEventListeners);
        return ehCacheFactoryBean;
    }
}
