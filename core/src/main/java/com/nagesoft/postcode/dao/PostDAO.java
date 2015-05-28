package com.nagesoft.postcode.dao;

import com.nagesoft.postcode.model.BuildingInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Kimjuhyeon on 15. 5. 19..
 */
@Repository
public interface PostDAO {

    /**
     * Insert Building Info.
     * @param buildingInfo Information to be inserted.
     */
    void insertBuildingInfo(BuildingInfo buildingInfo);

    /**
     * Insert Ref Lot Info.
     * @param buildingInfo Reference Lot Information.
     */
    void insertRefLot(BuildingInfo buildingInfo);

    List<BuildingInfo> getList(Map<String, Object> param);



}
