/*
 * @(#)LocaleChangeIntercepter.java  2014. 01. 07
 *
 * Copyright(c) 2014 NageSoft Corp.
 */

package com.nagesoft.postcode.core.intercepter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * LocaleChangeIntercepter
 * <p/>
 * 사용자의 Locale 정보 변경을 반영한다.
 *
 * @author JoonHo Son
 * @version 1.0 2014-01-07
 * @since 1.0
 * @deprecated 사용하지 않음
 */
@Deprecated
public class LocaleChangeIntercepter extends HandlerInterceptorAdapter {
	/**
	 * Log4j
	 */
	private Logger logger = Logger.getLogger(LocaleChangeIntercepter.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("------------------------------------------------------------------------");
		logger.debug("start localeChange");
		logger.debug("handler : " + handler);

		Enumeration names = request.getParameterNames();
		String defaultLocale = "ko";

		while (names.hasMoreElements()) {
			String name = (String)names.nextElement();

			if (name.equals("locale")) {
				String value = request.getParameter(name);

				// 변경된 locale 정보를 반영한다.
				setLocaleVariables(request, value);

				break;
			}
		}

		if (request.getAttribute("locale") == null) {
			setLocaleVariables(request, defaultLocale);
		}

		logger.debug("end localeChange");
		logger.debug("------------------------------------------------------------------------");

		return true;
	}

	/**
	 * 해당 locale에 따른 정보를 처리한다.
	 *
	 * @param request locale에 따른 정보를 저장할 HttpServletRequest
	 * @param locale  locale
	 */
	private void setLocaleVariables(HttpServletRequest request, String locale) {
		request.setAttribute("locale", locale);
		request.setAttribute("imageRoot", request.getContextPath() + "/images/" + locale);
		request.setAttribute("scriptRoot", request.getContextPath() + "/js/" + locale);
		request.setAttribute("cssRoot", request.getContextPath() + "/css/" + locale);
	}
}
