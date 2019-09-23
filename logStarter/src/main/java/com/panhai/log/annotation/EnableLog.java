package com.panhai.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.scheduling.annotation.EnableAsync;

import com.panhai.log.selector.LogImportSelector;

/**
 * 日志开启注解
 * @author kevin.gong
 * @date 2019年9月18日 上午10:14:38
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(LogImportSelector.class)
@EnableAsync
public @interface EnableLog {
	
	final String DEFAULT_EXPRESSION = "execution(public * com.panhai..controller.*Controller.*(..))";

	@AliasFor("expression")
	String value() default DEFAULT_EXPRESSION;
	
	@AliasFor("value")
	String expression() default DEFAULT_EXPRESSION;
	
}