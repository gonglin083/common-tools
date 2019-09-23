package com.panhai.log.handler;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

/**
 * 日志事件
 * @author kevin.gong
 */
@Getter
public class LogRepositoryEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 日志消息
	 */
	private String msg;
	/**
	 * 日志事件类型
	 */
	private EventType eventType;
	
	public LogRepositoryEvent(Object source, String msg, EventType eventType) {
		super(source);
		
		this.msg = msg;
		this.eventType = eventType;
	}
	
	public enum EventType {
		REQUEST, RESPONSE;
	}
	
}