package com.panhai.log.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.panhai.log.annotation.EnableLog;
import com.panhai.log.aspect.InterceptorOnExpression;

/**
 * 表达式配置类
 * <p>绑定注解上的表达式值和代理类关系
 * @author kevin.gong
 * @date 2019年9月18日 下午2:21:48
 */
public class ExpressionInterceptorConfig {

	@Autowired
	private ApplicationContext context;
	@Autowired
	private InterceptorOnExpression interceptor;
	
	@Bean
	@ConditionalOnProperty(name="log.helper.expression.open", havingValue="true")
	public DefaultPointcutAdvisor defaultPointcutAdvisor() {
		List<String> expressions = new ArrayList<>();
		Map<String, Object> beans = context.getBeansWithAnnotation(EnableLog.class);
		beans.entrySet().forEach(bean -> {
			String value = AopUtils.getTargetClass(bean.getValue()).getAnnotation(EnableLog.class).value();
			String expression = AopUtils.getTargetClass(bean.getValue()).getAnnotation(EnableLog.class).expression();
			expressions.add(EnableLog.DEFAULT_EXPRESSION.equals(value)?expression:value);
		});
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expressions.get(0));
        
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
	}
	
}