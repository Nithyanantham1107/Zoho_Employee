package com.listeners;

import com.exception.DBOperationException;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

public class ServerListener implements ServletContextListener {


    @Override
    public final void contextInitialized(ServletContextEvent sce) {


        System.out.println("ServerListener contextInitialized");

        try {
            ListenersConf.configureFilePath("/home/nithya-pt7676/IdeaProjects/employee/meta_data/server.properties");
        } catch (DBOperationException e) {
            System.out.println("Error Occured while creating the Table" + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("ServerListener contextInitialized Error" + e.getMessage());
            throw new RuntimeException(e);
        }


    }

    public final void contextDestroyed(ServletContextEvent sce) {


        System.out.println("ServerListener contextDestroyed");
    }
}
