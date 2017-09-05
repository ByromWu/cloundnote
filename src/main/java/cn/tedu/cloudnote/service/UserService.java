package cn.tedu.cloudnote.service;

import cn.tedu.cloudnote.entity.User;
import cn.tedu.cloudnote.util.NoteResult;

public interface UserService {
	public NoteResult checkLogin(String name,String password);

	public NoteResult addUser(String name,String nick,String password);
}
