package cn.tedu.cloudnote.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.cloudnote.service.UserService;
import cn.tedu.cloudnote.util.NoteResult;

@Controller
public class UserAddController {
	@Resource(name="userService")
	private UserService service;
	@RequestMapping("/user/add.do")
	@ResponseBody
	public NoteResult execute(String name,String nick,String password){
		return service.addUser(name, nick, password);
		
	}
	
}
