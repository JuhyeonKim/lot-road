/*
 * @(#)LoginSuccessHandler.java  2014. 09. 26
 *
 * Copyright(c) 2014 NageSoft Corp.
 */

package com.nagesoft.postcode.core;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * LoginSuccessHandler
 *
 * @author JoonHo Son
 * @version 1.0.0 2014-09-26
 * @since 1.0.0
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    Authentication authentication
	                                    ) throws ServletException, IOException {
		
		Cookie cookie = new Cookie("saveId", authentication.getName());
		response.addCookie(cookie);

		super.onAuthenticationSuccess(request, response, authentication);
	}
}
