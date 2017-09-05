package cn.tedu.cloudnote.controller.book;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.cloudnote.service.BookService;
import cn.tedu.cloudnote.util.NoteResult;

@Controller
public class AddBookController {
	@Resource(name="bookService")
	private BookService service;
	
	@RequestMapping("/book/add.do")
	@ResponseBody
	public NoteResult execute(String name,String userId){
		return service.addBook(name, userId);
	}
}
