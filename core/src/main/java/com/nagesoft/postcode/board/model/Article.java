package com.nagesoft.postcode.board.model;

import com.nagesoft.core.model.NGDataChangeInfoForUser;
import com.nagesoft.core.mybatis.NGCountable;
import com.nagesoft.core.validator.NGInsert;
import com.nagesoft.core.validator.NGUpdate;
import com.nagesoft.postcode.common.model.Attach;
import com.nagesoft.postcode.member.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Article
 * <p/>
 * 게시글
 *
 * @author Juhyeon Kim
 */
// TODO 등록 관리자, 등록 사용자, 수정관리자, 수정 사용자 분기작업..
@ToString
public class Article implements NGCountable, NGDataChangeInfoForUser<User> {

	/**
	 * position Index - 페이징용
	 */
	private int positionIdx;

	/**
	 * 게시글 시퀀스
	 */
	@Setter
	@Getter
	private Long sequence;

	/**
	 * 제목
	 */
	@Setter
	@Getter
	@Size(min = 1, max = 500, groups = {NGInsert.class, NGUpdate.class}, message = "제목은 필수 입니다.")
	private String title;

	/**
	 * 부 제목
	 */
	@Setter
	@Getter
	private String subTitle;

	/**
	 * 내용
	 */
	@Setter
	@Getter
	@Size(min = 1, max = 5000, groups = {NGInsert.class, NGUpdate.class}, message = "내용은 필수 입니다.")
	private String contents;

	/**
	 * URL
	 */
	@Getter
	private String url;

	/**
	 * 카테고리
	 */
	@Setter
	@Getter
	private String category;

	/**
	 * 카테고리
	 */
	@Setter
	@Getter
	private String categoryName;

	/**
	 * URL 타겟
	 */
	@Setter
	@Getter
	private String urlTarget;

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
	 * 최신글인지 아닌지
	 */
	@Setter
	@Getter
	private String isNew;

	/**
	 * 수정자
	 */
	private User modUser;

	/**
	 * 수정일
	 */
	@Setter
	@Getter
	private Date modDate;

	/**
	 * 조회수
	 */
	@Setter
	@Getter
	private Integer hitCount;

	/**
	 * 게시글이 포함된 게시판
	 */
	@Setter
	@Getter
	private Board board;

	/**
	 * 게시글 레벨
	 */
	@Setter
	@Getter
	private Integer level;

	/**
	 * 최상위 게시글
	 */
	@Setter
	@Getter
	private Article rootArticle;

	/**
	 * 사용여부
	 */
	@Setter
	@Getter
	private String useYn;

	/**
	 * 삭제 여부
	 */
	@Setter
	@Getter
	private String delYn;

	/**
	 * 답변 대상게시글 내의 정렬 순
	 */
	@Setter
	@Getter
	private Integer replyOrder;

	/**
	 * 공지 여부
	 */
	@Setter
	@Getter
	private String noticeYn;

	/**
	 * 첨부파일 - Form Data 전송용
	 */
	@Setter
	@Getter
	private CommonsMultipartFile[] attachFiles;

	/**
	 * 썸네일 - Form Data 전송용
	 */
	@Setter
	@Getter
	private CommonsMultipartFile thumbnailFile;

	/**
	 * 첨부파일 일련번호 목록 - 첨부파일 수정용
	 */
	@Setter
	@Getter
	private Long[] attachSequenceList;

	/**
	 * 썸네일 일련번호 - 썸네일 수정용
	 */
	@Setter
	@Getter
	private Long thumbnailSequence;

	/**
	 * 썸네일 정보
	 */
	@Setter
	@Getter
	private Attach thumbnail;

	/**
	 * 첨부파일 정보 목록
	 */
	@Setter
	@Getter
	private List<Attach> attachList;

	/**
	 * 시작일 ( 이벤트 기간 따위의 것 )
	 */
	@Setter
	@Getter
	private Date startDate;

	/**
	 * 종료일 ( 이벤트 기간 따위의 것 )
	 */
	@Setter
	@Getter
	private Date endDate;

	/**
	 * IP 주소
	 */
	@Getter
	@Setter
	private String ipAddress;

	/**
	 * 링크 정보 (링크 테이블)
	 */
	@Getter
	@Setter
	private List<Link> linkList;

	/**
	 * 링크 변경 구분자
	 */
	@Getter
	@Setter
	private Long typeChangeFlag;

	/**
	 * 상태<br />
	 * -1 : 대기 <br />
	 * 0 : 진행중 <br />
	 * 1 : 종료
	 */
	@Setter
	@Getter
	private Integer status;

	/**
	 * 목록 출력용 thumbnail image url
	 */
	@Getter
	@Setter
	private String listThumbnailURL;

	public void setUrl(String url) {

		url = url.replaceFirst("http://", "");
		url = url.replaceFirst("https://", "");

		this.url = url;
	}

	public Article() {
		super();

		this.useYn = "Y";
		this.delYn = "N";
		this.noticeYn = "N";
		this.hitCount = 0;
		this.level = 1;
		this.replyOrder = 0;
	}

	public String getLevelBullet() {
		String levelBullet = "";
		for (int i = 1; i < level; i++) {
			levelBullet += "&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		if (this.level > 1) {
			levelBullet += "┖>";
		}
		return levelBullet;
	}

	@Override
	public int getPositionIdx() {
		return positionIdx;
	}

	@Override
	public void setPositionIdx(int positionIdx) {
		this.positionIdx = positionIdx;
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
