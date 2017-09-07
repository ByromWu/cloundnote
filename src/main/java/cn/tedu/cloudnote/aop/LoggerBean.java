package cn.tedu.cloudnote.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * ���� ��װ��׮�����߼�
 * @author Administrator
 *	����spring-aop.jar aopalliance.jar aspectjweaver.jar
 */
@Component
@Aspect//����
public class LoggerBean {
	//����֪ͨ����/�������ʽ
	@Before("within(cn.tedu.cloudnote.controller..*)")
	public void LoggerController(){
		System.out.println("����controller�������");
	}
	
}
