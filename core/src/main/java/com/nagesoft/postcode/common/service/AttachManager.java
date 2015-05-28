package com.nagesoft.postcode.common.service;

import com.nagesoft.postcode.common.dao.AttachDao;
import com.nagesoft.postcode.common.model.Attach;
import com.nagesoft.postcode.common.model.ref.RefType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * AttachManager
 *
 * 첨부파일 Service Layer
 *
 * @author Juhyeon Kim
 *
 */
@Service
public class AttachManager {

	/**
	 * {@link AttachDao}
	 */
	@Autowired
	private AttachDao attachDao;

	/**
	 * 등록 기준으로 정렬된 파일 목록
	 *
	 * @param refType 업무 구분
	 * @param reference 게시물 일련번호
	 *
	 * @return 파일 목록
	 *
	 * @see com.nagesoft.postcode.common.model.ref.RefType
	 */
	public List<Attach> listAttach(RefType refType, Long reference){
		return attachDao.listAttach(refType, reference);
	}

	/**
	 * 정렬 기준을 파일 목록
	 *
	 * @param reference 게시물 일련번호
	 *
	 * @return 파일 목록
	 */
	public List<Attach> listAttachSort(RefType refType, Long reference){
		return attachDao.listAttachSort(refType, reference);
	}

	/**
	 * 파일 정보 조회
	 *
	 * @param id 파일 일련번호
	 *
	 * @return 파일 정보
	 */
	public Attach getAttach(Long id){
		return attachDao.getAttach(id);
	}

	/**
	 * 첨부 파일 저장
	 *
	 * @param attach 파일 정보
	 */
	@Transactional
	public void addAttach(Attach attach){
		attachDao.addAttach(attach);
	}

	/**
	 * 첨부 파일 삭제
	 *
	 * @param attachId 파일 일련번호
	 */
	public int deleteAttach(Long attachId){
		return attachDao.deleteAttach(attachId);
	}


	/**
	 * 특정 파일 수정용 삭제
	 *
	 * @param param refId, refType, 지우지 말아야 할 파일의 ID
	 */
	public int deleteAttachForEdit(Map<String,Object> param){
		return attachDao.deleteAttachForEdit(param);
	}

	/**
	 * 특정 게시물에 해당하는 첨부파일 삭제
	 *
	 * @param refType 업무 구분
	 * @param reference 게시물 일련번호
	 *
	 * @return 삭제된 행의 개수
	 */
	public int deleteAttachReference(RefType refType, Long reference){
		return attachDao.deleteAttachReference(refType, reference);
	}

	/**
	 * 첨부파일 정보 업데이트
	 *
	 * @param attach
	 * @return
	 */
	public int updateAttach(Attach attach){
		return attachDao.updateAttach(attach);
	}
}
