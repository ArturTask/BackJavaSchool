package ru.javaSchoolProject.dao;

//import org.hibernate.Session;
import org.hibernate.*;
import org.springframework.stereotype.Repository;
import ru.javaSchoolProject.models.Options;
import ru.javaSchoolProject.utils.HibernateSessionFactoryUtil;

@Repository
public class OptionsDao {

    public Options getOptionById(int id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        Options option = session.get(Options.class,id);
        tr.commit();
        session.close();
        return option;
    }

    public boolean deleteOption(Options option){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.delete(option);
        tr.commit();
        session.close();
        return true;
    }

    public boolean deleteOptionById(int id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        Options option = session.get(Options.class,id);
        session.delete(option);
        tr.commit();
        session.close();
        return true;
    }

}
