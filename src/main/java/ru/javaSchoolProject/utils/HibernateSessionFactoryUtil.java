package ru.javaSchoolProject.utils;


import ru.javaSchoolProject.models.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;


public class HibernateSessionFactoryUtil {
        private static SessionFactory sessionFactory;

        private HibernateSessionFactoryUtil() {}

        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                try {
//                    String confFile = "/Users/artur/Desktop/JavaSchoolProject/BackJavaSchool/src/main/resources/hibernate.cfg.xml";
//                    File f = new File(confFile);
                    Configuration configuration = new Configuration().configure();
//                    configuration.addAnnotatedClass(User.class);
                    configuration.addAnnotatedClass(User.class);
                    configuration.addAnnotatedClass(Contract.class);
                    configuration.addAnnotatedClass(ContractOptions.class);
                    configuration.addAnnotatedClass(Options.class);
                    configuration.addAnnotatedClass(Tariff.class);
                    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                    sessionFactory = configuration.buildSessionFactory(builder.build());
//                    sessionFactory = configuration.buildSessionFactory();



                } catch (Exception e) {
                    System.out.println("Exception! from HibernateSessionFactory: " + e.getMessage());
                }
            }
            return sessionFactory;
        }
    }
