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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kimjuhyeon on 15. 5. 19..
 */
@Log4j
@Component(value="main")
public class Main {

    private static ClassPathXmlApplicationContext context;

    @Autowired
    private PostManager postManager;


    public static void main(String[] args) throws IOException {
        String[] configLocations = new String[]{"classpath*:config/spring/*.xml"};

        Log4jConfigurer.initLogging("classpath:config/log4j/log4j.xml");

        context = new ClassPathXmlApplicationContext(configLocations);
        final Main main = (Main)context.getBean("main");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        assert main != null;

        long startTime = System.currentTimeMillis();

        int result = main.insert();


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

    public int insert() throws IOException {

        File f = new File("/Users/Kimjuhyeon/storage/Data/project_data/BD_SEJONG.txt");
//        File n_f = new File("/Users/Kimjuhyeon/storage/Data/project_data/BD_JEJU_cp.txt");


        FileInputStream fis = new FileInputStream(f);;
//        FileReader fr = new FileReader(f);

        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"EUC-KR"));

        String line;


        log.info("Begin Insert!");
        int rows = 0;


//        line = br.readLine();
//
//        log.debug(line);

        while((line = br.readLine()) != null){
//            log.debug(line);
            BuildingInfo buildingInfo = new BuildingInfo();
            buildingInfo.parseBuildingInfo(line);
            postManager.insertBuildingInfo(buildingInfo);
//            log.debug(buildingInfo.toString());
            rows++;

        }



        return rows;


    }

    public List<BuildingInfo> getList(Map<String, Object> param) {

        List<BuildingInfo> result = postManager.getList(param);

        return result;
    }

}
