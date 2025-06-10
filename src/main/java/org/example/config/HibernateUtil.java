package org.example.config;

import org.example.enity.Order;
import org.example.enity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;


public class HibernateUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = getConfiguration();

                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(Product.class);


                ServiceRegistry serviceRegistry =
                        new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration
                        .buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                logError(e);
            }
        }
        return sessionFactory;
    }




    private static Configuration getConfiguration(){
        Configuration configuration = new Configuration();
        Properties props = new Properties();
        try{
            props.load(HibernateUtil.class.getResourceAsStream("/app.properties"));
            props.put(Environment.JAKARTA_JDBC_DRIVER, props.getProperty("dbDriver"));
            props.put(Environment.JAKARTA_JDBC_URL, props.getProperty("dbUrl"));
            props.put(Environment.JAKARTA_JDBC_USER, props.getProperty("dbUser"));
            props.put(Environment.JAKARTA_JDBC_PASSWORD, props.getProperty("dbPass"));
            props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            props.put("hibernate.show_sql","true");
            configuration.setProperties(props);
            return configuration;
        } catch(IOException e){
           logError(e);
            throw new RuntimeException(e);
        }
    }

    private static void logError(Exception e){
        logger.error("Error occurred: {}",e.getMessage());
    }
}
