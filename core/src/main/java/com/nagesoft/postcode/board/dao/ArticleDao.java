package com.nagesoft.postcode.board.dao;

import com.nagesoft.core.mybatis.NGPageable;
import com.nagesoft.postcode.board.model.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 게시글 Dao
 *
 * @author Juhyeon Kim
 *
 */
@Repository(value = "articleDao")
public interface ArticleDao {

	/**
	 * 게시글 저장
	 *
	 * @param article 입력 할 게시글 정보
	 */
	public void insert(Article article);

	/**
	 * 게시글 수정
	 *
	 * @param article 수정 할 게시글 정보
	 */
	public void update(Article article);


	/**
	 * 게시글 단일건 조회
	 *
	 * @param param 검색 조건 - sequence
	 * @return 검색된 게시글
	 */
	public Article getArticle(Map<String, Object> param);

	/**
	 * 게시글 조회
	 *
	 * @param param 검색 조건 - title, contents, level, noticeYn(y|n), boardSequence(게시판 sequence), seq(게시글 sequence)
	 * @return 조회된 게시글 정보
	 */
	@NGPageable(countMapperID="listArticleCnt")
	public List<Article> listArticle(Map<String, Object> param);

	/**
	 * 이벤트 조회
	 *
	 * @param param 검색 조건 - title, contents, level, noticeYn(y|n), boardSequence(게시판 sequence), seq(게시글 sequence)
	 * @return 조회된 게시글 정보
	 */
	@NGPageable(countMapperID="listArticleCnt")
	public List<Article> listEvent(Map<String, Object> param);

	/**
	 * 상위 N 개의 글 가져오기
	 * @param param (boardURL - 게시판 URL | number - 가져올 글 개수 | thumbnailRefType - 썸네일 refType)
	 * @return 검색 결과
	 */
	public List<Article> listArticleTop(Map<String, Object> param);


	/**
	 * 게시글 개수
	 *
	 * @param param 검색 조건 - title, contents, level, noticeYn(y|n), boardSequence(게시판 sequence), seq(게시글 sequence)
	 * @return 검색된 게시글 개수
	 */
	public Integer listArticleCnt(Map<String, Object> param);

	/**
	 * 게시글 삭제
	 *
	 * @param sequence 게시글 아이디
	 */
	public void delete(Long sequence);

	/**
	 * 게시글 조회수 업데이트
	 *
	 * @param article 검색 조건 - sequence
	 */
	public void addHitCount(Map<String, Object> param);

	/**
	 * 계층형 부모글 처리 (Mysql, MSSql 전용)
	 *
	 * @param article 부모글
	 */
	public void updateArticleSequence(Article article);

	/**
	 * 계층형 답글 정렬순서 사전 처리
	 *
	 * @param article 답변 게시글
	 */
	public void updateReplyPrepareOrder(Article article);

	/**
	 * 이전글 조회
	 *
	 * @param article 이전 기준이 되는 게시글 정보
	 */
	public Article getPrevArticle(Article article);

	/**
	 * 다음글 조회
	 *
	 * @param article 다음글의 기준이 되는 게시글 정보
	 */
	public Article getNextArticle(Article article);

	/**
	 * 게시글 저장
	 *
	 * @param article 입력 할 게시글 정보
	 */
	public void insertLink(Map<String, Object> linkMap);
}
