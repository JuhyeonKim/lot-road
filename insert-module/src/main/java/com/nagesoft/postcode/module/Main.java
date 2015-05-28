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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kimjuhyeon on 15. 5. 19..
 */
@Log4j
@Component(value="main")
public class Main {

    private static Connection connection=null;
    private static PreparedStatement pstmt = null;
    private static String fileEncoding=null, buildingInfoPath = null,refLotPath=null;

    public static void main(String[] args) throws IOException {

        Properties prop = EnvProperties.getInstance().getProp();
        fileEncoding = prop.getProperty("data.fileEncoding");
        buildingInfoPath = prop.getProperty("data.buildingInfoPath");
        refLotPath = prop.getProperty("data.refLotPath");

        Log4jConfigurer.initLogging("classpath:config/log4j/log4j.xml");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        long startTime = System.currentTimeMillis();

        int result = insertBuildingInfo();

        long endTime = System.currentTimeMillis();

        long different = endTime - startTime;
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        log.info("------------------------------------------------------------------------");
        log.info("start  : " + format.format(new Date(startTime)));
        log.info("end    : " + format.format(new Date(endTime)));
        log.info("result : " + result);
//        System.out.printf("%d days, %d hours, %d minutes, %d seconds%n",
//                elapsedDays,
//                elapsedHours,
//                elapsedMinutes,
//                elapsedSeconds);
        log.info("------------------------------------------------------------------------");

    }

    public static String getInsertQuery(){

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

        File f = new File(buildingInfoPath+"/BD_SEJONG.txt");
//        File n_f = new File("/Users/Kimjuhyeon/storage/Data/project_data/BD_JEJU_cp.txt");

        int result = 0;

        if (f.isFile()) {

            result = doInsert(f);

        }else if(f.isDirectory()){

            File[] fArr = f.listFiles();
            for(File tmpFile : fArr){
                result += doInsert(tmpFile);
            }
        }

        return result;

    }

    private static int doInsert(File f) throws IOException {

        String line;
        int rows = 0;

        try {

            connection = getConnection();

            pstmt = connection.prepareStatement(getInsertQuery());

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
