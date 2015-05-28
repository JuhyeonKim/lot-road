package com.nagesoft.postcode.common.model.ref;

/**
 * RefType
 * <p/>
 * 파일 첨부 등록시 업무 구분자
 * <p/>
 *
 * @author Juhyeon Kim
 *
 */
public enum RefType {
	/**
	 * 테스트
	 *
	 * @deprecated 테스트 용도임
	 */
	@Deprecated
	TEST("test"),

	/**
	 * 공통 게시판 썸네일
	 */
	ARTICLE_THUMBNAIL("articleThumbnail"),

	/**
	 * 공통 게시판 썸네일
	 */
	LINK_FILE("linkFile"),

	/**
	 * 공통 게시판 첨부파일
	 */
	ARTICLE_FILE("articleFile"),

	/**
	 * 팝업 이미지
	 */
	POPUP_IMAGE("popupImage");



	final String code;

	RefType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static RefType getRefType(String code) {
		RefType[] roleTypes = RefType.values();
		RefType result = null;

		for (RefType type : roleTypes) {
			if (type.getCode().equals(code)) {
				result = type;
			}
		}

		return result;
	}
}
