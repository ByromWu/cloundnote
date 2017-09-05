package cn.tedu.cloudnote.service;



import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.cloudnote.dao.UserDAO;
import cn.tedu.cloudnote.entity.User;
import cn.tedu.cloudnote.util.NoteException;
import cn.tedu.cloudnote.util.NoteResult;
import cn.tedu.cloudnote.util.NoteUtil;

//扫描
@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource(name="userDAO")//注入
	private UserDAO dao;
	
	
	public NoteResult checkLogin(String name, String password){
		
		User user=dao.findByName(name);
		System.out.println(user);
		NoteResult result=new NoteResult();
		
		String md5_pwd;
		try {
			
			md5_pwd = NoteUtil.md5(password);
			
//			System.out.println(md5_pwd);
//			System.out.println(user.getCn_user_password());
//			
//			System.out.println(user.getCn_user_password().equals(md5_pwd));
		if(user==null){
			result.setStatus(1);
			result.setMessage("用户名不存在");	
		}else if(!(user.getCn_user_password().equals(md5_pwd))){
//			System.out.println(user.getCn_user_password());
//			System.out.println(1);
			result.setStatus(2);
			result.setMessage("密码错误");	
		}else{
			
			result.setStatus(0);
			result.setMessage("登陆成功");	
			user.setCn_user_password("");//屏蔽密码
			result.setData(user);
		}
		
		return result;
		} catch (Exception e) {
			throw new NoteException("密码加密异常",e);
			
		}
	}
	
	@Transactional//(默认RuntimeException	)
	public NoteResult addUser(String name,String nick,String password){
		NoteResult result=new NoteResult();
		
		String md5_pwd;
		try {
			//检测是否重名
			User has_user=dao.findByName(name);
			if(has_user!=null){
				result.setStatus(1);
				result.setMessage("用户名已被占用");
				return result;
			}
			
			//执行用户注册
			User user=new User();
			user.setCn_user_name(name);//设置用户名
			System.out.println(name);
			user.setCn_user_desc(nick);//设置昵称
			System.out.println(nick);
			md5_pwd = NoteUtil.md5(password);
			user.setCn_user_password(md5_pwd);//设置加密密码
			System.out.println(md5_pwd);
			String userId=NoteUtil.createId();
			user.setCn_user_id(userId);
			dao.save(user);
			
			//创建返回结果
			
			result.setStatus(0);
			result.setMessage("注册成功");
			
			//模拟异常
			String s=null;
			s.length();//跑一个空指针异常
			return result;
			

		} catch (Exception e) {
		
				throw new NoteException("用户注册异常",e);

		}
		
	}
}
