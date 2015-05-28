package com.nagesoft.postcode.board.dao;

import com.nagesoft.core.mybatis.NGPageable;
import com.nagesoft.postcode.board.model.Board;

import java.util.List;
import java.util.Map;

/**
 *
 * BoardDao
 *
 * 게시판 Dao
 *
 * @author Juhyeon Kim
 *
 */
public interface BoardDao {

	/**
	 * 게시판 정보 입력
	 *
	 * @param board 입력 할 게시판 정보
	 */
	public void insert(Board board);

	/**
	 * 게시판 정보 수정
	 *
	 * @param board 수정 할 검색 조건
	 */
	public void update(Board board);

	/**
	 * 단일 데이터 조회
	 *
	 * @param param 검색 조건 - sequence (게시판 sequence), boardURL (게시판 boardURL)
	 * @return 게시판 정보
	 */
	public Board getBoard(Map<String, Object> param);

	/**
	 * 게시판 목록 조회
	 *
	 * @param param 검색 조건 - sequence, useYn(y|n), boardURL (게시판 boardURL)
	 * @return 검색된 게시판 목록
	 */
	@NGPageable(countMapperID="listBoardCnt")
	public List<Board> listBoard(Map<String, Object> param);

	/**
	 * 게시판 카운트 조회
	 *
	 * @param param 검색 조건 - sequence, useYn(y|n), boardURL (게시판 boardURL)
	 * @return 검색결과 개수
	 */
	public Integer listBoardCnt(Map<String, Object> param);

	/**
	 * 등록된 게시판 상위메뉴 가져오기
	 *
	 * @return 게시판 상위메뉴 정보 목록
	 */
	public List<Board> getUpperMenuGroup();

	/**
	 * 게시판 삭제
	 *
	 * @param sequence 게시판 일련번호
	 */
	public void delete(Long sequence);
}
