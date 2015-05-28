package com.nagesoft.postcode.common.service;


import com.nagesoft.postcode.common.model.FileCmmnRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;


/**
 * File 공통 서비스
 * @author ChoiJudong
 *
 */
public interface FileCmmnService {


	/**
	 *  MultipartFile[] 을 일정한 규칙에 맞게 저장하고 파일정보VO(List<FileCmmnVO>)를 리턴한다.
	 *
	 * @param fileUploadCategory
	 * @param multipartFiles
	 * @return
	 */
	public List<FileCmmnRecord> getFileCmmnList(String fileUploadCategory, MultipartFile[] multipartFiles, String imgYn) throws Exception;

	/**
	 *  MultipartFile[] 을 일정한 규칙에 맞게 저장하고 파일ID(List<String>)를 리턴한다.
	 *
	 * @param fileUploadCategory
	 * @param multipartFiles
	 * @return
	 */
	public List<String> getFileIdList(String fileUploadCategory, MultipartFile[] multipartFiles) throws Exception;

	/**
	 * MultipartFile(단일파일) 을 일정한 규칙에 맞게 저장하고 파일정보(FileCmmnVO)를 리턴한다.
	 *
	 * @param fileUploadCategory
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 */
	public FileCmmnRecord getFileCmmnVO(String fileUploadCategory, MultipartFile multipartFile, String imgYn) throws Exception;

	/**
	 * File 객체를 일정한 규칙에 맞게 저장하고 파일정보(FileCmmnVO)를 리턴한다.
	 *
	 * @param fileUploadCategory
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public FileCmmnRecord getFileCmmnVO(String fileUploadCategory, File file) throws Exception;


	/**
	 *  image Thumbnail 생성 후 리턴
	 *
	 * @param fileUploadCategory
	 * @param multipartFile
	 * @return
	 * @throws java.io.IOException
	 */
	public FileCmmnRecord getFileThumbnail(String fileUploadCategory, MultipartFile multipartFile, int maxDim) throws Exception;

}
