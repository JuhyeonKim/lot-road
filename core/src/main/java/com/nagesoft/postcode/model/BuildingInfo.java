package com.nagesoft.postcode.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 *
 * Building Info
 * Created by Kimjuhyeon on 15. 5. 19..
 */
@Log4j
public class BuildingInfo {

    /**
     * 건물 관리번호 일련번호
     */
    @Setter
    @Getter
    private String buildingSeq;

    /**
     * 지번 일련번호
     */
    @Setter
    @Getter
    private Integer lotSeq;

    /**
     * 법정동 코드
     */
    @Setter
    @Getter
    private String legalDongCode;

    /**
     * 시도 이름
     */
    @Setter
    @Getter
    private String sidoName;

    /**
     * 시군구 이름
     */
    @Setter
    @Getter
    private String sigunguName;

    /**
     * 법정동 이름
     */
    @Setter
    @Getter
    private String legalDongName;

    /**
     * 법정 리 이름
     */
    @Setter
    @Getter
    private String legalRiName;

    /**
     * 산 여부
     * 0: 대지
     * 1: 산
     */
    @Setter
    @Getter
    private String sanYN;

    /**
     * 지번 본번(번지)
     */
    @Setter
    @Getter
    private Integer lotBasicNumber;

    /**
     * 지번 부번(호)
     */
    @Setter
    @Getter
    private Integer lotPartNumber;


    /**
     * 도로명 코드
     */
    @Setter
    @Getter
    private String roadCode;

    /**
     * 도로명 이름
     */
    @Setter
    @Getter
    private String roadName;

    /**
     * 지하여부
     * 0: 지상
     * 1 : 지하
     * 2: 공중
     */
    @Setter
    @Getter
    private String underYN;

    /**
     * 건물 본번
     */
    @Setter
    @Getter
    private Integer buildingBasicNumber;

    /**
     * 건물 부번
     */
    @Setter
    @Getter
    private Integer buildingPartNumber;

    /**
     * 우편번호
     */
    @Setter
    @Getter
    private String postCode;

    /**
     * 시군구용 건물명
     */
    @Setter
    @Getter
    private String sigunguBuildingName;

    /**
     * 공동주택 여부
     * 0 : 비공동주택
     * 1 : 공동주택
     */
    @Setter
    @Getter
    private String jointYN;

    /**
     * 관련지번 여부
     */
    @Setter
    @Getter
    private String refLotYn;

    /**
     * 내보낼 때 주소 사이 구분자 기본 공백
     */
    @Setter
    @Getter
    private String OUTPUT_DELIMITER=" ";

    /**
     * 읽을 때 주소 사이 구분자 기본 공백
     */
    @Setter
    @Getter
    private String INPUT_DELIMITER="\\|";

    /**
     * 정해진 형식에 맞게 주소 조회
     * - 법정동만 존재하고 법정 리가 존재 하지 않을 경우<br />
     * - 공동주택일 경우<br />
     * - 법정동, 법정리가 존재할 경우<br />
     * @return
     */
    public String getAddress() {

        StringBuffer sb = new StringBuffer();

        String description = null;

        sb.append(this.sidoName);
        sb.append(this.OUTPUT_DELIMITER);
        sb.append(this.sigunguName);

        if (this.legalRiName != null && !this.legalRiName.equals("")) {
            sb.append(this.OUTPUT_DELIMITER);
            sb.append(this.legalDongName);
            sb.append(this.OUTPUT_DELIMITER);
            sb.append(this.legalRiName);

        }

        sb.append(this.OUTPUT_DELIMITER);
        sb.append(this.roadName);

        if(this.buildingBasicNumber != null && this.buildingBasicNumber != 0){
            sb.append(this.OUTPUT_DELIMITER);
            sb.append(this.buildingBasicNumber);

            if(this.buildingPartNumber != null && this.buildingPartNumber != 0){
                sb.append("-");
                sb.append(this.buildingPartNumber);
            }

        }

        if(this.legalDongName != null && ! this.legalDongName.equals("")){
            if(this.jointYN != null && ! this.jointYN.equals("1")){
                description = (this.legalDongName + ", " + this.sigunguBuildingName);
            }else{
                description = this.legalDongName;
            }
        }

        sb.append("(");
        sb.append(description);
        sb.append(")");

        return sb.toString();
    }


    public void parseRefLot(String source) {

        String[] sourceArr = source.split(INPUT_DELIMITER);

        // 법정동코드
        this.legalDongCode = sourceArr[0];
        // 시도명
        this.sidoName = sourceArr[1];
        // 시군구명
        this.sigunguName = sourceArr[2];
        // 법정읍면동명
        this.legalDongName = sourceArr[3];
        //법정리명
        this.legalRiName = sourceArr[4];
        // 산여부
        this.sanYN = sourceArr[5];
        // 지번본번(번지)
        this.lotBasicNumber = Integer.parseInt(sourceArr[6]);
        // 지번부번(호)
        this.lotPartNumber = Integer.parseInt(sourceArr[7]);
        // 도로명코드
        this.roadCode = sourceArr[8];
        // 지하 여부
        this.underYN = sourceArr[9];
        // 건물 본번
        this.buildingBasicNumber = Integer.parseInt(sourceArr[10]);
        // 건물 부번
        this.buildingPartNumber = Integer.parseInt(sourceArr[11]);
        // 지번일련번호
        this.lotSeq = Integer.parseInt(sourceArr[12]);

    }
    public void parseBuildingInfo(String source) {

        String[] sourceArr = source.split(INPUT_DELIMITER);

        // 법정동코드
        this.legalDongCode = sourceArr[0];
        // 시도명
        this.sidoName = sourceArr[1];
        // 시군구명
        this.sigunguName = sourceArr[2];
        // 법정읍면동명
        this.legalDongName = sourceArr[3];
        //법정리명
        this.legalRiName = sourceArr[4];
        // 산여부
        this.sanYN = sourceArr[5];
        // 지번본번(번지)
        this.lotBasicNumber = Integer.parseInt(sourceArr[6]);
        // 지번부번(호)
        this.lotPartNumber = Integer.parseInt(sourceArr[7]);
        // 도로명코드
        this.roadCode = sourceArr[8];
        // 도로명
        this.roadName = sourceArr[9];
        // 지하 여부
        this.underYN = sourceArr[10];
        // 건물 본번
        this.buildingBasicNumber = Integer.parseInt(sourceArr[11]);
        // 건물 부번
        this.buildingPartNumber = Integer.parseInt(sourceArr[12]);
        // 건물관리번호
        this.buildingSeq = sourceArr[15];
        // 우편번호
        this.postCode = sourceArr[19];
        // 시군구용 건물명
        this.sigunguBuildingName = sourceArr[25];
        // 공동주택여부
        this.jointYN = sourceArr[26];

    }


    @Override
    public String toString() {
        return "BuildingInfo{" +
                "buildingSeq='" + buildingSeq + '\'' +
                ", lotSeq=" + lotSeq +
                ", legalDongCode='" + legalDongCode + '\'' +
                ", sidoName='" + sidoName + '\'' +
                ", sigunguName='" + sigunguName + '\'' +
                ", legalDongName='" + legalDongName + '\'' +
                ", legalRiName='" + legalRiName + '\'' +
                ", sanYN='" + sanYN + '\'' +
                ", lotBasicNumber=" + lotBasicNumber +
                ", lotPartNumber=" + lotPartNumber +
                ", roadCode='" + roadCode + '\'' +
                ", roadName='" + roadName + '\'' +
                ", underYN='" + underYN + '\'' +
                ", buildingBasicNumber=" + buildingBasicNumber +
                ", buildingPartNumber=" + buildingPartNumber +
                ", postCode='" + postCode + '\'' +
                ", sigunguBuildingName='" + sigunguBuildingName + '\'' +
                ", jointYN='" + jointYN + '\'' +
                ", refLotYn='" + refLotYn + '\'' +
                ", OUTPUT_DELIMITER='" + OUTPUT_DELIMITER + '\'' +
                ", INPUT_DELIMITER='" + INPUT_DELIMITER + '\'' +
                '}';
    }
}
