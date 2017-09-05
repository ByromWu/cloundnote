package cn.tedu.cloudnote.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class NoteUtil {
	public static String createId(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
	/**
	 * 将传入的src加密处理
	 * @throws NoSuchAlgorithmException 
	 */
	public static String md5(String src) throws NoSuchAlgorithmException{
		//将字符串信息采用MD5处理
		MessageDigest md=MessageDigest.getInstance("MD5");
		byte[] output=md.digest(src.getBytes());
		//将Md5处理结果采用base64转成字符串
		String s=Base64.encodeBase64String(output);
		return s;
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException{
		System.out.println(md5("123456"));
//		System.out.println(md5("12345616515"));
		//System.out.println(createId());
	}
}
