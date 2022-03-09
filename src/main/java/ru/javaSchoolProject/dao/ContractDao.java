package ru.javaSchoolProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.javaSchoolProject.dto.ContractIdAndNumberDto;
import ru.javaSchoolProject.models.Contract;
import ru.javaSchoolProject.models.User;
import ru.javaSchoolProject.utils.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;

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

    public Contract getContractById(int id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Contract contract = session.get(Contract.class,id);
        tx.commit();
        session.close();
        return contract;
    }

    public List<Contract> getContractsOfUser(int id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Contract> contracts = (List<Contract>) session.createQuery("From Contract as contract where contract.user.id ='" + id + "'").list();
        tx.commit();
        session.close();
        if(!contracts.isEmpty())
        {
            return contracts;
        }
        else {
            return null;
        }
    }

    public boolean deleteContract(int contractId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Contract contract = session.get(Contract.class,contractId);
        session.delete(contract);
        tx.commit();
        session.close();
        return true;
    }

    public List<Contract> getAllContracts(){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Contract> contracts = (List<Contract>) session.createQuery("From Contract ").list();
        tx.commit();
        session.close();
        if(!contracts.isEmpty())
        {
            return contracts;
        }
        else {
            return null;
        }
    }

}
