package com.nagesoft.postcode.board.service;

import com.nagesoft.postcode.board.dao.ArticleDao;
import com.nagesoft.postcode.board.model.Article;
import com.nagesoft.postcode.common.dao.AttachDao;
import com.nagesoft.postcode.common.model.Attach;
import com.nagesoft.postcode.common.model.ref.RefType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * ArticleManager
 *
 * 게시글 Service Layer
 *
 * @author Juhyeon Kim
 *
 */
@Service
public class ArticleManager {


	/**
	 * {@link ArticleDao}
	 */
	@Autowired
	private ArticleDao articleDao;

	/**
	 * {@link AttachDao}
	 */
	@Autowired
	private AttachDao attachDao;

	/**
	 * 게시글 입력 및 수정 처리<br />
	 * 게시글에 포함된 첨부파일처리도 함께 이뤄짐.
	 *
	 * @param article insert or update Data.
	 */
	@Transactional
	public void save(Article article) {

		if(article.getSequence() != null){
			articleDao.update(article);

			if(article.getThumbnailSequence() == null){
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("refType", RefType.ARTICLE_THUMBNAIL);
				param.put("referenceId", article.getSequence());
				attachDao.deleteAttachForEdit(param);
			}

			Map<String,Object> param = new HashMap<String,Object>();
			param.put("refType", RefType.ARTICLE_FILE);
			param.put("referenceId", article.getSequence());
			param.put("idList", article.getAttachSequenceList());
			if(article.getAttachSequenceList()!= null && article.getAttachSequenceList().length>0){
				attachDao.deleteAttachForEdit(param);
			}
			if(article.getAttachList() != null || article.getThumbnailFile() != null)
				saveAttach(article);

		}else{
			articleDao.insert(article);
			/*
			 * Parent Article 의 키값을 구해서 다시 업데이트 하기 위해..
			 * Oracle 같은 경우는 입력 전 Sequence 를 구해서 아래 내용 필요없음.
			 */
			Article rootArticle = new Article();
			rootArticle.setSequence(article.getSequence());
			article.setRootArticle(rootArticle);
			articleDao.updateArticleSequence(article);

			if(article.getAttachList() != null || article.getThumbnailFile() != null)
				saveAttach(article);

		}
	}


	/**
	 * 게시글 정보 조회
	 *
	 * @param param 검색 조건 - sequence
	 * @return 검색된 게시글 정보
	 */
	public Article getArticle(Map<String, Object> param) {

		return articleDao.getArticle(param);
	}

	/**
	 * 게시글 조회수 업데이트
	 *
	 * @param param 검색 조건 - sequence
	 */
	public void addHitCount(Map<String,Object> param){

		articleDao.addHitCount(param);
	}

	/**
	 * 이전 게시글 조회
	 *
	 * @param article 이전 게시글의 기준 게시글 (게시글 level 1 기준으로만 조회)
	 * @return 게시글 정보
	 */
	public Article getPrevArticle(Article article) {

		return articleDao.getPrevArticle(article);
	}

	/**
	 * 다음 게시글 조회
	 *
	 * @param article 다음 게시글의 기준 게시글 (게시글 level 1 기준으로만 조회)
	 * @return 게시글 정보
	 */
	public Article getNextArticle(Article article) {

		return articleDao.getNextArticle(article);
	}

	/**
	 * 게시글 목록 조회
	 *
	 * @param param 검색 조건 - title, contents, level, noticeYn(y|n), boardSequence(게시판 sequence), seq(게시글 sequence)
	 * @return 검색된 게시글 목록
	 */
	public List<Article> listArticle(Map<String, Object> param) {

		List<Article> result = articleDao.listArticle(param);

		return result;

	}

	/**
	 * 이벤트 목록 조회
	 *
	 * @param param 검색 조건 - title, contents, level, noticeYn(y|n), boardSequence(게시판 sequence), seq(게시글 sequence)
	 * @return 검색된 게시글 목록
	 */
	public List<Article> listEvent(Map<String, Object> param) {

		List<Article> result = articleDao.listEvent(param);

		return result;

	}

	/**
	 * 상위 N 개의 글 가져오기
	 * @param param (boardURL - 게시판 URL | number - 가져올 글 개수 | thumbnailRefType - 썸네일 refType)
	 * @return
	 */
	public List<Article> listArticleTop(Map<String,Object> param){

		return articleDao.listArticleTop(param);
	}

	/**
	 * 게시글 삭제
	 *
	 * @param sequence 게시글 일련번호
	 */
	public void delete(Long sequence) {

		articleDao.delete(sequence);

	}

	/**
	 * 답변 입력
	 *
	 * @param article
	 */
	public void saveReply(Article article){

		articleDao.updateReplyPrepareOrder(article);
		articleDao.insert(article);
	}

	/**
	 * 첨부파일 등록
	 *
	 * @param article
	 */
	private void saveAttach(Article article){
		if(article.getAttachList() != null){

			for(Attach attach : article.getAttachList()){
				attach.setRefKey(article.getSequence());
				attach.setRefType(RefType.ARTICLE_FILE);
				attachDao.addAttach(attach);
			}
		}else if(article.getThumbnail() != null){
			Attach thumbnail = article.getThumbnail();
			if(thumbnail != null){
				thumbnail.setRefKey(article.getSequence());
				thumbnail.setRefType(RefType.ARTICLE_THUMBNAIL);

				attachDao.addAttach(thumbnail);
			}
		}
	}

}
