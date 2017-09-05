package cn.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.cloudnote.entity.Share;

public interface ShareDAO {
	public Share findById(String shareId);
	public List<Share> findLikeTitle(Map<String,Object> params);
	public void save(Share share);
}
