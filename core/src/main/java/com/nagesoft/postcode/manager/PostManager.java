package com.nagesoft.postcode.manager;

import com.nagesoft.postcode.dao.PostDAO;
import com.nagesoft.postcode.model.BuildingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Kimjuhyeon on 15. 5. 27..
 */
@Service
public class PostManager {


    @Autowired
    private PostDAO dao;

    /**
     * Insert Building Info.
     * @param buildingInfo Information to be inserted.
     */
    public void insertBuildingInfo(BuildingInfo buildingInfo){
        dao.insertBuildingInfo(buildingInfo);

    }

    /**
     * Insert Ref Lot Info.
     * @param buildingInfo Reference Lot Information.
     */
    public void insertRefLot(BuildingInfo buildingInfo){

        dao.insertRefLot(buildingInfo);

    }

    /**
     * List road Address Info
     * @param param Condition
     * @return Data lists.
     */
    public List<BuildingInfo> listRoadAddr(Map<String, Object> param) {

        return dao.listRoadAddr(param);
    }


    /**
     * List Lot Address Info
     * @param param Condition
     * @return Data lists.
     */
    public List<BuildingInfo> listLotAddr(Map<String, Object> param) {

        return dao.listLotAddr(param);
    }


    /**
     * List SI-DO info.
     * @param param Condition
     * @return SI-DO list.
     */
    public List<String> listSido(Map<String, Object> param) {

        return dao.listSido(param);
    }

    /**
     * List SI-GUN-GU Info.
     * @param param Condition
     *              - sidoName
     * @return SI-GUN-GU list.
     */
    public List<Map<String, String>> listSigungu(Map<String, Object> param) {

        return dao.listSigungu(param);
    }

}
