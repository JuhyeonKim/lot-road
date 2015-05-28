/*
 * @(#)DateInfo.java  2013. 12. 12
 *
 * Copyright(c) 2013 NageSoft Corp.
 */

package com.nagesoft.postcode.core.model;

import java.util.Date;

/**
 * DateInfo
 *
 * @author JoonHo Son
 * @version 1.0 2013-12-12
 * @since 1.0
 */
public class DateInfo {
	/**
	 * 등록자
	 */
	private AbstractUser createdBy;

	/**
	 * 등록일
	 */
	private Date createdAt;

	/**
	 * 수정자
	 */
	private AbstractUser updatedBy;

	/**
	 * 수정일
	 */
	private Date updatedAt;

	public AbstractUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(AbstractUser createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public AbstractUser getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(AbstractUser updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
