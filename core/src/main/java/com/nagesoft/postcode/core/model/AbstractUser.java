/*
 * @(#)User.java  2013. 12. 12
 *
 * Copyright(c) 2013 NageSoft Corp.
 */

package com.nagesoft.postcode.core.model;

/**
 * AbstractUser
 * <p/>
 * 사용자 기본 정보 DTO.
 *
 * @author JoonHo Son
 * @version 1.0 2013-12-12
 * @since 1.0
 */
@Deprecated
public abstract class AbstractUser {
	/**
	 * 사용자 일련번호
	 */
	private Long seq;

	/**
	 * 사용자명
	 */
	private String name;

	/**
	 * 비밀번호
	 */
	private String password;

	/**
	 * 이메일
	 */
	private Email email;

	/**
	 * 사용자 아이디
	 */
	private String id;

	/**
	 * 작성자, 작성일시
	 */
	private DateInfo createdData;

	/**
	 * 수정자, 수정일시
	 */
	private DateInfo updatedData;

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DateInfo getCreatedData() {
		return createdData;
	}

	public void setCreatedData(DateInfo createdData) {
		this.createdData = createdData;
	}

	public DateInfo getUpdatedData() {
		return updatedData;
	}

	public void setUpdatedData(DateInfo updatedData) {
		this.updatedData = updatedData;
	}
}
