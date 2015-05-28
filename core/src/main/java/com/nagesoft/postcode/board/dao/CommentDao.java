package com.nagesoft.postcode.board.dao;

import com.nagesoft.core.mybatis.NGPageable;
import com.nagesoft.postcode.board.model.Comment;

import java.util.List;
import java.util.Map;

/**
 * Comment Dao
 *
 * @author Juhyeon Kim
 *
 */
public interface CommentDao {


	/**
	 * 입력
	 *
	 * @param comment 코멘트 정보
	 */
	public void insert(Comment comment);

	/**
	 * 수정
	 *
	 * @param comment 코멘트 정보
	 */
	public void update(Comment comment);

	/**
	 * 코멘트 조회(단일건)
	 *
	 * @param param 검색 조건 - (sequence)
	 * @return 검색된 코멘트
	 */
	public Comment getComment(Map<String, Object> param);

	/**
	 * 코멘트 조회
	 *
	 * @param param 검색 조건 - sequence(comment sequence), articleSequence(comment 대상 게시글 sequence)
	 * @return 검색된 코멘트 목록
	 */
	@NGPageable(countMapperID="listCommentCnt")
	public List<Comment> listComment(Map<String, Object> param);

	/**
	 * 코멘트 개수 조회
	 *
	 * @param param 검색 조건 - sequence(comment sequence), articleSequence(comment 대상 게시글 sequence)
	 * @return 검색된 코멘트 개수
	 */
	public Integer listCommentCnt(Map<String, Object> param);

	/**
	 * 코멘트 삭제
	 *
	 * @param sequence 코멘트 일련번호
	 */
	public void delete(Long sequence);

	/**
	 * 계층형 부모글 처리
	 *
	 * @param comment 부모 코멘트
	 */
	public void updateCommentSequence(Comment comment);

	/**
	 * 계층형 답글 정렬순서 사전 처리
	 *
	 * @param comment {@code com.nagesoft.board.commonBoard.model.Comment }
	 */
	public void updateReplyPrepareOrder(Comment comment);

}
