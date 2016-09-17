package com.zuhlke.microservices.demo.persisting;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


import javax.sql.DataSource;
import java.io.File;
import java.sql.Driver;

@SpringBootApplication
public class WidgetDbApplication {

    @Value("${db.port}")
    private String dbPort;

    @Value("${db.web.port}")
    private String webPort;

    @Value("${db.base.dir}")
    private String baseDir;

    @Value("${db.url}")
    private String url;

    @Value("${db.driver}")
    private String driver;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WidgetDbApplication.class, args);
    }

    @Bean(name="dbserver")
    @DependsOn("datasource")
    Server dbServer() throws Exception {
        Server dbServer = Server.createTcpServer("-tcpPort" ,dbPort, "-baseDir", baseDir, "-tcpDaemon", "-tcpAllowOthers").start();
        System.out.println("Starting db server at url: " + dbServer.getURL());
        return dbServer;
    }

    @Bean(name="webserver")
    @DependsOn("dbserver")
    Server webServer() throws Exception {
        Server webServer = Server.createWebServer("-webPort", webPort, "-baseDir", baseDir).start();
        System.out.println("Starting web client at url: " + webServer.getURL());
        return webServer;
    }


    @Bean(name="datasource")
    @DependsOn("init")
    public DataSource dataSource() throws ClassNotFoundException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Class.forName(driver).asSubclass(Driver.class));
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name="init")
    public Boolean init() {
        File base = new File(baseDir);
        base.mkdirs();
        File[] baseFiles = base.listFiles();
        for (File f : baseFiles) {
            f.delete();
        }
        return true;
    }


}
