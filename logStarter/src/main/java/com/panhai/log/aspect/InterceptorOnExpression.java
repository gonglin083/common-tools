package com.panhai.log.aspect;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.panhai.log.handler.LogMsgHandler;

import lombok.extern.slf4j.Slf4j;


/**
 * 表达式类型日志打印
 * @author kevin.gong
 * @date 2019年9月18日 下午2:09:03
 */
@Slf4j
public class InterceptorOnExpression implements MethodInterceptor {
	
	@Autowired
	private LogMsgHandler logMsgHandler;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String uuid = UUID.randomUUID().toString();
		String methodName = invocation.getMethod().getName();
		String args = (invocation.getArguments().length==BigDecimal.ZERO.intValue()?"":new ObjectMapper().writeValueAsString(invocation.getArguments()));
		logMsgHandler.requestHandler(uuid, methodName, args);
		
		Object ret = null;
		try {
			ret = invocation.proceed();
		} catch (Exception e) {
			log.error(String.format("-------------------------业务异常:%s-------------------------", uuid), e);
			throw e;
		}
		
		String res = (Optional.ofNullable(ret).isPresent()?new ObjectMapper().writeValueAsString(ret):null);
		logMsgHandler.responseHandler(uuid, res);
		return ret;
	}

	
}