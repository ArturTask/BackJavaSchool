package ru.javaSchoolProject.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.javaSchoolProject.controllers.AuthController;
import ru.javaSchoolProject.models.Tariff;
import ru.javaSchoolProject.models.User;
import ru.javaSchoolProject.utils.HibernateSessionFactoryUtil;

import java.util.List;

@Repository
public class TariffDao {

    final static Logger logger = Logger.getLogger(AuthController.class.getName());

    public boolean addTariff(Tariff tariff){
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();){
            Transaction tx1 = session.beginTransaction();
            session.save(tariff);
            tx1.commit();
            logger.info("SAVED tariff \""+tariff.getTitle()+"\"to DB: SUCCESS");
            return true;
        }
        catch (ConstraintViolationException e) { // if I decide to add some constraints to db
            logger.warn("NOT SAVED tariff \"" + tariff.getTitle() + "\"to DB: FAILURE(" + e.getMessage() + ")");
            return false;
        }
    }

    public Tariff findTariffById(int id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Tariff tariff = session.get(Tariff.class, id);
        tx1.commit();
        session.close();
        return tariff;
    }

    public boolean updateTariff(Tariff tariff){
//        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
//            Tariff oldTariff = findTariffById(tariff.getId());
//            delete(oldTariff);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.update(tariff);
            tx1.commit();
            session.close();
            logger.info("UPDATED tariff \""+tariff.getTitle()+"\"to DB: SUCCESS");
            return true;
//        }
//        catch (ConstraintViolationException e){ //because of some constraints in db
//            logger.warn("NOT UPDATED tariff \"" + tariff.getTitle() + "\"to DB: FAILURE(" + e.getMessage() + ")");
//            return false;
//        }
    }

    public void deleteTariffById(Tariff tariff) {
        Session session2 = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx2 = session2.beginTransaction();
        session2.delete(tariff);
        tx2.commit();
        session2.close();
    }

    public List<Tariff> findAll() {
        List<Tariff> tariffs = (List<Tariff>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Tariff  ").list();
        return tariffs;
    }
}
