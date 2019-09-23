package com.panhai.log.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志消息监听
 * @author kevin.gong
 */
@Slf4j
public class LogRepositoryListener {

	@Async
	@EventListener
	public void saveLogToRepository(LogRepositoryEvent event) {
		log.info(event.getMsg());
		//TODO 持久化操作
		
	}
}
