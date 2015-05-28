/*
 * @(#)UsernamePasswordFilter.java  2014. 01. 15
 *
 * Copyright(c) 2014 NageSoft Corp.
 */

package com.nagesoft.postcode.core.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * AuthenticationAddRemoteDataFilter
 * <p/>
 * 인증 처리전 사용자 정보를 추가한다.
 * <p/>
 * 요청 아이피 등
 *
 * @author JoonHo Son
 * @version 1.0 2014-01-15
 * @since 1.0
 */
public class AuthenticationAddRemoteDataFilter extends UsernamePasswordAuthenticationFilter {
	public static final String IP_ADDR = "IP_ADDR";

	@Override
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		Map<String, Object> detailMap = new HashMap<String, Object>();

		detailMap.put(IP_ADDR, request.getRemoteAddr());

		authRequest.setDetails(detailMap);
	}
}
