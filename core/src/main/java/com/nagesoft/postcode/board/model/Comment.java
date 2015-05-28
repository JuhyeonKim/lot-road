package com.nagesoft.postcode.board.model;

import com.nagesoft.core.model.NGDataChangeInfoForUser;
import com.nagesoft.core.mybatis.NGCountable;
import com.nagesoft.postcode.member.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 코멘트
 *
 * @author Juhyeon Kim
 *
 */
@ToString
public class Comment implements NGCountable, NGDataChangeInfoForUser<User> {

	/**
	 * for Paging index
	 */
	private int positionIdx;

	/**
	 * 시퀀스
	 */
	@Setter
	@Getter
	private Long sequence;
	/**
	 * 내용
	 */
	@Setter
	@Getter
	private String contents;
	/**
	 * 등록자 닉네임
	 */
	@Setter
	@Getter
	private String regUserNickname;
	/**
	 * 이메일
	 */
	@Setter
	@Getter
	private String email;
	/**
	 * 최상위 코멘트
	 */
	@Setter
	@Getter
	private Comment rootComment;
	/**
	 * 코멘트 대상 게시글
	 */
	@Setter
	@Getter
	private Article article;
	/**
	 * 코멘트 레벨
	 */
	@Setter
	@Getter
	private Integer level;
	/**
	 * 하나의 root 코멘트 내의 정렬 순서
	 */
	@Setter
	@Getter
	private Integer replyOrder;
	/**
	 * 사용여부
	 */
	@Setter
	@Getter
	private String useYn;

	/**
	 * 등록자
	 */
	private User regUser;

	/**
	 * 등록일
	 */
	@Setter
	@Getter
	private Date regDate;

	/**
	 *  수정자
	 */
	private User modUser;

	/**
	 * 수정일
	 */
	@Setter
	@Getter
	private Date modDate;


	@Override
	public int getPositionIdx() {
		return positionIdx;
	}
	@Override
	public void setPositionIdx(int positionIdx) {
		this.positionIdx = positionIdx;
	}

	public Comment() {
		super();
		this.level = 1;
		this.replyOrder = 0;
		this.useYn="Y";
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


}
