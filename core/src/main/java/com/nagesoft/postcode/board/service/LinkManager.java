package com.nagesoft.postcode.board.service;

import com.nagesoft.postcode.board.dao.LinkDao;
import com.nagesoft.postcode.board.model.Link;
import com.nagesoft.postcode.common.dao.AttachDao;
import com.nagesoft.postcode.common.model.Attach;
import com.nagesoft.postcode.common.model.ref.RefType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 링크 Service Layer
 *
 * @author cloud
 *
 */
@Service
public class LinkManager {


	/**
	 * {@see ArticleDao}
	 */
	@Autowired
	private LinkDao linkDao;

	/**
	 * {@link AttachDao}
	 */
	@Autowired
	private AttachDao attachDao;

	/**
	 * 
	 * insertLink
	 *
	 * 링크 등록처리 ,첨부파일 처리
	 *
	 * @param link
	 */
	@Transactional
	public void insertLink(Link link) {
		
		if(link.getAttachList() != null){
			for(Attach attach : link.getAttachList()){
				attach.setRefKey(link.getArticleSeq());
				attach.setRefType(RefType.LINK_FILE);
				attachDao.addAttach(attach);
				link.setAttachSeq(attach.getId());
			}
		}
		linkDao.insertLink(link);
	}

	/**
	 * 링크 수정
	 * @param link 링크 DTO
	 */
	@Transactional
	public void updateLink(Link link) {
		
		if(link.getAttachList() != null){
			for(Attach attach : link.getAttachList()){
				//setAttachForUpdate
				attach.setRefKey(link.getArticleSeq());
				attach.setRefType(RefType.LINK_FILE);
				attachDao.addAttach(attach);
				link.setAttachSeq(attach.getId());
			}
		}
		if(link.getArticleSeq() != null){
			linkDao.updateLink(link);
		}
	}
	
	/**
	 * 게시글 목록 조회
	 *
	 * @param param 검색 조건 - title, contents, level, noticeYn(y|n), boardSequence(게시판 sequence), seq(게시글 sequence)
	 * @return 검색된 게시글 목록
	 */
	public List<Link> listLink(Map<String, Object> param) {

		List<Link> result = linkDao.listLink(param);

		return result;

	}

	/**
	 * 링크 삭제
	 *
	 * @param sequence 게시글 일련번호
	 */
	public void deleteLink(Long sequence) {

		linkDao.deleteLink(sequence);

	}

}
