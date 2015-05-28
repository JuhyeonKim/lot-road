package com.nagesoft.postcode.board.model;


import com.nagesoft.postcode.common.model.Attach;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 *
 * Link
 *
 * 게시글
 *
 * @author cloud
 *
 */
// TODO 등록 관리자, 등록 사용자, 수정관리자, 수정 사용자 분기작업..
@ToString
public class Link{

	/**
	 *  링크 시퀀스
	 */
	@Setter
	@Getter
	private Long sequence;
	/**
	 *  첨부파일 키
	 */
	@Setter
	@Getter
	
	private Long attachSeq;
	/**
	 *  게시글 키
	 */
	@Setter
	@Getter
	private Long articleSeq;
	/**
	 *  URL
	 */
	@Setter
	@Getter
	private String linkUrl;
	/**
	 *  링크 대상
	 */
	@Setter
	@Getter
	private String linkTarget;

	/**
	 * 첨부파일 - Form Data 전송용
	 */
	@Setter
	@Getter
	private CommonsMultipartFile[] attachFiles;
	
	/**
	 * 첨부파일 정보 목록
	 */
	@Setter
	@Getter
	private List<Attach> attachList;
	
	/**
	 * 첨부파일 일련번호 목록 - 첨부파일 수정용
	 */
	@Setter
	@Getter
	private Long[] attachSequenceList;

}
