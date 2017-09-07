package cn.tedu.cloudnote.aop;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * ���� ���쳣��Ϣд���ļ�
 * @author Administrator
 *
 */
@Component
@Aspect
public class ExceptionBean {
	//ָ���쳣֪ͨ���������ʽ
	@AfterThrowing(throwing="e",pointcut="within(cn.tedu.cloudnote.controller..*)")
	public void execute(Exception e){
		//System.out.println("���쳣��Ϣд���ļ�"+e);
		try {
			
			//��e������Ϣд��note_error.log�ļ�
			FileWriter fw=new FileWriter("D:\\note_error.log",true);
			PrintWriter pw=new PrintWriter(fw,true);
			//��ȡ�쳣����ʱ��
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(new Date());
			pw.println("*********************");
			pw.println("****����ʱ��:"+time);
			pw.println("****�쳣����:"+e);
			pw.println("****�쳣����***********");
			e.printStackTrace(pw);
			pw.close();
			fw.close();
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
	}
}
