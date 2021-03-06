/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.dal.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import mymoviesassigment.dal.exceptions.daoException;

/**
 *
 * @author nedas
 */
public class DatabaseConnectionDAO {

    private final Properties configProp = new Properties();

    private DatabaseConnectionDAO(){
        //Private constructor to restrict new instances
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("data/loginInfo.properties");
        try {
            configProp.load(in);
        } catch (IOException e) {
            try {
                throw new daoException(e.getMessage());
            } catch (daoException ex) {
                Logger.getLogger(ex.getMessage());
            }
        }
    }

    private static class LazyHolder  {
        private static final DatabaseConnectionDAO INSTANCE = new DatabaseConnectionDAO() ;
    }

    public static DatabaseConnectionDAO getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getProperty(String key) {
        return configProp.getProperty(key);
    }

    public Set<String> getAllPropertyNames() {
        return configProp.stringPropertyNames();
    }

    public boolean containsKey(String key) {
        return configProp.containsKey(key);
    }
}
