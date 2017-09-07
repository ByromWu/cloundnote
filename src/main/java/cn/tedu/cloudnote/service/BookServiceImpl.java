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
		//���û�ID��ѯ��Ϣ
		List<Book> list=dao.findByUserId(userId);
		//�������ؽ��
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("��ѯ���");
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
		result.setMessage("�����ʼǱ��ɹ�");
		result.setData(book);
		return result;
	}

}
