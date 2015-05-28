package com.nagesoft.postcode.common.service.impl;

import com.nagesoft.core.NGConfiguration;
import com.nagesoft.postcode.common.model.FileCmmnRecord;
import com.nagesoft.postcode.common.service.FileCmmnService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 파일 업로드 서비스
 *
 * @author ChoiJudong
 */
@Log4j
@Service
public class FileCmmnServiceImpl implements FileCmmnService {
	/**
	 * 파일 업로드 설정 파일 정보
	 */
	@Autowired
	private NGConfiguration config;

	/**
	 * FileDownload Separator : Pipe
	 */
	public final static String FILE_DOWNLOAD_SEPARATOR = "|";

	public final static String FILE_DOWNLOAD_SEPARATOR_SPLIT = "\\" + FILE_DOWNLOAD_SEPARATOR;

	/**
	 * FileUpload Category
	 */
	public final static String FILE_UPLOAD_CATEGORY_RECOGNITION = "01";  // 인정업무

	public final static String FILE_UPLOAD_CATEGORY_DOC_STYLE = "02";    // 문서양식

	public final static String FILE_UPLOAD_CATEGORY_USER = "03";         // 사용자정보(가입정보,신청정보)

	public final static String FILE_UPLOAD_CATEGORY_XLS_UPLOAD = "04";   // 엑셀업로드양식

	public final static String FILE_UPLOAD_CATEGORY_ETC = "99";          // 기타

	public final static String FILE_UPLOAD_CATEGORY_TEMP = "00";         // 임시(자바임시폴더에 파일을 저장한다. DB저장 X)

	/* FileUpload Temp Path */

	/**
	 * @deprecated nage.fileUpload.tempPath
	 */
	@Deprecated
	public final static String FILE_UPLOAD_TEMP_PATH = "D:/WORKSPACES/mdtheater.or.kr/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/project-md-admin/upload/temp";//임시파일경로

	/**
	 * @deprecated nage.fileUpload.base.imagePath
	 */
	@Deprecated
	public final static String FILE_BASE_IMG_PATH = "D:/WORKSPACES/mdtheater.or.kr/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/project-md-admin/images";//이미지 파일 경로

	/**
	 * @deprecated nage.fileUpload.basePath
	 */
	@Deprecated
	public final static String FILE_BASE_PATH = "D:/WORKSPACES/mdtheater.or.kr/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/project-md-admin/upload";//기본 이미지 파일 경로

	/**
	 * @deprecated nage.fileUpload.logicalPath
	 */
	@Deprecated
	public final static String FILE_LOGICAL_UPLOAD_PATH = "/upload";

	/**
	 * @deprecated nage.fileUpload.logical.imagePath
	 */
	@Deprecated
	public final static String FILE_LOGICAL_IMAGE_UPLOAD_PATH = "/upload/images";

	public final static Long FILE_SINGLE_MAX_SIZE = 1000000000L;//최대 사이즈 경로
	//public final static String FILE_SECURITY_EXT = "";

	//@Autowired 공통코드 매퍼 codeMapper;

	/**
	 * 등록가능한 확장자 목록
	 *
	 * @return
	 */

	public List<String> getFileExtList() {

		List<String> groups = new ArrayList<String>();
		//프로퍼티로 관리할지 DB 코드로 관리할지 두가지 방법이 존재
		groups.add("jpg");
		groups.add("png");
		groups.add("bmp");
		groups.add("gif");
		groups.add("txt");
		groups.add("zip");
		groups.add("pdf");

		return groups;
	}

	/**
	 * 공통 파일정보 가져오기
	 * -
	 */
	public List<FileCmmnRecord> getFileCmmnList(String fileUploadCategory, MultipartFile[] multipartFiles, String imgYn)
		throws Exception {
		if (StringUtils.isEmpty(fileUploadCategory)) {
			log.error("-------------------------------------------------------------------------------");
			log.error("파일업로드 분류값이 잘못되었습니다. fileUploadCategory : " + fileUploadCategory);
			log.error("-------------------------------------------------------------------------------");

			return null;
		}

		List<FileCmmnRecord> fileCmmnList = null;

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		log.debug("---------------------------------------------");
		log.debug("파일업로드 시작.");
		log.debug("---------------------------------------------");

		try {

			if (multipartFiles != null && multipartFiles.length > 0) {
//				String baseRepository = FILE_BASE_PATH;   // 기본저장소
				String baseRepository = ("Y".equals(imgYn))? config.getProperty("nage.fileUpload.base.imagePath"): config
					.getProperty("nage.fileUpload.basePath");

				File baseRepositoryFolder = new File(baseRepository);
				File createFolder;

				StringBuilder saveFilePathBuffer = new StringBuilder();
				StringBuilder saveFileAllPathBuffer = new StringBuilder();
				StringBuilder createFolderPathBuffer = new StringBuilder();
				StringBuilder saveFileOnlyPathBuffer = new StringBuilder();

				//------------------------------------------------------------------
				//  기본저장소 폴더확인
				//------------------------------------------------------------------
				if (!baseRepositoryFolder.exists() || !baseRepositoryFolder.isDirectory()) {
					log.error("-------------------------------------------------------------------------------");
					log.error("기본파일저장소 설정이 잘못되었습니다. 경로가 존재하지 않거나 폴더가 아닐 수가 있습니다.");
					log.error("baseRepository : " + baseRepository);
					log.error("-------------------------------------------------------------------------------");

					return null;
				}

				//------------------------------------------------------------------
				// 분류폴더가 존재하는지 확인후 없으면 생성(파일분류가 임시가 아닐경우에만 실행한다.)
				//------------------------------------------------------------------
				if (!fileUploadCategory.equals(FILE_UPLOAD_CATEGORY_TEMP)) {

					createFolderPathBuffer.setLength(0);
					createFolderPathBuffer.append(baseRepository).append("/").append(fileUploadCategory);
					createFolder = new File(createFolderPathBuffer.toString());

					if (!createFolder.exists()) {
						if (createFolder.mkdir()) {
							log.debug("-------------------------------------------------------------------------------");
							log.debug("파일저장소. 파일분류 폴더생성완료");
							log.debug("createFolder : " + createFolder.toString());
							log.debug("-------------------------------------------------------------------------------");
						} else {
							log.error("-------------------------------------------------------------------------------");
							log.error("파일저장소. 파일분류 폴더를 생성할 수가 없습니다.");
							log.error("createFolder : " + createFolder.toString());
							log.error("-------------------------------------------------------------------------------");

							return null;
						}
					}

					//  저장폴더 존재하는지 확인후 없으면 생성
					createFolderPathBuffer.append("/");
					createFolder = new File(createFolderPathBuffer.toString());

					if (!createFolder.exists()) {
						if (createFolder.mkdir()) {
							log.debug("-------------------------------------------------------------------------------");
							log.debug("파일저장소. 폴더생성완료");
							log.debug("createFolder : " + createFolder.toString());
							log.debug("-------------------------------------------------------------------------------");
						} else {
							log.error("-------------------------------------------------------------------------------");
							log.error("파일저장소.  폴더를 생성할 수가 없습니다.");
							log.error("createFolder : " + createFolder.toString());
							log.error("-------------------------------------------------------------------------------");

							return null;
						}
					}
				}


				fileCmmnList = new ArrayList<FileCmmnRecord>();

				for (MultipartFile multipartFile : multipartFiles) {
					//--------------------------------------------------------
					// 첨부파일 용량이 0 Byte이하면 무조건 업로드X
					//--------------------------------------------------------
					if (multipartFile.getSize() <= 0) {
						log.info("-------------------------------------------------------------------------------");
						log.info("첨부파일 용량이 0Byte입니다.");
						log.info("-------------------------------------------------------------------------------");

						continue;
					} else {
						//--------------------------------------------------------
						// 첨부파일 용량이 제한용량을 초과하면 업로드 X
						//--------------------------------------------------------
						if (multipartFile.getSize() > FILE_SINGLE_MAX_SIZE) {
							log.warn("-------------------------------------------------------------------------------");
							log.warn("첨부파일 용량이 제한용량을 초과하였습니다. fileSize : " + multipartFile.getSize());
							log.warn("-------------------------------------------------------------------------------");

							continue;
						}
					}

					boolean extChk = false;
					//--------------------------------------------------------
					// 업로드금지 첨부파일
					//--------------------------------------------------------

					List<String> extList = this.getFileExtList();

					String fileExtension = FileCmmnServiceImpl.getFileExtensions(multipartFile.getOriginalFilename());
					if (StringUtils.isEmpty(fileExtension)) {
						log.warn("-------------------------------------------------------------------------------");
						log.warn("첨부파일에 확장자가 존재하지 않습니다.");
						log.warn("-------------------------------------------------------------------------------");

						continue;
					} else {
						String cmpExtension = fileExtension.toLowerCase();

						for (String record : extList) {
							if (cmpExtension.equals(record.toLowerCase())) {
								extChk = true;
							}
						}
					}

					if (extChk) {

						String fileId = null;
						String originalFileName = null;
						String saveFileName = null;
						String saveFilePath = null;
						String saveOnlyPath = null;
						String saveFileAllPath = null;
						long fileSize = 0;

						saveFilePathBuffer.setLength(0);
						saveFileAllPathBuffer.setLength(0);
						saveFileOnlyPathBuffer.setLength(0);
						//--------------------------------------------------------
						// 파일분류가 임시일 경우에는 자바임시폴더에 파일을 저장한다.
						//--------------------------------------------------------
						if (fileUploadCategory.equals(FILE_UPLOAD_CATEGORY_TEMP)) {
							// 파일ID(예:12091410105733282835)
							fileId = FileCmmnServiceImpl.getFileId();

							// 원본파일명(예 : 테스트.txt)
							originalFileName = multipartFile.getOriginalFilename().replace(" ", "_");

							// 실제저파일명(예 : 12091410105733282835.txt)
							saveFileName = fileId + "." + FilenameUtils.getExtension(originalFileName);

							// 저장 경로파일명(예 : /12091410105733282835.txt)
							saveFilePath = saveFilePathBuffer.append("/").append(saveFileName).toString();

							// 저장 경로전체 파일명(예 : C:/Users/WON/AppData/Local/Temp/12091410105733282835.txt)
							saveFileAllPath = saveFileAllPathBuffer.append(config.getProperty("nage.fileUpload.tempPath"))
							                                       .append("/")
							                                       .append(saveFilePath)
							                                       .toString();

							//저장경로
							saveOnlyPath = saveFileOnlyPathBuffer.append(config.getProperty("nage.fileUpload.tempPath"))
							                                     .append("/")
							                                     .toString();

							// 파일크기
							fileSize = multipartFile.getSize();
						} else {
							// 파일ID(예:12091410105733282835)
							fileId = FileCmmnServiceImpl.getFileId();
							// 원본파일명(예 : 테스트.txt)
							originalFileName = multipartFile.getOriginalFilename().replace(" ", "_");

							// 실제저파일명(예 : 12091410105733282835.txt)
							saveFileName = fileId + "." + FilenameUtils.getExtension(originalFileName);

							// 저장 경로파일명(예 : /201208/12091410105733282835.txt)
							saveFilePath = saveFilePathBuffer.append("/").append(saveFileName).toString();

							// 저장 경로전체 파일명(예 : /201208/12091410105733282835.txt)
							saveFileAllPath = saveFileAllPathBuffer.append(baseRepository)
							                                       .append("/")
							                                       .append(fileUploadCategory)
							                                       .append(saveFilePath)
							                                       .toString();

							//저장경로
							saveOnlyPath = saveFileOnlyPathBuffer.append(baseRepository)
							                                     .append("/")
							                                     .append(fileUploadCategory)
							                                     .toString();
							// 파일크기
							fileSize = multipartFile.getSize();
						}

						// 지정한 경로에 파일이동
						multipartFile.transferTo(new File(saveFileAllPath));


						FileCmmnRecord fileCmmnVO = new FileCmmnRecord();
						fileCmmnVO.setFileId(fileId);
						fileCmmnVO.setFileGubun(fileUploadCategory);
						fileCmmnVO.setFileName(originalFileName);
						fileCmmnVO.setFilePath(saveFilePath);
						fileCmmnVO.setFileOnlyPath(saveOnlyPath);
						fileCmmnVO.setFileSize(fileSize);
						fileCmmnVO.setFileAllPath(saveFileAllPath);
						fileCmmnVO.setFileRegDate(FileCmmnServiceImpl.getNowTimestamp());
						fileCmmnVO.setFileSysName(saveFileName);
						if (!multipartFile.getContentType().toLowerCase().contains("image")) {
							fileCmmnVO.setFileImgYn("N");
						} else {
							fileCmmnVO.setFileImgYn("Y");
						}
						if ("Y".equals(imgYn)) {
							fileCmmnVO.setFileLogicalPath(config.getProperty("nage.fileUpload.logical.imagePath") + "/" + fileUploadCategory);
						} else {
							fileCmmnVO.setFileLogicalPath(config.getProperty("nage.fileUpload.logicalPath") + "/" + fileUploadCategory);
						}
						fileCmmnList.add(fileCmmnVO);

						log.debug("---------------------------------------------");
						log.debug("파일업로드가 정상적으로 수행되었습니다.");
						log.debug(fileCmmnVO.toString());
						log.debug("---------------------------------------------");
					} else {
						log.warn("-------------------------------------------------------------------------------");
						log.warn("첨부파일이 보안사항에 위배되었습니다. fileExtension : " + fileExtension);
						log.warn("-------------------------------------------------------------------------------");
					}
				}
			}
		} catch (Exception ignored) {
		} finally {
			log.debug("---------------------------------------------");
			log.debug("파일업로드 종료. Time : " + stopWatch.getTime() + "ms");
			log.debug("---------------------------------------------");
		}

		return fileCmmnList;
	}

	/**
	 * 파일 썸네일 추출
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public FileCmmnRecord getFileThumbnail(String fileUploadCategory, MultipartFile multipartFile, int maxDim)
		throws Exception {
		FileCmmnRecord fileCmmnVO = null;

		if (multipartFile != null && multipartFile.getSize() > 0) {
			MultipartFile[] multipartFiles = new MultipartFile[]{multipartFile};

			List<FileCmmnRecord> fileCmmnList = getFileCmmnList(fileUploadCategory, multipartFiles, "Y");

			if (fileCmmnList != null && !fileCmmnList.isEmpty()) {
				fileCmmnVO = fileCmmnList.get(0);

				if ("Y".equals(fileCmmnVO.getFileImgYn())) {
					String saveFile = config.getProperty("nage.fileUpload.base.imagePath") + "/" + fileUploadCategory + "/" + fileCmmnVO
						.getFileId() + "_thumb.jpg";

					String loadFile = fileCmmnVO.getFileAllPath();

					log.debug("---------------------------------------------");
					log.debug("파일 썸네일 추출 시작");
					log.debug("---------------------------------------------");

					File save = new File(saveFile.replaceAll("/", "\\" + File.separator));

					FileInputStream fis = new FileInputStream(loadFile.replaceAll("/", "\\" + File.separator));
					BufferedImage im = ImageIO.read(fis);

					Image inImage = new ImageIcon(loadFile).getImage();
					double scale = (double)maxDim / (double)inImage.getHeight(null);

					if (inImage.getWidth(null) > inImage.getHeight(null)) {
						scale = (double)maxDim / (double)inImage.getWidth(null);
					}

					int scaledW = (int)(scale * inImage.getWidth(null));
					int scaledH = (int)(scale * inImage.getHeight(null));

					BufferedImage thumb = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_RGB);

					Graphics2D g2 = thumb.createGraphics();

					g2.drawImage(im, 0, 0, scaledW, scaledH, null);

					ImageIO.write(thumb, "jpg", save);

					log.debug("---------------------------------------------");
					log.debug("파일 썸네일 추출 종료");
					log.debug("---------------------------------------------");

					fileCmmnVO.setFileSysName(fileCmmnVO.getFileId() + "_thumb.jpg");
					fis.close();
					//   FileUtils.forceDelete( new File(fileCmmnVO.getFileAllPath()));
				} else {
					log.debug("---------------------------------------------");
					log.debug("파일 썸네일이 이미지 파일이 아님");
					log.debug("---------------------------------------------");
				}
			}
		}

		return fileCmmnVO;
	}

	/**
	 * 파일ID를 구해준다.
	 * - 년월일시분 난수6자리 : 총20자리
	 * - 예) 12091410105767041072
	 *
	 * @return
	 */
	public static String getFileId() {
		StringBuffer fileNameBuffer = new StringBuffer();
		fileNameBuffer.append(getPatternNowDate("yyMMddHHmmss"));
		fileNameBuffer.append(RandomStringUtils.randomNumeric(8));

		return fileNameBuffer.toString();
	}

	/**
	 * 현재일시는 패턴에 맞게 가져오기
	 *
	 * @param pattern
	 *
	 * @return
	 */
	public static String getPatternNowDate(String pattern) {
		String nowDate = null;

		if (StringUtils.isNotEmpty(pattern)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			nowDate = simpleDateFormat.format(new Date());
		}

		return nowDate;
	}

	/**
	 * 현재 Timestamp 구하기
	 *
	 * @return
	 */
	public static Timestamp getNowTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 파일확장자를 추출한다.
	 *
	 * @param originalFileName
	 *
	 * @return
	 */
	public static String getFileExtensions(String originalFileName) {
		String fileExtension = null;

		if (StringUtils.isNotEmpty(originalFileName)) {
			int dotIndex = originalFileName.lastIndexOf(".");

			if (dotIndex > 0) {
				fileExtension = originalFileName.substring(dotIndex + 1, originalFileName.length());
			}
		}

		return fileExtension;
	}

	public List<String> getFileIdList(String fileUploadCategory, MultipartFile[] multipartFiles) throws Exception {
		List<FileCmmnRecord> fileCmmnList = getFileCmmnList(fileUploadCategory, multipartFiles, "N");
		List<String> fileIdList = null;

		if (fileCmmnList != null && fileCmmnList.size() > 0) {
			fileIdList = new ArrayList<String>();

			for (int i = 0; i < fileCmmnList.size(); i++) {
				FileCmmnRecord fileCmmnVO = fileCmmnList.get(i);

				fileIdList.add(fileCmmnVO.getFileId());
			}
		}

		return fileIdList;
	}

	public FileCmmnRecord getFileCmmnVO(String fileUploadCategory, MultipartFile multipartFile, String imgYn)
		throws Exception {
		FileCmmnRecord fileCmmnVO = null;

		if (multipartFile != null && multipartFile.getSize() > 0) {
			MultipartFile[] multipartFiles = new MultipartFile[]{multipartFile};

			List<FileCmmnRecord> fileCmmnList = getFileCmmnList(fileUploadCategory, multipartFiles, imgYn);

			if (fileCmmnList != null && fileCmmnList.size() > 0) {
				fileCmmnVO = fileCmmnList.get(0);
			}
		}

		return fileCmmnVO;
	}

	public FileCmmnRecord getFileCmmnVO(String fileUploadCategory, File file) throws Exception {
		if (file == null || !file.exists()) {
		/*	logger.info("-------------------------------------------------------------------------------");
			logger.info("파일정보가 존재하지 않습니다.");
			logger.info("-------------------------------------------------------------------------------");*/
		}

		String baseRepository = FILE_BASE_PATH;   // 기본저장소
		File baseRepositoryFolder = new File(baseRepository);
		File createFolder;

		StringBuilder saveFilePathBuffer = new StringBuilder();
		StringBuilder saveFileAllPathBuffer = new StringBuilder();
		StringBuilder createFolderPathBuffer = new StringBuilder();


		//------------------------------------------------------------------
		// 기본저장소 폴더확인
		//------------------------------------------------------------------
		if (!baseRepositoryFolder.exists() || !baseRepositoryFolder.isDirectory()) {
			/*logger.info("-------------------------------------------------------------------------------");
			logger.info("기본파일저장소 설정이 잘못되었습니다. 경로가 존재하지 않거나 폴더가 아닐 수가 있습니다.");
			logger.info("baseRepository : " + baseRepository);
			logger.info("-------------------------------------------------------------------------------");*/
			return null;
		}


		//------------------------------------------------------------------
		// 분류폴더가 존재하는지 확인후 없으면 생성(파일분류가 임시가 아닐경우에만 실행한다.)
		//------------------------------------------------------------------
		if (!fileUploadCategory.equals(FILE_UPLOAD_CATEGORY_TEMP)) {

			createFolderPathBuffer.setLength(0);
			createFolderPathBuffer.append(baseRepository).append("/").append(fileUploadCategory);
			createFolder = new File(createFolderPathBuffer.toString());

			if (!createFolder.exists()) {
				if (createFolder.mkdir()) {
					log.debug("-------------------------------------------------------------------------------");
					log.debug("파일저장소. 파일분류 폴더생성완료");
					log.debug("createFolder : " + createFolder.toString());
					log.debug("-------------------------------------------------------------------------------");
				} else {
					log.error("-------------------------------------------------------------------------------");
					log.error("파일저장소. 파일분류 폴더를 생성할 수가 없습니다.");
					log.error("createFolder : " + createFolder.toString());
					log.error("-------------------------------------------------------------------------------");

					return null;
				}
			}

			//  저장폴더 존재하는지 확인후 없으면 생성
			createFolderPathBuffer.append("/");
			createFolder = new File(createFolderPathBuffer.toString());

			if (!createFolder.exists()) {
				if (createFolder.mkdir()) {
					log.debug("-------------------------------------------------------------------------------");
					log.debug("파일저장소. 폴더생성완료");
					log.debug("createFolder : " + createFolder.toString());
					log.debug("-------------------------------------------------------------------------------");
				} else {
					log.error("-------------------------------------------------------------------------------");
					log.error("파일저장소. 폴더를 생성할 수가 없습니다.");
					log.error("createFolder : " + createFolder.toString());
					log.error("-------------------------------------------------------------------------------");

					return null;
				}
			}
		}


		//--------------------------------------------------------
		// 첨부파일 용량이 0 Byte이하면 무조건 업로드X
		//--------------------------------------------------------
		assert file != null;
		if (file.length() <= 0) {
			log.error("-------------------------------------------------------------------------------");
			log.error("첨부파일 용량이 0Byte입니다.");
			log.error("-------------------------------------------------------------------------------");

			return null;
		} else {
			//--------------------------------------------------------
			// 첨부파일 용량이 제한용량을 초과하면 업로드 X
			//--------------------------------------------------------
			if (file.length() > FILE_SINGLE_MAX_SIZE) {
				log.error("-------------------------------------------------------------------------------");
				log.error("첨부파일 용량이 제한용량을 초과하였습니다. fileSize : " + file.length());
				log.error("-------------------------------------------------------------------------------");

				return null;
			}
		}

		//--------------------------------------------------------
		// 업로드금지 첨부파일
		//--------------------------------------------------------
		//	String[] fileExtensions = FILE_SECURITY_EXT.split(";");
		String fileExtension = FileCmmnServiceImpl.getFileExtensions(file.getName());
		boolean extChk = false;


		List<String> extList = this.getFileExtList();

		if (StringUtils.isEmpty(fileExtension)) {
			log.error("-------------------------------------------------------------------------------");
			log.error("첨부파일에 확장자가 존재하지 않습니다.");
			log.error("-------------------------------------------------------------------------------");

			return null;
		} else {
			String cmpExtension = "." + fileExtension;

			for (String record : extList) {
				if (cmpExtension.equals(record)) {
					extChk = true;
				}
			}
		}

		FileCmmnRecord fileCmmnVO = new FileCmmnRecord();

		if (extChk) {
			String fileId;
			String originalFileName;
			String saveFileName;
			String saveFilePath;
			String saveFileAllPath;
			String saveOnlyPath;

			long fileSize;

			saveFilePathBuffer.setLength(0);
			saveFileAllPathBuffer.setLength(0);


			//--------------------------------------------------------
			// 파일분류가 임시일 경우에는 자바임시폴더에 파일을 저장한다.
			//--------------------------------------------------------
			if (fileUploadCategory.equals(FILE_UPLOAD_CATEGORY_TEMP)) {
				// 파일ID(예:12091410105733282835)
				fileId = FileCmmnServiceImpl.getFileId();

				// 원본파일명(예 : 테스트.txt)
				originalFileName = file.getName().replace(" ", "_");

				// 실제저파일명(예 : 12091410105733282835.txt)
				saveFileName = fileId + "." + FilenameUtils.getExtension(originalFileName);

				// 저장 경로파일명(예 : /12091410105733282835.txt)
				saveFilePath = saveFilePathBuffer.append("/").append(saveFileName).toString();

				// 저장 경로전체 파일명(예 : C:/Users/WON/AppData/Local/Temp/12091410105733282835.txt)
				saveFileAllPath = saveFileAllPathBuffer.append(config.getProperty("nage.fileUpload.tempPath"))
				                                       .append("/")
				                                       .append(saveFilePath)
				                                       .toString();

				saveOnlyPath = saveFileAllPathBuffer.append(config.getProperty("nage.fileUpload.tempPath"))
				                                    .append("/")
				                                    .toString();

				// 파일크기
				fileSize = file.length();
			} else {
				// 파일ID(예:12091410105733282835)
				fileId = FileCmmnServiceImpl.getFileId();

				// 원본파일명(예 : 테스트.txt)
				originalFileName = file.getName().replace(" ", "_");

				// 실제저파일명(예 : 12091410105733282835.txt)
				saveFileName = fileId + "." + FilenameUtils.getExtension(originalFileName);

				// 저장 경로파일명(예 : /201208/12091410105733282835.txt)
				saveFilePath = saveFilePathBuffer.append("/").append(saveFileName).toString();

				// 저장 경로전체 파일명(예 : D:/99/201208/12091410105733282835.txt)
				saveFileAllPath = saveFileAllPathBuffer.append(baseRepository)
				                                       .append("/")
				                                       .append(fileUploadCategory)
				                                       .append(saveFilePath)
				                                       .toString();

				saveOnlyPath = saveFileAllPathBuffer.append(baseRepository)
				                                    .append("/")
				                                    .append(fileUploadCategory)
				                                    .toString();

				// 파일크기
				fileSize = file.length();
			}

			// 지정한 경로에 파일이동
			FileUtils.moveFile(file, new File(saveFileAllPath));


			fileCmmnVO.setFileId(fileId);
			fileCmmnVO.setFileGubun(fileUploadCategory);
			fileCmmnVO.setFileName(originalFileName);
			fileCmmnVO.setFilePath(saveFilePath);
			fileCmmnVO.setFileOnlyPath(saveOnlyPath);
			fileCmmnVO.setFileSize(fileSize);
			fileCmmnVO.setFileAllPath(saveFileAllPath);
			fileCmmnVO.setFileRegDate(FileCmmnServiceImpl.getNowTimestamp());
			fileCmmnVO.setFileSysName(saveFileName);

			fileCmmnVO.setFileLogicalPath(config.getProperty("nage.fileUpload.logicalPath") + "/" + fileUploadCategory);

			log.debug("---------------------------------------------");
			log.debug("파일업로드가 정상적으로 수행되었습니다.");
			log.debug("---------------------------------------------");
		} else {
			log.warn("-------------------------------------------------------------------------------");
			log.warn("첨부파일이 보안사항에 위배되었습니다. fileExtension : " + fileExtension);
			log.warn("-------------------------------------------------------------------------------");
		}

		return fileCmmnVO;
	}

	public String getFileId(String fileUploadCategory, MultipartFile multipartFile) throws Exception {
		String fileId = null;

		FileCmmnRecord fileCmmnVO = getFileCmmnVO(fileUploadCategory, multipartFile, "N");

		if (fileCmmnVO != null && StringUtils.isNotEmpty(fileCmmnVO.getFileId())) {
			fileId = fileCmmnVO.getFileId();
		}

		return fileId;
	}
}
