package cn.tedu.cloudnote.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tedu.cloudnote.dao.NoteDAO;
import cn.tedu.cloudnote.dao.ShareDAO;
import cn.tedu.cloudnote.entity.Note;
import cn.tedu.cloudnote.entity.Share;
import cn.tedu.cloudnote.util.NoteResult;
import cn.tedu.cloudnote.util.NoteUtil;
@Service("noteService")
public class NoteServiceImpl implements NoteService {
	@Resource(name="noteDAO")
	private NoteDAO dao;
	@Resource(name="shareDAO")
	private ShareDAO sharedao;
	
	public NoteResult loadBookNotes(String bookId) {
		//按笔记本ID查询笔记信息
		List<Map> list=dao.findByBookId(bookId);
		//System.out.println(list);
		//创建返回结果
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("查询完毕");
		result.setData(list);
		return result;
	}

	public NoteResult loadNote(String noteId) {
		//按笔记ID查询笔记信息
		Note note=dao.findById(noteId);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("查询完毕");
		result.setData(note);
		return result;
	}

	public NoteResult updateNote(String noteId, String title, String body) {
		Note note=new Note();
		note.setCn_note_title(title);
		note.setCn_note_body(body);
		note.setCn_note_id(noteId);
		long time=System.currentTimeMillis();
		note.setCn_note_last_modify_time(time);
		//System.out.println(note);
		int rows=dao.updateNote(note);
		NoteResult result=new NoteResult();
		//System.out.println(rows);
		if(rows==1){
			result.setStatus(0);
			result.setMessage("保存笔记成功");
		}else{
			result.setStatus(1);
			result.setMessage("保存笔记失败");
		}
		return result;
	}

	public NoteResult addNote(String userId, String noteTitle, String bookId) {
		Note note=new Note();
		note.setCn_user_id(userId);
		note.setCn_note_title(noteTitle);
		note.setCn_notebook_id(bookId);
		
		String noteId=NoteUtil.createId();
		note.setCn_note_id(noteId);
		long time=System.currentTimeMillis();
		note.setCn_note_create_time(time);
		note.setCn_note_last_modify_time(time);
		
		dao.save(note);
		//创建返回结果
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("创建笔记成功");
		result.setData(noteId);
		return result;
	}

	public NoteResult deleteNote(String noteId) {
		int rows=dao.updateStatus(noteId);
		//创建返回结果
		NoteResult result=new NoteResult();
		if(rows>=1){
			result.setStatus(0);
			result.setMessage("删除笔记成功");
		}else{
			result.setStatus(1);
			result.setMessage("删除笔记失败");
		}
		return result;
	}

	public NoteResult moveNote(String noteId, String bookId) {
		Note note=new Note();
		note.setCn_note_id(noteId);
		note.setCn_notebook_id(bookId);
		int rows=dao.updateBookId(note);
		NoteResult result=new NoteResult();
		if(rows>=1){
			result.setStatus(0);
			result.setMessage("转移笔记成功");
		}else{
			result.setStatus(1);
			result.setMessage("转移笔记失败");
		}
		return result;
	}

	public NoteResult shareNote(String noteId) {
		//获取笔记信息
		Note note=dao.findById(noteId);
		NoteResult result=new NoteResult();
		if("2".equals(note.getCn_note_type_id())){
			result.setStatus(1);
			result.setMessage("该笔记已分享过");
			return result;
		}
		//更新笔记的cn_note_type_id为分享状态
		dao.updateTypeId(noteId);
		//添加到分项表
		Share share=new Share();
		share.setCn_share_body(note.getCn_note_body());
		share.setCn_share_title(note.getCn_note_title());
		share.setCn_share_id(NoteUtil.createId());
		sharedao.save(share);
		
		result.setStatus(0);
		result.setMessage("分享笔记成功");
		return result;
	}

	public NoteResult searchShareNote(String keyword,int page) {
		//System.out.println(keyword);
		String title="%";
		if(keyword!=null&&!"".equals(keyword)){
			title="%"+keyword+"%";
		}
		//System.out.println(title);
		//计算抓取起点
		if(page<1){
			page=1;
		}
		int begin=(page-1)*5;
		//封装成Map参数
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("begin", begin);
		params.put("keyword", title);
		System.out.println(params);
		List<Share> list=sharedao.findLikeTitle(params);
		//封装NoteResult
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("搜索完毕");
		result.setData(list);
		System.out.println(result);
		return result;
	}

	public NoteResult loadShareNote(String shareId) {
		Share share=sharedao.findById(shareId);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("加载笔记成功");
		result.setData(share);
		return result;
	}

}
