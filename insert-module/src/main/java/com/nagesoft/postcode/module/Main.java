package com.nagesoft.postcode.module;

import com.nagesoft.postcode.manager.PostManager;
import com.nagesoft.postcode.model.BuildingInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Log4jConfigurer;

import java.io.*;
import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Kimjuhyeon on 15. 5. 19..
 */
@Log4j
@Component(value="main")
public class Main {

    private static Connection connection=null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;
    private static String fileEncoding=null, buildingInfoPath = null,refLotPath=null;

    public static void main(String[] args) throws IOException {

        Properties prop = EnvProperties.getInstance().getProp();
        fileEncoding = prop.getProperty("data.fileEncoding");
        buildingInfoPath = prop.getProperty("data.buildingInfoPath");
        refLotPath = prop.getProperty("data.refLotPath");

        Log4jConfigurer.initLogging("classpath:config/log4j/log4j.xml");

        display();

    }

    public static void display() throws IOException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        System.out.println("1. Insert Building Info");
        System.out.println("2. Insert ref-lot info");
        System.out.println("3. EXIT Program");
        System.out.println("Press number!");

        BufferedReader br = new BufferedReader((new InputStreamReader(System.in)));

        String s = br.readLine();


        long startTime = System.currentTimeMillis();

        int result = 0;

        if (s.equals("1")) {
            result=insertBuildingInfo();
        }else if (s.equals("2")) {
            result=insertRefLot();
        }else if (s.equals("3")) {
            System.out.println("It was shut down..");
            return;
        }else{
            display();
        }

        long endTime = System.currentTimeMillis();

        log.info("------------------------------------------------------------------------");
        log.info("start  : " + format.format(new Date(startTime)));
        log.info("end    : " + format.format(new Date(endTime)));
        log.info("result : " + result);
        log.info("------------------------------------------------------------------------");


    }

    /**
     * Build Info 테이블의 데이터 유무 조회 쿼리
     * @return 쿼리
     */
    public static String getQueryForSelectCountBuildInfo(){
      String query = "SELECT COUNT(*) as CNT FROM POST_BD_INFO";

        return query;
    }

    /**
     * Ref Lot 테이블의 데이터 유무 조회 쿼리
     * @return 쿼리
     */
    public static String getQueryForSelectCountRefLot(){
      String query = "SELECT COUNT(*) as CNT FROM POST_REF_LOT";

        return query;
    }

    public static String getInsertQueryForRefLot(){
        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO POST_REF_LOT(");
        sb.append("        ROAD_CD");
        sb.append("        ,UNDER_YN");
        sb.append("        ,BD_BASIC_NUM");
        sb.append("        ,BD_PART_NUM");
        sb.append("        ,LOT_SEQ");
        sb.append("        ,LEGAL_DONG_CD");
        sb.append("        ,SIDO_NM");
        sb.append("        ,SIGUNGU_NM");
        sb.append("        ,LEGAL_DONG_NM");
        sb.append("        ,LEGAL_RI_NM");
        sb.append("        ,SAN_YN");
        sb.append("        ,LOT_BASIC_NUM");
        sb.append("        ,LOT_PART_NUM");
        sb.append(") VALUES (");
        sb.append("        ?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(")");

        return sb.toString();
    }

    public static String getInsertQueryForBDInfo(){

        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO POST_BD_INFO(");
        sb.append("        BD_SEQ");
        sb.append("        ,LEGAL_DONG_CD");
        sb.append("        ,SIDO_NM");
        sb.append("        ,SIGUNGU_NM");
        sb.append("        ,LEGAL_DONG_NM");
        sb.append("        ,LEGAL_RI_NM");
        sb.append("        ,SAN_YN");
        sb.append("        ,LOT_BASIC_NUM");
        sb.append("        ,LOT_PART_NUM");
        sb.append("        ,ROAD_CD");
        sb.append("        ,ROAD_NM");
        sb.append("        ,UNDER_YN");
        sb.append("        ,BD_BASIC_NUM");
        sb.append("        ,BD_PART_NUM");
        sb.append("        ,POST_CD");
        sb.append("        ,SIGUNGU_BD_NM");
        sb.append("        ,JOINT_YN");
        sb.append(") VALUES (");
        sb.append("?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(")");

        return sb.toString();

    }

    public static int insertBuildingInfo() throws IOException {

        int result=0;

        try {
            connection = getConnection();

            pstmt = connection.prepareStatement(getQueryForSelectCountBuildInfo());
            ResultSet rs = pstmt.executeQuery();

            int dataCount = 0;
            while (rs.next()) {
                dataCount = rs.getInt("CNT");
            }

            if(dataCount == 0) {
                File f = new File(buildingInfoPath);
//        File n_f = new File("/Users/Kimjuhyeon/storage/Data/project_data/BD_JEJU_cp.txt");


                if (f.isFile()) {

                    result = doInsertBuildInfo(f);

                } else if (f.isDirectory()) {

                    File[] fArr = f.listFiles();
                    for (File tmpFile : fArr) {
                        result += doInsertBuildInfo(tmpFile);
                    }
                }

            }else{
                System.out.println("Building info already exists..");
                display();
            }
        } catch (SQLException e) {
            log.error("Ocurred Exception at insertRefLot!");
        } catch (ClassNotFoundException e) {
            log.error("Ocurred Exception at insertRefLot!");
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;

                }
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;

                }
                if (connection != null) {
                    connection.close();
                    connection = null;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;

    }

    /**
     * 관련 지번 입력 파일 / 디렉토리 구분
     * @return 입력한 총 개수
     * @throws IOException
     */
    public static int insertRefLot() throws IOException {
        int result = 0;


        try {
            connection = getConnection();

            pstmt = connection.prepareStatement(getQueryForSelectCountRefLot());
            rs = pstmt.executeQuery();

            int dataCount = 0;
            while (rs.next()) {
                dataCount = rs.getInt("CNT");
            }

            if (dataCount == 0) {

                File f = new File(refLotPath);

                if (f.isFile()) {

                    result = doInsertRefLot(f);

                } else if (f.isDirectory()) {

                    File[] fArr = f.listFiles();
                    for (File tmpFile : fArr) {
                        result += doInsertRefLot(tmpFile);
                    }
                }

            }else{
                System.out.println("Reference Lot-number already exists..");
                display();
            }
        } catch (SQLException e) {
            log.error("Occurred Exception at insertRefLot!");
        } catch (ClassNotFoundException e) {
            log.error("Occurred Exception at insertRefLot!");
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;

                }
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;

                }
                if (connection != null) {
                    connection.close();
                    connection = null;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }

    /**
     * RefLot 입력 실행
     * @param f 입력할  파일
     * @return 총 입력 행 수
     * @throws IOException
     */
    private static int doInsertRefLot(File f) throws IOException {

        String line;
        int rows = 0;

        try {

            connection = getConnection();

            pstmt = connection.prepareStatement(getInsertQueryForRefLot());

            connection.setAutoCommit(false);

            log.info("Begin Insert!");


            FileInputStream fis = new FileInputStream(f);
//        FileReader fr = new FileReader(f);

            BufferedReader br = new BufferedReader(new InputStreamReader(fis,fileEncoding));

            while ((line = br.readLine()) != null) {
                BuildingInfo buildingInfo = new BuildingInfo();
                buildingInfo.parseRefLot(line);

                pstmt.setString(1,buildingInfo.getRoadCode());
                pstmt.setString(2,buildingInfo.getUnderYN());
                pstmt.setInt(3, buildingInfo.getBuildingBasicNumber());
                pstmt.setInt(4, buildingInfo.getBuildingPartNumber());
                pstmt.setInt(5, buildingInfo.getLotSeq());
                pstmt.setString(6,buildingInfo.getLegalDongName());
                pstmt.setString(7,buildingInfo.getSidoName());
                pstmt.setString(8,buildingInfo.getSigunguName());
                pstmt.setString(9, buildingInfo.getLegalDongName());
                pstmt.setString(10, buildingInfo.getLegalRiName());
                pstmt.setString(11,buildingInfo.getSanYN());
                pstmt.setInt(12, buildingInfo.getLotBasicNumber());
                pstmt.setInt(13, buildingInfo.getLotPartNumber());

                pstmt.addBatch();
                pstmt.clearParameters();

                rows++;

                if ((rows % 10000) == 0) {
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                    connection.commit();
                    log.info(rows + "Rows Inserted.");
                }
            }

            pstmt.executeBatch();
            connection.commit();



        }catch(Exception e){
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {

                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;

                }
                if (connection != null) {
                    connection.close();
                    connection = null;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rows;

    }

    /**
     * BuildInfo 입력 실행
     * @param f 입력할 파일
     * @return 총 입력한 행 수
     * @throws IOException
     */
    private static int doInsertBuildInfo(File f) throws IOException {

        String line;
        int rows = 0;

        try {

            connection = getConnection();

            pstmt = connection.prepareStatement(getInsertQueryForBDInfo());

            connection.setAutoCommit(false);

            log.info("Begin Insert!");


            FileInputStream fis = new FileInputStream(f);;
//        FileReader fr = new FileReader(f);

            BufferedReader br = new BufferedReader(new InputStreamReader(fis,fileEncoding));

            while ((line = br.readLine()) != null) {
                BuildingInfo buildingInfo = new BuildingInfo();
                buildingInfo.parseBuildingInfo(line);


                pstmt.setString(1, buildingInfo.getBuildingSeq());
                pstmt.setString(2, buildingInfo.getLegalDongCode());
                pstmt.setString(3, buildingInfo.getSidoName());
                pstmt.setString(4, buildingInfo.getSigunguName());
                pstmt.setString(5, buildingInfo.getLegalDongName());
                pstmt.setString(6, buildingInfo.getLegalRiName());
                pstmt.setString(7, buildingInfo.getSanYN());
                pstmt.setInt(8, buildingInfo.getLotBasicNumber());
                pstmt.setInt(9, buildingInfo.getLotPartNumber());
                pstmt.setString(10, buildingInfo.getRoadCode());
                pstmt.setString(11, buildingInfo.getRoadName());
                pstmt.setString(12, buildingInfo.getUnderYN());
                pstmt.setInt(13, buildingInfo.getBuildingBasicNumber());
                pstmt.setInt(14, buildingInfo.getBuildingPartNumber());
                pstmt.setString(15, buildingInfo.getPostCode());
                pstmt.setString(16, buildingInfo.getSigunguBuildingName());
                pstmt.setString(17, buildingInfo.getJointYN());

                pstmt.addBatch();
                pstmt.clearParameters();

                rows++;

                if ((rows % 10000) == 0) {
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                    connection.commit();
                    log.info(rows + "Rows Inserted.");
                }
            }

            pstmt.executeBatch();
            connection.commit();


        }catch(Exception e){
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;

                }
                if (connection != null) {
                    connection.close();
                    connection = null;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rows;

    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        Properties prop = EnvProperties.getInstance().getProp();
        String driver = prop.getProperty("jdbc.driver");
        String url = prop.getProperty("jdbc.url");
        String username = prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");

        if (connection == null) {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

}
