package cn.tedu.cloudnote.service;

import cn.tedu.cloudnote.util.NoteResult;

public interface NoteService {
	public NoteResult loadShareNote(String shareId);
	public NoteResult searchShareNote(String keyword,int page);
	public NoteResult shareNote(String noteId);
	public NoteResult moveNote(String noteId,String bookId);
	public NoteResult deleteNote(String noteId);
	public NoteResult addNote(String userId,String noteTitle,String bookId);
	public NoteResult updateNote(String noteId,String title,String body);
	public NoteResult loadNote(String noteId);
	public NoteResult loadBookNotes(String bookId);
}
