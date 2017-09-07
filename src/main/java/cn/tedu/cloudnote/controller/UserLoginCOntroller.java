package cn.tedu.cloudnote.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.cloudnote.service.UserService;
import cn.tedu.cloudnote.util.NoteResult;

@Controller//ɨ��
@RequestMapping("/user")
public class UserLoginCOntroller {
	@Resource(name="userService")
	private UserService service;
	
	@RequestMapping("/login.do")
	@ResponseBody//JSON���
	public NoteResult execute(String name,String password){
		return service.checkLogin(name, password);
		
	}
}
