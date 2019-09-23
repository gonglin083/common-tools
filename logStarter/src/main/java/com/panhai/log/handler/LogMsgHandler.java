package com.panhai.log.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 日志信息处理
 * @author kevin.gong
 * @date 2019年9月18日 下午4:13:56
 */
public class LogMsgHandler {
	
	@Autowired
	private ApplicationContext appContext;

	public void requestHandler(String uuid, String methodName, String args) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); 
        if(attributes == null) return;
        HttpServletRequest request = attributes.getRequest();
        
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(String.format("-------------------------请求参数:%s-------------------------", uuid) + "\n"); 
        requestBuilder.append("URL : " + request.getRequestURL().toString() + "\n");  
        requestBuilder.append("HTTP_METHOD : " + request.getMethod() + "\n");  
        requestBuilder.append("IP : " + request.getRemoteAddr() + "\n");  
        requestBuilder.append("CLASS_METHOD : " + methodName + "\n");  
        requestBuilder.append("ARGS : " + args + "\n");  
       
        appContext.publishEvent(new LogRepositoryEvent(this, 
				requestBuilder.toString(), LogRepositoryEvent.EventType.REQUEST));
	}
	
	public void responseHandler(String uuid, String res) {
		StringBuilder responseBuilder = new StringBuilder();
		responseBuilder.append(String.format("-------------------------响应参数:%s-------------------------", uuid) + "\n"); 
		responseBuilder.append(String.format("方法的返回值 : %s", res));
		
		appContext.publishEvent(new LogRepositoryEvent(this, 
				responseBuilder.toString(), LogRepositoryEvent.EventType.RESPONSE));
	}
	
}