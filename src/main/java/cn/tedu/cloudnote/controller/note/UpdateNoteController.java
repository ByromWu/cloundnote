package cn.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.cloudnote.service.NoteService;
import cn.tedu.cloudnote.util.NoteResult;

@Controller
public class UpdateNoteController {
	@Resource(name="noteService")
	private NoteService service;
	
	@RequestMapping("/note/update.do")
	@ResponseBody
	public NoteResult execute(String noteId,String title,String body){
		return service.updateNote(noteId, title, body);
	}
}
