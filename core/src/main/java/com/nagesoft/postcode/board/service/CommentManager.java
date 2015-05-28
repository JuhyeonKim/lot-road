package com.nagesoft.postcode.board.service;

import com.nagesoft.postcode.board.dao.CommentDao;
import com.nagesoft.postcode.board.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 *
 * CommentManager
 *
 * comment Service Layer
 *
 * @author Juhyeon Kim
 *
 */
@Service("commentManager")
public class CommentManager  {


	/**
	 * {@link CommentDao}
	 */
	@Autowired
	private CommentDao commentDao;

	/**
	 * comment 입력 및 수정
	 *
	 * @param comment 입력 할 comment 정보
	 */
	@Transactional
	public void save(Comment comment) {

		if(comment.getSequence() != null){
			commentDao.update(comment);

		}else{
			commentDao.insert(comment);
			commentDao.updateCommentSequence(comment);

		}

	}

	/**
	 * comment 정보 조회
	 *
	 * @param param 검색 조건 sequence
	 * @return comment 정보
	 */
	public Comment getComment(Map<String, Object> param) {


		Comment result = commentDao.getComment(param);

		return result;
	}

	/**
	 * comment 검색 목록
	 *
	 * @param param 검색 조건 - sequence(comment sequence), articleSequence(comment 대상 게시글 sequence)
	 * @return 검색된 comment 목록
	 */
	public List<Comment> listComment(Map<String, Object> param) {

		List<Comment> result = commentDao.listComment(param);

		return result;
	}

	/**
	 * comment 삭제
	 *
	 * @param sequence comment 일련번호
	 */
	public void delete(Long sequence) {

		commentDao.delete(sequence);

	}

	/**
	 * 답변 comment 등록
	 *
	 * @param comment 저장할 comment 정보
	 */
	public void saveReply(Comment comment) {
		commentDao.updateReplyPrepareOrder(comment);
		commentDao.insert(comment);

	}



}
