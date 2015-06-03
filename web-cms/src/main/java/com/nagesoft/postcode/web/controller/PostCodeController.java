package com.nagesoft.postcode.web.controller;

import com.nagesoft.postcode.core.common.BaseController;
import com.nagesoft.postcode.manager.PostManager;
import com.nagesoft.postcode.model.BuildingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Kimjuhyeon on 15. 6. 3..
 * 우편번호 관련 컨트롤러
 *
 */
@Controller
@RequestMapping("/postcode")
public class PostCodeController extends BaseController {

    @Autowired
    PostManager postManager;

    /**
     * 도로명주소 검색
     *
     * @param request HttpServletRequest
     *                검색 조건
     * @return 검색된 주소 결과
     */
    @ResponseBody
    @RequestMapping(value = "/listRoad", method = RequestMethod.GET)
    public List<BuildingInfo> listRoad(HttpServletRequest request) {
            List<BuildingInfo> result = null;

        Map<String, Object> param = getQueryMap(request);
        param.put("SKIP_PAGING",true);

        result = postManager.listRoadAddr(param);

        return result;
    }
    /**
     * 도로명주소 검색
     *
     * @param request HttpServletRequest
     *                검색 조건
     * @return 검색된 주소 결과
     */
    @ResponseBody
    @RequestMapping(value = "/listLot", method = RequestMethod.GET)
    public List<BuildingInfo> listLot(HttpServletRequest request) {
            List<BuildingInfo> result = null;

        Map<String, Object> param = getQueryMap(request);
        param.put("SKIP_PAGING",true);

        result = postManager.listRoadAddr(param);

        return result;
    }

    /**
     * 시도 목록 조회
     * @param request
     * @return 시도 목록
     */
    @ResponseBody
    @RequestMapping(value="/listSido",method=RequestMethod.GET)
    public List<String> listSido(HttpServletRequest request){
        List<String> result = null;

        Map<String, Object> param = getQueryMap(request);
        param.put("SKIP_PAGING",true);

        result = postManager.listSido(param);

        return result;
    }

    /**
     * 시 군 구 목록 조회
     * 검색조건은 q.sidoName
     * @param request
     * @return 검색된 시군구 목록
     */
    @ResponseBody
    @RequestMapping(value="/listSigungu",method=RequestMethod.GET)
    public List<Map<String,String>> listSigugun(HttpServletRequest request){
        List<Map<String,String>> result = null;

        Map<String, Object> param = getQueryMap(request);
        param.put("SKIP_PAGING",true);

        result = postManager.listSigungu(param);

        return result;
    }


}
