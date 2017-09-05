package cn.tedu.cloudnote.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 切面 封装打桩操作逻辑
 * @author Administrator
 *	导入spring-aop.jar aopalliance.jar aspectjweaver.jar
 */
@Component
@Aspect//切面
public class LoggerBean {
	//给定通知类型/切入点表达式
	@Before("within(cn.tedu.cloudnote.controller..*)")
	public void LoggerController(){
		System.out.println("进入controller组件处理");
	}
	
}
