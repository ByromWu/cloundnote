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

//ɨ��
@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource(name="userDAO")//ע��
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
			result.setMessage("�û���������");	
		}else if(!(user.getCn_user_password().equals(md5_pwd))){
//			System.out.println(user.getCn_user_password());
//			System.out.println(1);
			result.setStatus(2);
			result.setMessage("�������");	
		}else{
			
			result.setStatus(0);
			result.setMessage("��½�ɹ�");	
			user.setCn_user_password("");//��������
			result.setData(user);
		}
		
		return result;
		} catch (Exception e) {
			throw new NoteException("��������쳣",e);
			
		}
	}
	
	@Transactional//(Ĭ��RuntimeException	)
	public NoteResult addUser(String name,String nick,String password){
		NoteResult result=new NoteResult();
		
		String md5_pwd;
		try {
			//����Ƿ�����
			User has_user=dao.findByName(name);
			if(has_user!=null){
				result.setStatus(1);
				result.setMessage("�û����ѱ�ռ��");
				return result;
			}
			
			//ִ���û�ע��
			User user=new User();
			user.setCn_user_name(name);//�����û���
			System.out.println(name);
			user.setCn_user_desc(nick);//�����ǳ�
			System.out.println(nick);
			md5_pwd = NoteUtil.md5(password);
			user.setCn_user_password(md5_pwd);//���ü�������
			System.out.println(md5_pwd);
			String userId=NoteUtil.createId();
			user.setCn_user_id(userId);
			dao.save(user);
			
			//�������ؽ��
			
			result.setStatus(0);
			result.setMessage("ע��ɹ�");
			
			//ģ���쳣
			String s=null;
			s.length();//��һ����ָ���쳣
			return result;
			

		} catch (Exception e) {
		
				throw new NoteException("�û�ע���쳣",e);

		}
		
	}
}
