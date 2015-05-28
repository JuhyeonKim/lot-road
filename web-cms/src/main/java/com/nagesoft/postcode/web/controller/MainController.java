/*
 * @(#)MainController.java  2013. 12. 18
 *
 * Copyright(c) 2013 NageSoft Corp.
 */

package com.nagesoft.postcode.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * MainController
 * <p/>
 * 사이트 메인 페이지
 *
 * @author JoonHo Son
 */
@Controller(value = "mainController")
public class MainController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main() {
		return "redirect:/main";
	}

	@Deprecated
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String mainMain() {
		return "/main";
	}
}
