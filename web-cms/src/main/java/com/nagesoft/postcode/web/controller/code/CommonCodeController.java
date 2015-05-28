package com.nagesoft.postcode.web.controller.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nagesoft.postcode.member.model.User;
import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nagesoft.postcode.code.model.CommonCode;
import com.nagesoft.postcode.code.service.CommonCodeManager;
import com.nagesoft.postcode.core.common.BaseController;
import com.nagesoft.core.validator.NGInsert;
import com.nagesoft.core.validator.NGUpdate;

/**
 * CommonCodeController
 * <p/>
 * 공통코드 controller
 *
 * @author Juhyeon Kim
 */
@Log4j
@Controller
@RequestMapping(value = "/code")
public class CommonCodeController extends BaseController {

	@Autowired
	private CommonCodeManager manager;

	/**
	 * 공통코드 관리 메인 화면
	 *
	 * @return 메인 화면
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String main(Model model) {
		CommonCode code = new CommonCode();

		code.setUseYN("N");

		model.addAttribute("code", code);
		return "/code/codeList";
	}

	/**
	 * 공통코드 목로 조회(ajax)
	 *
	 * @return 코드 목록
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map<String, Object> getAllCodeList() {
		Map<String, Object> root = new HashMap<String, Object>();

		try {
			List<Map<String, Object>> codeList = manager.getCodeList();

			root.put("list", codeList);
			root.put(JSON_RESULT_KEY, "y");
		} catch (Exception e) {
			log.error("공통코드 목록 조회중 오류 발생", e);
			root.put(JSON_RESULT_KEY, "n");
			root.put(JSON_MESSAGE_KEY, "서버에서 오류가 발생하였습니다.");
		}

		return root;
	}

	/**
	 * 공통코드 등록 처리
	 *
	 * @param code   코드
	 * @param result BindingResult
	 *
	 * @return 처리 결과
	 */
	@ResponseBody
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public Map<String, Object> insertCommonCode(@Validated(value = {NGInsert.class}) CommonCode code,
	                                            BindingResult result) {
		Map<String, Object> root = new HashMap<String, Object>();

		try {
			if (result.hasErrors()) {
				// TODO: 에러 처리
			}
			code.setRegUser((User)getCurrentUser());
			code.setModUser((User)getCurrentUser());
			manager.saveCommonCode(code);
			root.put(JSON_RESULT_KEY, "y");
		} catch (Exception e) {
			log.error("공통코드 등록 오류 : ", e);
			root.put(JSON_RESULT_KEY, "n");
			root.put(JSON_MESSAGE_KEY, "서버에서 오류가 발생하였습니다.");
		}

		return root;
	}

	/**
	 * 공통코드 변경 처리
	 *
	 * @param code   코드
	 * @param result BindingResult
	 *
	 * @return 처리 결과
	 */
	@ResponseBody
	@RequestMapping(value = "/{code}/form", method = RequestMethod.PUT)
	public Map<String, Object> updateCommonCode(@PathVariable(value = "code") String commonCode,
	                                            @Validated(value = {NGUpdate.class}) CommonCode code,
	                                            BindingResult result) {
		Map<String, Object> root = new HashMap<String, Object>();

		try {
			if (result.hasErrors()) {
				// TODO: 에러 처리
			}

			code.setRegUser((User)getCurrentUser());
			code.setModUser((User)getCurrentUser());
			manager.saveCommonCode(code);
			root.put(JSON_RESULT_KEY, "y");
		} catch (Exception e) {
			log.error("공통코드 수정 오류 : ", e);
			root.put(JSON_RESULT_KEY, "n");
			root.put(JSON_MESSAGE_KEY, "서버에서 오류가 발생하였습니다.");
		}

		return root;
	}

	/**
	 * listChildCode
	 *
	 * 공통코드 하위코드 목록 조회
	 *
	 * @param code   코드
	 *
	 * @return 처리 결과
	 */
	@ResponseBody
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public Map<String, Object> listChildCode(
			@PathVariable("code") String code
	        ) {

		Map<String, Object> root = new HashMap<String, Object>();

		try {

			if(code == null || code.equals("")){
				List<CommonCode> codeList =  new ArrayList<CommonCode>();
				root.put("list", codeList);
				root.put(JSON_RESULT_KEY, "y");

			}else{
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("parentCode", code);
				List<CommonCode> codeList =  manager.getAllCodeList(param);
				root.put("list", codeList);
				root.put(JSON_RESULT_KEY, "y");
			}


		} catch (Exception e) {
			log.error("공통코드 목록 조회중 오류 발생", e);
		}

		return root;
	}

}
