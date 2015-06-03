package com.nagesoft.postcode.dao;

import com.nagesoft.core.mybatis.NGPageable;
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

    /**
     * Count Road Address rows number.
     * @param param Condition
     * @return row number
     */
    int countRoadAddr(Map<String, Object> param);

    /**
     * List road Address Info
     * @param param Condition
     * @return Data lists.
     */
    @NGPageable(countMapperID = "countRoadAddr")
    List<BuildingInfo> listRoadAddr(Map<String, Object> param);


    /**
     * Count Lot Address rows number.
     * @param param Condition
     * @return row number
     */
    int countLotAddr(Map<String, Object> param);

    /**
     * List Lot Address Info
     * @param param Condition
     * @return Data lists.
     */
    @NGPageable(countMapperID = "countLogAddr")
    List<BuildingInfo> listLotAddr(Map<String, Object> param);

}
