package com.nagesoft.postcode.board.service;

import com.nagesoft.postcode.board.dao.BoardDao;
import com.nagesoft.postcode.board.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 *
 * BoardManager
 *
 * 게시판 Service Layer
 *
 * @author Juhyeon Kim
 *
 */
@Service(value="boardManager")
public class BoardManager {

	/**
	 * {@link BoardDao}
	 */
	@Autowired
	private BoardDao boardDao;

	/**
	 * 게시판 입력 또는 수정
	 *
	 * @param board 입력할 게시판 데이터
	 */
	@Transactional
	public void save(Board board) {

		if(board.getSequence() != null){
			 boardDao.update(board);
		}else{
			boardDao.insert(board);
			boardDao.update(board);
		}

	}

	/**
	 * 게시판 정보 단일건 조회
	 *
	 * @param param 검색 조건 - sequence (게시판 sequence), boardURL (게시판 boardURL)
	 * @return 게시판 정보
	 */
	public Board getBoard(Map<String,Object> param){

		return boardDao.getBoard(param);
	}

	/**
	 * 게시판 목록 조회
	 *
	 * @param param 검색 조건 - sequence, useYn(y|n), boardURL (게시판 boardURL)
	 * @return 검색된 게시판 목록
	 */
	public List<Board> listBoard(Map<String, Object> param) {

		List result = boardDao.listBoard(param);

		return result;
	}

	/**
	 *
	 * getUpperMenuGroup
	 *
	 * 등록된 게시판 상위메뉴 가져오기
	 *
	 * @return 게시판 상위메뉴 정보 목록
	 */
	public List<Board> getUpperMenuGroup(){

		return boardDao.getUpperMenuGroup();
	}

	/**
	 * 게시판 삭제
	 *
	 * @param sequence 게시판 일련번호
	 */
	public void delete(Long sequence)
	{
		boardDao.delete(sequence);

	}

}
