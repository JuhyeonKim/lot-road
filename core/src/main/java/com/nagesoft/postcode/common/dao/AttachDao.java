package com.nagesoft.postcode.common.dao;

import com.nagesoft.postcode.common.model.Attach;
import com.nagesoft.postcode.common.model.ref.RefType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 첨부파일 처리 Dao
 *
 * @author Juhyeon Kim
 *
 */
@Repository(value = "attachDao")
public interface AttachDao {

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
	public List<Attach> listAttach(@Param("refType") RefType refType, @Param("reference") Long reference);

	/**
	 * 정렬 기준을 파일 목록
	 *
	 * @param reference 게시물 일련번호
	 *
	 * @return 파일 목록
	 */
	public List<Attach> listAttachSort(@Param("refType") RefType refType, @Param("reference") Long reference);

	/**
	 * 파일 정보 조회
	 *
	 * @param id 파일 일련번호
	 *
	 * @return 파일 정보
	 */
	public Attach getAttach(Long id);

	/**
	 * 첨부 파일 저장
	 *
	 * @param attach 파일 정보
	 */
	public void addAttach(Attach attach);

	/**
	 * 첨부 파일 삭제
	 *
	 * @param attachId 파일 일련번호
	 */
	public int deleteAttach(Long attachId);

	/**
	 * 특정 파일 수정용 삭제
	 *
	 * @param param refId, refType, 지우지 말아야 할 파일의 ID
	 */
	public int deleteAttachForEdit(Map<String, Object> param);

	/**
	 * 특정 게시물에 해당하는 첨부파일 삭제
	 *
	 * @param refType 업무 구분
	 * @param reference 게시물 일련번호
	 *
	 * @return 삭제된 행의 개수
	 */
	public int deleteAttachReference(@Param("refType") RefType refType, @Param("reference") Long reference);

	/**
	 * 첨부파일 정보 업데이트
	 *
	 * @param attach
	 * @return
	 */
	int updateAttach(Attach attach);
}
