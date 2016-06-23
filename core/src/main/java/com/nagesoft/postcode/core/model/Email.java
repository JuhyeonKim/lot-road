/*
 * @(#)Email.java  2013. 12. 12
 *
 * Copyright(c) 2013 NageSoft Corp.
 */

package com.nagesoft.postcode.core.model;

import org.apache.commons.lang.StringUtils;

import com.nagesoft.module.common.NGStringUtil;

/**
 * Email
 * <p/>
 * 이메일 DTO
 *
 * @author JoonHo Son
 * @version 1.0 2013-12-12
 * @since 1.0
 */
public class Email {
	/**
	 * 이메일 아이디
	 */
	private String mailID;

	/**
	 * 이메일 서버 주소
	 */
	private String mailServer;

	/**
	 * Constructor
	 * <p/>
	 * {@code spring-mvc@nagesoft.com} 형태의 이메일 주소를 인자로 하여 변수를 초기화 한다.<br />
	 * 휴효한 이메일이 아닐 경우 변수를 초기화 하지 않는다.
	 *
	 * @param emailAddress 이메일 주소
	 */
	public Email(String emailAddress) {
		// TODO: 이메일 유효성 검사 실패시 예외를 발생 시킬 것인지?
		if (StringUtils.isNotEmpty(emailAddress) && NGStringUtil.isValidEmail(emailAddress, true)) {
			mailID = StringUtils.substringBefore(emailAddress, "@");
			mailServer = StringUtils.substringAfter(emailAddress, "@");
		}
	}

	/**
	 * 이메일 주소를 반환한다.
	 *
	 * @return 이메일 주소
	 */
	private String getEmailAddress() {
		StringBuilder builder = new StringBuilder();

		if (StringUtils.isNotEmpty(mailID) && StringUtils.isNotEmpty(mailServer)) {

			builder.append(mailID).append("@").append(mailServer);

			return builder.toString();
		}

		return builder.toString();
	}

	public String getMailID() {
		return mailID;
	}

	public void setMailID(String mailID) {
		this.mailID = mailID;
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}
}
