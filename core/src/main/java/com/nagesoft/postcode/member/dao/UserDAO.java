/*
 * @(#)UserDAO.java  2014. 10. 23
 *
 * Copyright(c) 2014 NageSoft Corp.
 */

package com.nagesoft.postcode.member.dao;

import com.nagesoft.core.mybatis.NGPageable;
import com.nagesoft.postcode.member.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * UserDAO
 *
 * @author Juhyeon Kim
 * @version 1.0.0 2014-01-20
 * @since 1.0.0
 */
@Repository(value = "userDAO")
public interface UserDAO {
	/**
	 * 회원 목록 조회
	 *
	 * @param parameter 조회 조건
	 * @return 회원 목록
	 */
	@NGPageable(countMapperID = "getUserCount")
	public List<User> getUserList(Map<String, Object> parameter);

	/**
	 * 엑셀 출력을 위한 사용자 목록 조회
	 *
	 * @param parameter 조회 조건
	 * @return 사용자 목록
	 */
	public List<Map<String, Object>> getUserListForExcel(Map<String, Object> parameter);

	/**
	 * 사용자 목록 개수 조회
	 *
	 * @param parameter 조회 조건
	 * @return 사용자 목록 개수
	 */
	public Integer getUserCount(Map<String, Object> parameter);

	/**
	 * 사용자 심플 조회
	 *
	 * @param sequence 일련번호
	 * @return 사용자정보
	 */
	public User getUser(@Param(value = "sequence") Long sequence);

    public User getUserWithPassword(@Param(value = "id") String id, @Param(value = "password") String password);

	/**
	 * 사용자 권한 조회
	 * @param id 사용자 아이디
	 * @return 검색된 사용자 및 권한 정보
	 */
	public User getUserWithRole(@Param(value = "id") String id);

	/**
	 * 사용자 정보 삭제
	 *
	 * @param sequence 일련번호
	 * @return 삭제 행 개수
	 */
	public Integer delete(@Param(value = "sequence") Long sequence);

	/**
	 * 사용자 정보 수정
	 *
	 * @param user 사용자
	 * @return 변경된 행의 개수
	 */
	public Integer update(User user);

	/**
	 * 사용자 정보 입력
	 *
	 * @param user 사용자
	 */
	public void insert(User user);

	/**
	 * 아이디 중복체크
	 *
	 * @param userID 사용자 계정명
	 * @return 중복 계정 갯수
	 */
	public Integer getDuplicationIdCount(String userID);

	/**
	 * 패스워드 변경
	 *
	 * @param user 사용자 DTO
	 * @return 화면
	 */
	/**
	 * 
	 * @param user
	
	 */
	public Integer updatePassword(User user);

	/**
	 * 아이디 찾기
	 *
	 * @param param 검색조건
	 * @return 검색결과
	 */
	public User getAccount(Map<String, Object> param);

	/**
	 * 사용자 방문회수 업데이트
	 * @param user 업데이트 할 사용자 정보
	 * @return 업데이트 영향 받은 행 개수
	 */
	public Integer updateVisitCount(User user);
}
