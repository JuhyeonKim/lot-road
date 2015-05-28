package com.nagesoft.postcode.board.model;

import com.nagesoft.core.model.NGDataChangeInfoForUser;
import com.nagesoft.core.mybatis.NGCountable;
import com.nagesoft.postcode.member.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 *
 * Board
 *
 * 게시판
 *
 * @author Juhyeon Kim
 *
 */
@ToString
public class Board implements NGCountable,NGDataChangeInfoForUser<User> {

	/**
	 * position Index - 페이징 용도
	 */
	private int positionIdx;

	/**
	 *  시퀀스
	 */
	@Setter
	@Getter
	private Long sequence;
	/**
	 * 이름
	 */
	@Setter
	@Getter
	private String name;
	/**
	 * 설명
	 */
	@Setter
	@Getter
	private String description;
	/**
	 * 화면 노출용 제목
	 */
	@Setter
	@Getter
	private String displayTitle;
	/**
	 * 헤더
	 */
	@Setter
	@Getter
	private String header;
	/**
	 * 상위 메뉴
	 */
	@Setter
	@Getter
	private String upperMenu;
	/**
	 * 게시판 타입
	 */
	@Setter
	@Getter
	private BoardType boardType;
	/**
	 * 게시판 URL
	 */
	@Setter
	@Getter
	private String boardURL;
	/**
	 * 카테고리
	 */
	@Setter
	@Getter
	private String category;
	/**
	 * 답변 사용 여부
	 */
	@Setter
	@Getter
	private String replyYn;
	/**
	 * 코멘트 사용 여부
	 */
	@Setter
	@Getter
	private String commentYn;
	/**
	 * 썸네일 사용 여부
	 */
	@Setter
	@Getter
	private String thumbnailYn;
	/**
	 * 첨부파일 사용 여부
	 */
	@Setter
	@Getter
	private String attachYn;
	/**
	 * 노출여부
	 */
	@Setter
	@Getter
	private String useYn;
	/**
	 * 삭제여부
	 */
	@Setter
	@Getter
	private String delYn;
	/**
	 * 이메일 발송 여부
	 */
	@Setter
	@Getter
	private String emailSendYn;
	/**
	 * URL 링크 사용 여부
	 */
	@Setter
	@Getter
	private String linkYn;
	/**
	 * 비밀글 사용 여부
	 */
	@Setter
	@Getter
	private String secretYn;
	/**
	 * 이전 및 다음글 사용 여부
	 */
	@Setter
	@Getter
	private String prevNextYn;
	/**
	 * 사용자 작성가능 여부
	 */
	@Setter
	@Getter
	private String userWritableYn;
	/**
	 * rownumber
	 */
	@Setter
	@Getter
	private Integer rownumber;
	/**
	 * 등록자
	 */
	private User regUser;
	/**
	 * 등록날짜
	 */
	@Setter
	@Getter
	private Date regDate;
	/**
	 * 수정자
	 */
	private User modUser;
	/**
	 * 수정날짜
	 */
	@Setter
	@Getter
	private Date modDate;
	/**
	 * 게시판 내의 게시글 리스트
	 */
	@Setter
	@Getter
	private List<Article> articleList;
	/**
	 * 공연 연동 사용 여부
	 */
	@Setter
	@Getter
	private String perfYn;
	/**
	 * 기간선택 사용 여부
	 */
	@Setter
	@Getter
	private String periodYn;

	@Override
	public int getPositionIdx() {
		return positionIdx;
	}
	@Override
	public void setPositionIdx(int position) {
		this.positionIdx = position;
	}
	@Override
	public User getRegUser() {
		return regUser;
	}
	@Override
	public void setRegUser(User regUser) {
		this.regUser = regUser;
	}
	@Override
	public User getModUser() {
		return modUser;
	}
	@Override
	public void setModUser(User modUser) {
		this.modUser = modUser;
	}

	public Board() {
		super();

		this.commentYn = "N";
		this.replyYn = "N";
		this.thumbnailYn = "N";
		this.attachYn = "N";
		this.useYn = "Y";
		this.delYn = "N";
		this.prevNextYn = "Y";
		this.secretYn = "N";
		this.emailSendYn = "N";
		this.userWritableYn = "N";
		this.linkYn = "N";
		this.perfYn = "N";
		this.periodYn = "N";
	}


}
