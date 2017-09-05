package cn.tedu.cloudnote.service;


import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tedu.cloudnote.dao.BookDAO;
import cn.tedu.cloudnote.entity.Book;
import cn.tedu.cloudnote.util.NoteResult;
import cn.tedu.cloudnote.util.NoteUtil;
@Service("bookService")
public class BookServiceImpl implements BookService {
	@Resource(name="bookDAO")
	private BookDAO dao;
	public NoteResult loadUserBooks(String userId) {
		//按用户ID查询信息
		List<Book> list=dao.findByUserId(userId);
		//创建返回结果
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("查询完毕");
		result.setData(list);
		//System.out.println(result);
		return result;
	}
	public NoteResult addBook(String name, String userId) {
		Book book=new Book();
		book.setCn_notebook_name(name);
		book.setCn_user_id(userId);
		String bookId=NoteUtil.createId();
		book.setCn_notebook_id(bookId);
		Timestamp time=new Timestamp(System.currentTimeMillis());
		book.setCn_notebook_createtime(time);
		dao.save(book);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("创建笔记本成功");
		result.setData(book);
		return result;
	}

}
