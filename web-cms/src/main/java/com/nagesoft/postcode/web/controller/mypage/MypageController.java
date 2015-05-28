package com.nagesoft.postcode.web.controller.mypage;

import com.nagesoft.postcode.core.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Kimjuhyeon on 15. 1. 20..
 */
@Controller
@RequestMapping("/mypage")
public class MypageController extends BaseController{

    /**
     * 정보 수정
     * @return 화면
     */
    @RequestMapping(value="form",method= RequestMethod.GET)
    public String form() {

        return "/mypage/form";
    }

    /**
     * 비밀번호 수정
     * @return 화면
     */
    @RequestMapping(value="/password",method=RequestMethod.GET)
    public String password() {

        return "/mypage/password";
    }
}
