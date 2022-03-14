package ru.javaSchoolProject.dao;

import org.apache.log4j.Logger;
import ru.javaSchoolProject.controllers.AuthController;
import ru.javaSchoolProject.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.javaSchoolProject.utils.HibernateSessionFactoryUtil;
import java.util.List;

@Repository
public class UserDao {

    final static Logger logger = Logger.getLogger(AuthController.class.getName());

    public User findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    public User findByLoginAndPassword(String login, String password)
        {
            List<User> users = (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User as user where user.login ='" + login + "'" + " and user.password = '" + password + "'").list();
            if(!users.isEmpty())
            {
                //logger.info("FOUND user \""+login+"\" in DB by Login and Password: SUCCESS");
                return users.get(0);
            }
            else {
                //logger.info("NOT FOUND user \"" + login + "\" in DB by Login and Password: FAILURE");
                return null;
            }

        }

    public User findByLogin(String login)
    {
        List<User> users = (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User as user where user.login ='" + login + "'").list();
        if(!users.isEmpty())
        {
            //logger.info("FOUND user \""+login+"\" in DB by Login: SUCCESS");
            return users.get(0);
        }
        else {
            //logger.info("NOT FOUND user \"" + login + "\" in DB by Login: FAILURE");
            return null;
        }

    }

    public boolean save(User user) {

        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();){
            Transaction tx1 = session.beginTransaction();
            session.save(user);
            tx1.commit();
            //logger.info("SAVED user \""+user.getLogin()+"\"to DB: SUCCESS");
            return true;
        }
        catch (ConstraintViolationException e){ // when user with same login exists

//            System.out.println("from UserDao - save:"+e.getMessage());
            //logger.info("NOT SAVED user \""+user.getLogin()+"\"to DB: FAILURE("+e.getMessage()+")");
            return false;
        }

    }

    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    public List<User> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<User> users = (List<User>)  session.createQuery("From User").list();
        tx.commit();
        session.close();
        return users;
    }

    public User findByPhoneNumber(Long phoneNumber){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<User> users = (List<User>)  session.createQuery("select user From Contract c where c.phoneNumber="+phoneNumber).list();
        tx.commit();
        session.close();
        if(!users.isEmpty()) {
            return users.get(0);
        }
        else {
            return null;
        }
    }
}