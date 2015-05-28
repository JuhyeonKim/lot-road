package com.nagesoft.postcode.common.model;

import com.nagesoft.postcode.common.model.ref.RefType;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;


/**
 * File Model
 * @author ChoiJudong
 *
 */
public class FileCmmnRecord {

	private String fileId;         // 파일ID : 예) 14091410105733282835
	private String fileGubun;      // 파일구분
	private String fileName;       // 파일명 : 예) 가나다.txt
	private String filePath;       // 파일저장경로+파일명 : 예) /201408/12091410105733282835.txt
	private String fileSysName;	//저장된 파일명
	private String fileOnlyPath;	//파일저장경로
	private long fileSize;         // 파일크기 : 예) 256234
	private Date fileRegDate; // 파일등록일
	private String fileAllPath;    // 파일저장전체경로 : 예) D:/Temp/201408/12091410105733282835.txt
	private String fileLogicalPath;	//논리경로
	private String fileImgYn;	//이미지파일여부

	/**
	 * 파일확장자 구하기
	 *
	 * @return
	 */
	public String getFileExtension(){
		if(StringUtils.isNotEmpty(filePath)){
			return FilenameUtils.getExtension(filePath);
		}else{
			return null;
		}
	}

	/**
	 * 파일저장경로 가져오기
	 * @return
	 */
	public String getFileOnlyPath() {
		return fileOnlyPath;
	}

	/**
	 * 파일저장경로 저장
	 * @param fileOnlyPath
	 */
	public void setFileOnlyPath(String fileOnlyPath) {
		this.fileOnlyPath = fileOnlyPath;
	}

	/**
	 * 파일크기 int형으로 변환
	 *
	 * @return
	 */
	public int getFileSizeInt(){
		return (int)fileSize;
	}

	public String toString(){
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * 파일전체경로 가져오기
	 * @return
	 */
	public String getFileAllPath() {
		return fileAllPath;
	}

	/**
	 * 파일전체경로 셋팅
	 * @param fileAllPath
	 */
	public void setFileAllPath(String fileAllPath) {
		this.fileAllPath = fileAllPath;
	}

	/**
	 * 파일 구분값 가져오기
	 * @return
	 */
	public String getFileGubun() {
		return fileGubun;
	}

	/**
	 * 파일 구분값 셋팅
	 * @param fileGubun
	 */
	public void setFileGubun(String fileGubun) {
		this.fileGubun = fileGubun;
	}

	/**
	 * @return
	 */
	public Date getFileRegDate() {
		return fileRegDate;
	}

	public void setFileRegDate(Date fileRegDate) {
		this.fileRegDate = fileRegDate;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileSysName() {
		return fileSysName;
	}

	public void setFileSysName(String fileSysName) {
		this.fileSysName = fileSysName;
	}

	public String getFileLogicalPath() {
		return fileLogicalPath;
	}

	public void setFileLogicalPath(String fileLogicalPath) {
		this.fileLogicalPath = fileLogicalPath;
	}

	public String getFileImgYn() {
		return fileImgYn;
	}

	public void setFileImgYn(String fileImgYn) {
		this.fileImgYn = fileImgYn;
	}

	public Attach convertAttach(){
		Attach attach = new Attach();

		attach.setDisplayName(fileName);
		attach.setFileSize(this.fileSize);
		attach.setFileType(this.getFileExtension());
		attach.setSavedDir(this.fileLogicalPath);
		attach.setSavedName(this.fileSysName);

		return attach;
	}
	public Attach convertAttach(Long refKey, RefType refType){
		Attach attach = new Attach();

		attach.setDisplayName(fileName);
		attach.setFileSize(this.fileSize);
		attach.setFileType(this.getFileExtension());
		attach.setRefKey(refKey);
		attach.setRefType(refType);
		attach.setSavedDir(this.fileLogicalPath);
		attach.setSavedName(this.fileSysName);

		return attach;
	}
}
