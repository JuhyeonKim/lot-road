package com.nagesoft.postcode.board.model;

/**
 *
 * BoardType
 *
 * 게시판 타입 정의
 *
 * @author Juhyeon Kim
 *
 */
public enum BoardType {


	/**
	 * 일반
	 */
	GENERAL("general","일반"),

	/**
	 * FAQ
	 */
	FAQ("faq","FAQ 타입"),


	/**
	 * QNA
	 */
	QNA("qna","QnA 타입"),


	/**
	 * URL 링크
	 */
	URL("url","URL 링크 타입");

	final String code;
	final String codeName;

	BoardType(String code,String codeName) {
		this.code = code;
		this.codeName = codeName;
	}

	public String getCode() {
		return code;
	}

	public String getCodeName(){
		return codeName;
	}

	public static BoardType getBoardType(String code) {
		BoardType[] boardTypes = BoardType.values();
		BoardType result = null;


		for (BoardType type : boardTypes) {
			if (type.getCode().equals(code)) {
				result = type;
				break;
			}
		}

		return result;
	}

	public static String[][] getAllNames(){

		BoardType[] objs = BoardType.values();
		int length = objs.length;
		String[][] names = new String[length][2];

		for (int i = 0; i < length; i++) {
			names[i][0] = objs[i].getCode();
			names[i][1] = objs[i].getCodeName();
		}
		return names;

	}
}
