package cn.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.cloudnote.entity.Note;

public interface NoteDAO {
	public int updateTypeId(String noteId);
	public int updateBookId(Note Note);
	public int updateStatus(String noteId);
	public void save(Note note);
	public int updateNote(Note note);
	public List<Map> findByBookId(String bookId);
	public Note findById(String noteId);
}
