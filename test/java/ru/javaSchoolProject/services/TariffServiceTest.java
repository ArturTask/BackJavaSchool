package ru.javaSchoolProject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.javaSchoolProject.dao.TariffDao;
import ru.javaSchoolProject.dao.UserDao;
import ru.javaSchoolProject.dto.TariffDto;
import ru.javaSchoolProject.enums.Role;
import ru.javaSchoolProject.models.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class TariffServiceTest {

    @Mock
    private TariffDao tariffDao;

    @InjectMocks
    private TariffService tariffService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Tariff not found")
    public void tariffNotFoundTest(){
        when(tariffDao.findTariffById(1)).thenReturn(null);
        assertNull(tariffService.findTariffById("1").getId());
    }

    @Test
    @DisplayName("Add invalid tariff")
    public void addInvalidTariff(){
        assertEquals("Invalid data from frontend",tariffService.addNewTariff(new TariffDto(null,null,"descr","-10",true,null)).getResponse());
    }

    @Test
    @DisplayName("Update invalid tariff")
    public void updateTariffTest(){
        assertEquals("Invalid Tariff data",tariffService.updateTariff(new TariffDto(null,null,"descr","-10",true,null)).getResponse());
    }

    @Test
    @DisplayName("Delete invalid tariff")
    public void deleteTariffTest(){
        assertEquals("Invalid Tariff data",tariffService.updateTariff(new TariffDto(null,null,"descr","-10",true,null)).getResponse());
    }

}
