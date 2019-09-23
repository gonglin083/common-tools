package com.panhai.log.aspect;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.panhai.log.handler.LogMsgHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 注解类型日志打印
 * @author kevin.gong
 * @date 2019年9月18日 上午10:47:04
 */
@Slf4j
@Aspect
public class AspectOnAnnotation {

	private static final String LOG_UUID = "log_id";
	
	@Autowired
	private LogMsgHandler logMsgHandler;
	
	/**
	 * 存放一个uuid，用于对照一组请求和相应信息
	 */
	private static final ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<Map<String, String>>(){
		@Override
		protected Map<String, String> initialValue() {
			Map<String, String> map = new HashMap<String, String>() {
				private static final long serialVersionUID = 1L;
				{
					this.put(LOG_UUID, UUID.randomUUID().toString());
				}
			};
			return map;
		};
	};
	
	@Pointcut("@annotation(com.panhai.log.annotation.MethodLog)")  
    public void methodLog(){}  
		
	@Before("methodLog()")  
    public void doBefore(JoinPoint joinPoint) throws Throwable {  
		String uuid = threadLocal.get().get(LOG_UUID);
		String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
		String args = (joinPoint.getArgs().length==BigDecimal.ZERO.intValue()?"":new ObjectMapper().writeValueAsString(joinPoint.getArgs()));
		logMsgHandler.requestHandler(uuid, methodName, args);
    }  
	
	@AfterReturning(returning = "ret", pointcut = "methodLog()")  
    public void doAfterReturning(Object ret) throws Throwable { 
		String uuid = threadLocal.get().get(LOG_UUID);
		String res = new ObjectMapper().writeValueAsString(ret);
		logMsgHandler.responseHandler(uuid, res);
    }
	
	@AfterThrowing(throwing = "t", pointcut = "methodLog()")
	public void doAfterThrowing(Throwable t) throws Throwable { 
		String uuid = threadLocal.get().get(LOG_UUID);
		log.error(String.format("-------------------------业务异常:%s-------------------------", uuid), t);
		throw t;
    }
	
}