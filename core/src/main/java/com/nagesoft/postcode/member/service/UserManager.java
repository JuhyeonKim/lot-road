/*
 * @(#)UserManager.java  2014. 10. 23
 *
 * Copyright(c) 2014 NageSoft Corp.
 */

package com.nagesoft.postcode.member.service;

import com.nagesoft.core.exception.NGDuplicateDataException;
import com.nagesoft.core.model.NGAbstractUser;
import com.nagesoft.core.service.NGUserDetailService;
import com.nagesoft.postcode.member.dao.RoleDAO;
import com.nagesoft.postcode.member.dao.UserDAO;
import com.nagesoft.postcode.member.model.Role;
import com.nagesoft.postcode.member.model.RoleType;
import com.nagesoft.postcode.member.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserManager
 *
 * @author Juhyeon Kim
 * @version 1.0.0 2014-10-23
 * @since 1.0.0
 */
@Service(value = "userManager")
public class UserManager extends NGUserDetailService {
	
	/**
	 * {@link com.nagesoft.postcode.member.dao.UserDAO}
	 */
	@Autowired
	private UserDAO dao;

    /**
     * {@link com.nagesoft.postcode.member.dao.RoleDAO}
     */
    @Autowired
    private RoleDAO roleDAO;

	/**
	 * 사용자 목록 조회
	 *
	 * @param parameter 조회 조건
	 * @return 사용자 목록
	 */
	public List<User> getUserList(Map<String, Object> parameter) {
		return dao.getUserList(parameter);
	}

	/**
	 * 사용자 조회
	 *
	 * @param sequence 일련번호
	 * @return 사용자
	 */
	public User getUser(Long sequence) {
		return dao.getUser(sequence);
	}

	/**
	 * 사용자 저장/수정 처리
	 *
	 * @param user 사용자
	 */
	@Transactional
	public void saveUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException,NGDuplicateDataException{

        user.setPassword(encryptPassword(user.getPassword()));

		if (user.getSequence() == null) {
            // 신규 가입
            if (dao.getDuplicationIdCount(user.getId()) > 0) {
                // 아이디 중복 확인.
                throw new NGDuplicateDataException("입력하신 아이디는 이미 사용중입니다.");
            }

			dao.insert(user);

            Role role = new Role();
            role.setRoleType(RoleType.ROLE_GENERAL);
            role.setUser(user);
            role.setRegUser(user);
            roleDAO.insert(role);

		} else {
			if (StringUtils.isEmpty(user.getPassword())) {
				user.setPassword(null);
			}
			dao.update(user);
		}
	}

	/**
	 * 일반 회원 정보 목록 조회( 엘셀 다운로드 용)
	 *
	 * @param parameter 검색 조건 (flag N-일반회원|P-파트너회원)
	 * @return 검색된 사용자 목록
	 */
	public List<Map<String, Object>> getUserListForExcel(Map<String, Object> parameter) {
		return dao.getUserListForExcel(parameter);
	}

	@Override
	public NGAbstractUser getUser(String arg0) {

		return dao.getUserWithRole(arg0);
	}

	@Override
	public NGAbstractUser getUser(String account, String password)
			throws UsernameNotFoundException {

        return dao.getUserWithPassword(account,password);
	}

	/**
	 * 아이디 중복 체크
	 *
	 * @param userID 사용자 계정명
	 * @return 중복계정의 갯수
	 */
	public Integer getDuplicationIdCount(String userID){
		Integer cnt = dao.getDuplicationIdCount(userID);
		return cnt;
	}


	/**
	 * 비밀번호 수정(비밀번호 암호화)
	 *
	 * @param user 사용자 DTO
	 * @throws java.io.IOException 예외사항
	 * @throws java.security.NoSuchAlgorithmException 예외사항
	 */
	public void updatePassword(User user) throws IOException, NoSuchAlgorithmException {
		user.setPassword(encryptPassword(user.getPassword()));
		dao.updatePassword(user);
	}

	/**
	 * 아이디 조회
	 * 
	 * @param param 검색조건
	 * @return 아이디
	 */
	public User getAccount(Map<String, Object> param){
		return dao.getAccount(param);
	}

}
