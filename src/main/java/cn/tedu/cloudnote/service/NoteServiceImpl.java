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
		//���ʼǱ�ID��ѯ�ʼ���Ϣ
		List<Map> list=dao.findByBookId(bookId);
		//System.out.println(list);
		//�������ؽ��
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("��ѯ���");
		result.setData(list);
		return result;
	}

	public NoteResult loadNote(String noteId) {
		//���ʼ�ID��ѯ�ʼ���Ϣ
		Note note=dao.findById(noteId);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("��ѯ���");
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
			result.setMessage("����ʼǳɹ�");
		}else{
			result.setStatus(1);
			result.setMessage("����ʼ�ʧ��");
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
		//�������ؽ��
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("�����ʼǳɹ�");
		result.setData(noteId);
		return result;
	}

	public NoteResult deleteNote(String noteId) {
		int rows=dao.updateStatus(noteId);
		//�������ؽ��
		NoteResult result=new NoteResult();
		if(rows>=1){
			result.setStatus(0);
			result.setMessage("ɾ���ʼǳɹ�");
		}else{
			result.setStatus(1);
			result.setMessage("ɾ���ʼ�ʧ��");
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
			result.setMessage("ת�Ʊʼǳɹ�");
		}else{
			result.setStatus(1);
			result.setMessage("ת�Ʊʼ�ʧ��");
		}
		return result;
	}

	public NoteResult shareNote(String noteId) {
		//��ȡ�ʼ���Ϣ
		Note note=dao.findById(noteId);
		NoteResult result=new NoteResult();
		if("2".equals(note.getCn_note_type_id())){
			result.setStatus(1);
			result.setMessage("�ñʼ��ѷ����");
			return result;
		}
		//���±ʼǵ�cn_note_type_idΪ����״̬
		dao.updateTypeId(noteId);
		//��ӵ������
		Share share=new Share();
		share.setCn_share_body(note.getCn_note_body());
		share.setCn_share_title(note.getCn_note_title());
		share.setCn_share_id(NoteUtil.createId());
		sharedao.save(share);
		
		result.setStatus(0);
		result.setMessage("����ʼǳɹ�");
		return result;
	}

	public NoteResult searchShareNote(String keyword,int page) {
		//System.out.println(keyword);
		String title="%";
		if(keyword!=null&&!"".equals(keyword)){
			title="%"+keyword+"%";
		}
		//System.out.println(title);
		//����ץȡ���
		if(page<1){
			page=1;
		}
		int begin=(page-1)*5;
		//��װ��Map����
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("begin", begin);
		params.put("keyword", title);
		System.out.println(params);
		List<Share> list=sharedao.findLikeTitle(params);
		//��װNoteResult
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("�������");
		result.setData(list);
		System.out.println(result);
		return result;
	}

	public NoteResult loadShareNote(String shareId) {
		Share share=sharedao.findById(shareId);
		NoteResult result=new NoteResult();
		result.setStatus(0);
		result.setMessage("���رʼǳɹ�");
		result.setData(share);
		return result;
	}

}
