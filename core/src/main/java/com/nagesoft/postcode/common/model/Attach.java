package com.nagesoft.postcode.common.model;

import com.nagesoft.postcode.common.model.ref.RefType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * DB 저장용 첨부파일 Class
 *
 * Attach
 *
 * @author Juhyeon Kim
 *
 */
public class Attach {
	private Long id;

	/**
	 * 첨부파일 소유자 타입 (예: 'article' > 상품)
	 */
	private RefType refType;

	/**
	 * 첨부파일 소유자 키값 (예: 213 > 게시글중에 키값이 213번 )
	 */
	private Long refKey;

	/**
	 * 첨부파일 마다 구분을 할수 있다. (예: 'cart' > 쇼핑카트에 담겼을때용 이미지)
	 */
	private String typeKey; // optional

	/**
	 * 원본 파일 이름
	 */
	private String displayName;

	/**
	 * 저장된 파일 이름
	 */
	private String savedName;

	/**
	 * 저장된 파일 디렉토리
	 */
	private String savedDir;

	/**
	 * 파일 타입 (doc, xls, jpg, png)
	 */
	private String fileType;

	/**
	 * 파일 사이즈 byte
	 */
	private Long fileSize;

	/**
	 * 정렬 순서
	 */
	private Integer sortOrder;

	/**
	 * 등록 날짜
	 */
	private Date regDate;


	/**
	 * link
	 */
	private String link;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Attach)) {
			return false;
		}

		final Attach attach = (Attach) o;

		return !(id != null ? !id.equals(attach.getId())
				: attach.getId() != null);
	}

	public static void main(String[] args) {

		System.out.println(Enum.valueOf(RefType.class, "article"));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RefType getRefType() {
		return refType;
	}

	public void setRefType(RefType refType) {
		this.refType = refType;
	}

	public Long getRefKey() {
		return refKey;
	}

	public void setRefKey(Long refKey) {
		this.refKey = refKey;
	}

	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSavedName() {
		return savedName;
	}

	public void setSavedName(String savedName) {
		this.savedName = savedName;
	}

	public String getSavedDir() {
		return savedDir;
	}

	public void setSavedDir(String savedDir) {
		this.savedDir = savedDir;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}


}
