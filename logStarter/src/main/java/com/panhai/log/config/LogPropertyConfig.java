package com.panhai.log.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

/**
 * 日志默认配置加载
 * <p>在META-INF/spring.factories中配置加载
 * @author kevin.gong
 */
public class LogPropertyConfig implements EnvironmentPostProcessor {

	private static final String PROPERTY_SOURCE_NAME = "defaultProperties";
	private static final String LOG_OPEN = "log.helper.expression.open";
	private static final String LOG_LEVEL = "log.helper.level";
	private static final String LOG_PATH = "log.helper.path";
	private static final String LOG_FILENAME = "log.helper.filename";
	
	private static final String LOG_LEVEL_INFO = "info";
	private static final String LOG_PATH_DEFAULT = "/usr/local/log/log-helper/";
	private static final String LOG_FILENAME_DEFAULT = "project_log";
	
	
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> propertyMap = new HashMap<>();
        //根据表达式打印日志:默认-开启
        propertyMap.put(LOG_OPEN, Boolean.TRUE);
        //日志级别:默认-info
        propertyMap.put(LOG_LEVEL, LOG_LEVEL_INFO);
        //日志路径:默认-/usr/local/log/log-helper/
        propertyMap.put(LOG_PATH, LOG_PATH_DEFAULT);
        //日志名称:默认-project_log
        propertyMap.put(LOG_FILENAME, LOG_FILENAME_DEFAULT);
        
        MapPropertySource target = (MapPropertySource) environment.getPropertySources().get(PROPERTY_SOURCE_NAME);
        if (!Optional.ofNullable(target).isPresent()) {
            target = new MapPropertySource(PROPERTY_SOURCE_NAME, propertyMap);
        }
        environment.getPropertySources().addLast(target);
    }
	
}