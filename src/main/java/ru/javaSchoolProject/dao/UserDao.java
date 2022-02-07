package ru.javaSchoolProject.dao;

import ru.javaSchoolProject.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.javaSchoolProject.utils.HibernateSessionFactoryUtil;
import java.util.List;

@Repository
public class UserDao {

    public User findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    public User findByLoginAndPassword(String login, String password)
        {
            List<User> users = (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User as user where user.login ='" + login + "'" + " and user.password = '" + password + "'").list();
            if(!users.isEmpty())
            {
                return users.get(0);
            }
            else {
                return null;
            }

        }

    public User findByLogin(String login)
    {
        List<User> users = (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User as user where user.login ='" + login + "'").list();
        if(!users.isEmpty())
        {
            return users.get(0);
        }
        else {
            return null;
        }

    }

    public boolean save(User user) {

        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();){
            Transaction tx1 = session.beginTransaction();
            session.save(user);
            tx1.commit();
            return true;
        }
        catch (ConstraintViolationException e){ // when user with same login exists
            System.out.println("from UserDao - save:"+e.getMessage());
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
        List<User> users = (List<User>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }
}