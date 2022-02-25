package ru.javaSchoolProject.dao;

import org.springframework.stereotype.Repository;
import ru.javaSchoolProject.models.Tariff;
import ru.javaSchoolProject.utils.HibernateSessionFactoryUtil;

import java.util.List;

@Repository
public class TariffDao {


    public List<Tariff> findAll() {
        List<Tariff> tariffs = (List<Tariff>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Tariff  ").list();
        return tariffs;
    }
}
