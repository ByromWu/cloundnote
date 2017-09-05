package cn.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.cloudnote.service.NoteService;
import cn.tedu.cloudnote.util.NoteResult;

@Controller
public class SearchShareController {
	@Resource(name="noteService")
	private NoteService service;
	
	@RequestMapping("/note/search_share.do")
	@ResponseBody
	public NoteResult execute(String keyword,int page){
		System.out.println(keyword);
		System.out.println(page);
		return service.searchShareNote(keyword,page);
	}
}
