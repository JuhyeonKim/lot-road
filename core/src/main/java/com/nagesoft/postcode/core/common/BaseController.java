/*
 * @(#)BaseController.java  2013. 12. 18
 *
 * Copyright(c) 2013 NageSoft Corp.
 */

package com.nagesoft.postcode.core.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nagesoft.postcode.core.model.LabelValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.nagesoft.postcode.core.Constants;
import com.nagesoft.core.model.NGAbstractUser;
import com.nagesoft.core.service.NGUserDetailService;


/**
 * AdminBaseController
 * <p/>
 * Base controller for administrator.
 *
 * @author Juhyeon Kim
 * @version 1.0 2014-07-29
 * @since 1.0
 */
//@Component(value = "baseController")
public class BaseController extends AbstractController {

	/**
	 * 사용자 정보 manager
	 * <p/>
	 * 관리자 및 프런트(사용자) 모듈에서 각각 NGUserDetailService를 구현한 객체가 이를 대체한다.
	 */
	@Autowired()
	protected NGUserDetailService manager;

	/**
	 * JSON result key
	 */
	protected static final String JSON_RESULT_KEY = "result";

	/**
	 * JSON result message
	 */
	protected static final String JSON_MESSAGE_KEY = "message";

	/**
	 * 메세지 저장용 키
	 */
	private static final String MESSAGE_KEY = "savedMessage";


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);

		binder.registerCustomEditor(Date.class, editor);
	}

	/**
	 * 화면 출력용 메세지 저장
	 *
	 * @param request HttpServletRequest
	 * @param message 출력 메세지
	 */
	@SuppressWarnings("unchecked")
	public void saveMessage(HttpServletRequest request, String message) {
		List<String> messageList = (List<String>)request.getSession().getAttribute(MESSAGE_KEY);

		if (messageList == null) {
			messageList = new ArrayList<String>();
		}

		messageList.add(message);
		request.getSession().setAttribute(MESSAGE_KEY, messageList);
	}

	/**
	 * Gets the query map.
	 *
	 * @return the query map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getQueryMap(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, Object> condMap = new HashMap<String, Object>();
		Map<String, LabelValue> dynamicMap = new HashMap<String, LabelValue>();

		for (String key : parameterMap.keySet()) {
			if (key.startsWith(Constants.CONDITION_PARAM_KEY)) {
				String[] values = parameterMap.get(key);
				// remove 'q.' prefix
				String conditionKey = key
					.substring(Constants.CONDITION_PARAM_KEY.length());

				// cond.dynamic.key.1 or cond.dynamic.value.1
				if (StringUtils.startsWith(conditionKey, "dynamic")) {
					String[] parsedData = StringUtils.split(conditionKey, '.');
					if (StringUtils.equals(parsedData[1], "key")) {
						LabelValue lv = dynamicMap.get(parsedData[2]);
						if (lv == null) {
							lv = new LabelValue();
							dynamicMap.put(parsedData[2], lv);
						}

						lv.setLabel(values[0]);
					} else {
						LabelValue lv = dynamicMap.get(parsedData[2]);
						if (lv == null) {
							lv = new LabelValue();
							dynamicMap.put(parsedData[2], lv);
						}

						lv.setValue(values[0]);
					}
				} else {
					if (StringUtils.isNotBlank(values[0])) {
						if (values.length > 1) {
							condMap.put(conditionKey, values);
						} else {
							condMap.put(conditionKey, values[0]);
						}
					}
				}
			}
		}

		if (!dynamicMap.isEmpty()) {
			for (LabelValue lv : dynamicMap.values()) {
				if (StringUtils.isNotBlank(lv.getValue())) {
					condMap.put(lv.getLabel(), lv.getValue());
				}
			}
		}

		if (condMap.isEmpty()) {
			condMap.put("EMPTYCOND", "EMPTYCOND");
		}

		if (parameterMap.containsKey(Constants.ORDER_KEY)
			&& parameterMap.containsKey(Constants.ORDER_VALUE)) {
			condMap.put(parameterMap.get(Constants.ORDER_KEY)[0],
			            parameterMap.get(Constants.ORDER_VALUE)[0]);
		} else {
			condMap.put("EMPTYORD", "EMPTYORD");
		}

		return condMap;
	}



	public NGAbstractUser getCurrentUser(boolean forceReloadInCurrentSession) {
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth == null) {
			return null;
		}
		Object principal = auth.getPrincipal();
		NGAbstractUser user;

		// the Acegi assigned principal is typically an instance of User
		// except for certain cases where the user programatically updates
		// the authentication token (eg users' password) by calling code the
		// code below in which case the principal will be an instance of
		// String for the rest of the duration of the current
		// request-response.

		if (UserDetails.class.isAssignableFrom(principal.getClass())) {
			if (forceReloadInCurrentSession) {
				user = manager.getUser(((UserDetails)principal).getUsername());
			} else {
				user = (NGAbstractUser)principal;
			}
		} else if (principal instanceof String) {
			if (((String)principal).contains("anonymous")) {
				user = null;
			} else {
				user = manager.getUser((String)principal);
			}
		} else {
			throw new IllegalArgumentException("Unrecognized principal type : " + principal);
		}

		return user;
	}

	public NGAbstractUser getCurrentUser() {
		return getCurrentUser(false);
	}

}
