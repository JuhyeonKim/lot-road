package com.nagesoft.postcode.code.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nagesoft.postcode.code.dao.CommonCodeDAO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagesoft.postcode.code.model.CommonCode;
import com.nagesoft.common.NGStringUtil;

/**
 * CommonCodeManager
 *
 * 공통코드관리 Service Layer
 *
 * @author Juhyoen Kim
 *
 */
@Service
public class CommonCodeManager {

	@Autowired
	private CommonCodeDAO dao;

	/**
	 * 코드 목록
	 */
	private static List<Map<String, Object>> codeList;

	/**
	 * 공통코드 저장/수정 처리
	 *
	 * @param code 코드
	 */
	@Transactional
	public void saveCommonCode(CommonCode code) {
		if (StringUtils.isEmpty(code.getCode())) {
			// 저장
			code.setCode(makeCode());

			if (code.getParent() == null || StringUtils.isEmpty(code.getParent().getCode())) {
				// 자신의 코드를 그룹 코드로 지정
				code.setLevel(1);
				code.setParent(null);
				code.setSortOrder(null);
			} else {
				Map<String, Object> parameter = new HashMap<String, Object>();

				parameter.put("code", code.getParent().getCode());

				CommonCode parentCode = dao.getCode(parameter);

				code.setLevel(parentCode.getLevel() + 1);

				if (code.getSortOrder() == null) {
					code.setSortOrder(parentCode.getMaxSortOrder() + 1);
				} else {
					// 지정된 정렬순서 이후로 모두 업데이트
					Integer maxSortOrder = dao.getCommonCodeCount(code.getParent().getCode());

					if (maxSortOrder > 1 && code.getSortOrder() >= 1) {
						dao.updateNextSortOrderAll(code.getSortOrder(), code.getParent().getCode(), code.getModUser());
					} else if (code.getSortOrder() > maxSortOrder + 1) {
						code.setSortOrder(maxSortOrder + 1);
					}
				}
			}

			dao.insertCommonCode(code);
		} else {
			// 수정
			Map<String, Object> parameter = new HashMap<String, Object>();

			parameter.put("code", code.getCode());

			CommonCode oldData = dao.getCode(parameter);
			Integer oldSort = oldData.getSortOrder();

			if (oldData.getParent() != null && StringUtils.isNotEmpty(oldData.getParent().getCode())) {
				if ("N".equals(code.getUseYN())) {
					// 사용 안함으로 전환할 경우
					code.setSortOrder(getCommonCodeCount(code.getParent().getCode()));

					Integer maxCount = getCommonCodeCount(code.getParent().getCode());

					dao.updateNextSortOrder(oldSort, maxCount, oldData.getParent().getCode(), code.getModUser());
					code.setSortOrder(maxCount);
				} else {
					if (oldSort < code.getSortOrder()) {
						dao.updateNextSortOrder(oldSort,
						                        code.getSortOrder(),
						                        oldData.getParent().getCode(),
						                        code.getModUser());
					} else if (oldSort > code.getSortOrder()) {
						dao.updateBeforeSortOrder(code.getSortOrder(),
						                          oldSort,
						                          oldData.getParent().getCode(),
						                          code.getModUser());
					}
				}
			}

			if ("N".equals(code.getUseYN())) {
				checkCodeList(false);

				List<Map<String, Object>> getCurrentChildList = findChildCodeList(code.getCode(), null);

				updateChildUseYN(getCurrentChildList);
			}

			dao.updateCommonCode(code);
		}
	}

	@SuppressWarnings("unchecked")
	private void updateChildUseYN(List<Map<String, Object>> childList) {
		dao.updateUseYNAllChild("N", (String)childList.get(0).get("parentCode"));

		for (Map<String, Object> child: childList) {
			if (CollectionUtils.isNotEmpty((Collection)child.get("childList"))) {
				updateChildUseYN((List<Map<String, Object>>)child.get("childList"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> findChildCodeList(String code, List<Map<String, Object>> childList) {
		List<Map<String, Object>> resultList = null;

		if (childList == null) {
			for (Map<String, Object> currentCode: codeList) {
				if (code.equals(currentCode.get("code"))) {
					resultList = (List<Map<String, Object>>)currentCode.get("childList");
				} else {
					List<Map<String, Object>> currentChildList = (List<Map<String, Object>>)currentCode.get("childList");

					if (CollectionUtils.isNotEmpty(currentChildList)) {
						findChildCodeList(code, currentChildList);
					}
				}
			}
		} else {
			for (Map<String, Object> child: childList) {
				if (code.equals(child.get("code"))) {
					resultList = (List<Map<String, Object>>)child.get("childList");
				} else if (CollectionUtils.isNotEmpty((Collection)child.get("childList"))) {
					findChildCodeList(code, (List<Map<String, Object>>)child.get("childList"));
				}
			}
		}

		return resultList;
	}

	public List<Map<String, Object>> getCodeList() {
		checkCodeList(true);

		return codeList;
	}

	public List<CommonCode> getAllCodeList(Map<String, Object> parameter) {
		return dao.getCodeList(parameter);
	}

	public List<Map<String, Object>> sortCodeList(List<CommonCode> commonCodeList) {
		List<Map<String, Object>> codeList = new ArrayList<Map<String, Object>>();

		for (CommonCode code : commonCodeList) {
			Map<String, Object> codeMap = new HashMap<String, Object>();

			codeMap.put("code", code.getCode());
			codeMap.put("name", code.getName());
			codeMap.put("useYN", code.getUseYN());
			codeMap.put("regManagerID", code.getRegUser().getSequence());
			codeMap.put("modManagerID", (code.getModUser() != null)? code.getModUser().getSequence(): "");
			codeMap.put("description", code.getDescription());
			codeMap.put("level", code.getLevel());
			codeMap.put("sortOrder", code.getSortOrder());
			codeMap.put("alias", code.getAlias());

			if (code.getParent() != null && StringUtils.isNotEmpty(code.getParent().getCode())) {
				codeMap.put("parentCode", code.getParent().getCode());
				codeMap.put("parentCodeName", code.getParent().getName());

				findParentCode(codeList, codeMap);
			} else {
				codeList.add(codeMap);
			}
		}

		return codeList;
	}

	@SuppressWarnings("unchecked")
	public void findParentCode(List<Map<String, Object>> codeList, Map<String, Object> codeMap) {
		for (Map<String, Object> code : codeList) {
			if (code.get("childList") != null) {
				findParentCode((List<Map<String, Object>>)code.get("childList"), codeMap);
			}

			if (code.get("code").equals(codeMap.get("parentCode"))) {
				List<Map<String, Object>> childList = (List<Map<String, Object>>)code.get("childList");

				if (childList == null) {
					childList = new ArrayList<Map<String, Object>>();
				}

				childList.add(codeMap);

				code.put("childList", childList);

				break;
			}
		}
	}

	/**
	 * 코드 생성
	 * <p/>
	 * {@code System.currentTimeMillis()} + 무작위 3자리 문자
	 *
	 * @return 코드
	 */
	private String makeCode() {
		return String.valueOf(System.currentTimeMillis()) + NGStringUtil.makeRandomString(3);
	}

	private Integer getCommonCodeCount(String code) {
		return dao.getCommonCodeCount(code);
	}

	private void checkCodeList(boolean isForceSelect) {
		// FIXME: isForceSelect 제거 및 cache 처리 할 것.
		if (codeList == null || isForceSelect) {
			codeList = sortCodeList(getAllCodeList(new HashMap<String, Object>()));
		}
	}

}
