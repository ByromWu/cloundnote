package test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.cloudnote.dao.UserDAO;
import cn.tedu.cloudnote.entity.User;
import cn.tedu.cloudnote.service.UserService;
import cn.tedu.cloudnote.util.NoteResult;



public class TestCase {
	private ApplicationContext ac;
	@Before
	public void init(){
		String[] conf={"conf/spring-mvc.xml","conf/spring-mybatis.xml","conf/spring-aop.xml"};
		System.out.println(1);
		ac=new ClassPathXmlApplicationContext(conf);
		
	}
	
	@Test
	public void test1() throws SQLException{
		
		DataSource ds=ac.getBean("dbcp", DataSource.class);
		Connection conn=ds.getConnection();
		System.out.println(conn);
		conn.close();
		SqlSessionFactory ssf=ac.getBean("ssf", SqlSessionFactory.class);
		SqlSession session=ssf.openSession();
		System.out.println(session);
		UserDAO dao=ac.getBean("userDAO", UserDAO.class);
		User user=dao.findByName("demo1");
		if(user==null){
			System.out.println("用户名不存在");
		}else{
			System.out.println(user.getCn_user_password());
		}
	}
	
	@Test
	public void test2(){
		UserService service=ac.getBean("userService", UserService.class);
		System.out.println(service.getClass().getName());
		NoteResult result=service.checkLogin("demo", "1234");
		System.out.println(result);
	}
	
	@Test
	public void addUser(){
		UserService service=ac.getBean("userService", UserService.class);
		NoteResult result=service.addUser("demo0","demo","123456");
		System.out.println(result);
	}
}
