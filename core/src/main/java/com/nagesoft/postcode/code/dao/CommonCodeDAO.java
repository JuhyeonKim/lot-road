package com.nagesoft.postcode.code.dao;

import java.util.List;
import java.util.Map;

import com.nagesoft.postcode.member.model.User;
import org.apache.ibatis.annotations.Param;

import com.nagesoft.postcode.code.model.CommonCode;
import org.springframework.stereotype.Repository;

/**
 * CommonCodeDAO
 *
 * 공통 코드 관리 DAO
 *
 * @author Juhyeon Kim
 *
 */
@Repository
public interface CommonCodeDAO {

	/**
	 * 코드 등록 처리
	 *
	 * @param parameter 코드
	 */
	public void insertCommonCode(CommonCode parameter);

	/**
	 * 공통코드 목록 조회
	 *
	 * @param parameter 조회 조건
	 *
	 * @return 코드 목록
	 */
	public List<CommonCode> getCodeList(Map<String, Object> parameter);

	/**
	 * 코드 상세 조회
	 *
	 * @param parameter 조회 조건
	 *
	 * @return 코드
	 */
	public CommonCode getCode(Map<String, Object> parameter);

	/**
	 * 코드 수정 처리
	 *
	 * @param parameter 코드
	 *
	 * @return 변경된 행의 개수
	 */
	public Integer updateCommonCode(CommonCode parameter);

	public Integer getCommonCodeCount(@Param(value = "code") String code);

	public Integer updateNextSortOrderAll(@Param(value = "currentSortOrder") Integer sortOrder,
	                                      @Param(value = "code") String code,
	                                      @Param(value = "modUser") User modUser);

	public Integer updateNextSortOrder(@Param(value = "from") Integer from,
	                                   @Param(value = "to") Integer to,
	                                   @Param(value = "code") String code,
	                                   @Param(value = "modUser") User modUser);

	public Integer updateBeforeSortOrder(@Param(value = "from") Integer from,
	                                     @Param(value = "to") Integer to,
	                                     @Param(value = "code") String code,
	                                     @Param(value = "modUser") User modUser);

	public Integer updateUseYNAllChild(@Param(value = "useYN") String useYN,
	                                   @Param(value = "code") String code);

}
