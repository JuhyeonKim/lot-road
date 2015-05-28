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

    public List<BuildingInfo> getList(Map<String, Object> param) {

        return dao.getList(param);
    }
}
