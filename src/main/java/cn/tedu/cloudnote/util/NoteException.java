package cn.tedu.cloudnote.util;

/**
 * �Զ����쳣
 * @author Administrator
 *
 */
public class NoteException extends RuntimeException {
	public NoteException(String msg,Throwable t){
		super(msg,t);
	}
}
