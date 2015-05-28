package com.nagesoft.postcode.board.dao;

import com.nagesoft.postcode.board.model.Link;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 링크 Dao
 *
 * @author cloud
 *
 */
@Repository(value = "linkDao")
public interface LinkDao {


	/**
	 * 게시글 조회
	 *
	 * @param param 조건 - seq(게시글 sequence)
	 * @return 조회된 게시글과 링크 정보
	 */
	public List<Link> listLink(Map<String, Object> param);


	/**
	 * 링크 저장
	 *
	 * @param link 입력 할 게시글 정보
	 */
	public void insertLink(Link link);
	
	/**
	 * 링크 저장
	 *
	 * @param link 입력 할 게시글 정보
	 */
	public void updateLink(Link link);
	
	/**
	 * 링크 삭제
	 *
	 * @param sequence 게시글(article) 아이디
	 */
	public void deleteLink(Long sequence);
	
	
}
