package com.nagesoft.postcode.web.controller.member;

import com.nagesoft.core.exception.NGDuplicateDataException;
import com.nagesoft.core.validator.NGInsert;
import com.nagesoft.postcode.code.model.CommonCode;
import com.nagesoft.postcode.code.service.CommonCodeManager;
import com.nagesoft.postcode.core.common.BaseController;
import com.nagesoft.postcode.member.model.User;
import com.nagesoft.postcode.member.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kimjuhyeon on 15. 1. 20..
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController{

    /**
     * @see com.nagesoft.postcode.member.service.UserManager
     */
    @Autowired
    private UserManager userManager;

    /**
     * @see com.nagesoft.postcode.code.service.CommonCodeManager
     */
    @Autowired
    private CommonCodeManager commonCodeManager;

    /**
     * 회원 가입 폼
     *
     * @param model Model
     * @return 화면
     */
    @RequestMapping(value = "/join",method= RequestMethod.GET)
    public String joinForm( Model model) {

        User user = new User();
        model.addAttribute("user", user);

        setJoinForm(model);

        return "/member/join";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String doJoin(HttpServletRequest request
            , Model model
            , @Validated(value = {NGInsert.class}) User user
            , BindingResult result) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (result.hasErrors()) {

            return "/member/join";
        }

        try {
            userManager.saveUser(user);
        }catch(NGDuplicateDataException exception){

            saveMessage(request,"입력하신 아이디는 이미 사용중입니다.");
            return "/member/join";

        }


        return "redirect:/login";
    }

    /**
     * 회원 가입 폼 세팅
     * @param model 세팅 할 Model  객체
     */
    private void setJoinForm(Model model) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("parentCode", "14229502731433SP");
        List<CommonCode> groupCodeList = commonCodeManager.getAllCodeList(param);
        model.addAttribute("groupCodeList", groupCodeList);
        
    }
}
