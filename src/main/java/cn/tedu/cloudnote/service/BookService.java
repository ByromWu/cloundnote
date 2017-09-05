package cn.tedu.cloudnote.service;

import cn.tedu.cloudnote.util.NoteResult;

public interface BookService {
	public NoteResult addBook(String name,String userId);
	public NoteResult loadUserBooks(String userId);
}
