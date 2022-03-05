package ru.javaSchoolProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.javaSchoolProject.models.Contract;
import ru.javaSchoolProject.models.User;
import ru.javaSchoolProject.utils.HibernateSessionFactoryUtil;

@Repository
public class ContractDao {

    public boolean save(Contract contract) {

        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(contract);
            tx1.commit();
            return true;
        }catch (ConstraintViolationException e){//when new phone number is NOT unique
            return false;
        }
    }

}
