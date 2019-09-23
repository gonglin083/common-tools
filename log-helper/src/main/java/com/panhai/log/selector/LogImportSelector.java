package com.panhai.log.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 日志加载组件选择器
 * @author kevin.gong
 * @date 2019年9月18日 上午10:19:41
 */
public class LogImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		String aspectAnnoClass = "com.panhai.log.aspect.AspectOnAnnotation";
		String aspectExprClass = "com.panhai.log.aspect.InterceptorOnExpression";
		String handlerClass = "com.panhai.log.handler.LogRepositoryListener";
		String configClass = "com.panhai.log.config.ExpressionInterceptorConfig";
		String msgHandlerClass = "com.panhai.log.handler.LogMsgHandler";
		return new String[]{msgHandlerClass, aspectAnnoClass, aspectExprClass, handlerClass, configClass};
	}
	
}