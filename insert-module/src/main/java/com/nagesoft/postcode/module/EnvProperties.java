package com.nagesoft.postcode.module;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Kimjuhyeon on 15. 5. 28..
 */
public class EnvProperties {

    private static EnvProperties instance=null;

    private Properties prop =null;

    public static EnvProperties getInstance(){
        if(instance == null){
            instance = new EnvProperties();
        }

        return instance;
    }

    private EnvProperties(){

    }

    public Properties getProp() throws IOException {
        if (prop == null) {
            InputStream is = getClass().getResourceAsStream("/config/app.properties");
            prop = new Properties();
            prop.load(is);
        }

        return prop;
    }

}
