package com.listeners;

import com.dao.DBSetupDao;
import com.dbconnection.DBConnection;
import com.dbparser.TableParser;
import com.exception.DBOperationException;
import com.logger.AccessLogger;
import com.logger.ApplicationLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class ListenersConf {


    public static void configureFilePath(String filePath) throws IOException, DBOperationException {

        Properties prop = new Properties();
        InputStream input = Files.newInputStream(Paths.get(filePath));
        prop.load(input);
        AccessLogger.configureAccessLogger(prop.getProperty("AccessLog"));
        ApplicationLogger.configureApplicationLogger(prop.getProperty("ApplicationLog"));

        configureDB(prop.getProperty("DBfilePath"));
        List<String> createQuery = TableParser.tableParser(prop.getProperty("DBschemaPath"));
        DBSetupDao.createTables(createQuery);
        DBSetupDao.addAdminUser();
   }

    public static void configureDB(String filePath) throws IOException {
        HikariConfig hikariConfig = new HikariConfig(filePath);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        DBConnection.setDataSource(hikariDataSource);

    }


}
