package ru.javaSchoolProject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.javaSchoolProject.dao.ContractDao;
import ru.javaSchoolProject.dao.TariffDao;
import ru.javaSchoolProject.dto.ContractDto;
import ru.javaSchoolProject.dto.ContractIdAndNumberDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ContractServiceTest {

    @Mock
    private ContractDao contractDao;

    @InjectMocks
    private ContractService contractService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Sign invalid contract")
    public void signInvalidContractTest(){
        assertEquals("Invalid contract data(wrong phone number or user/tariff id)", contractService.signContract(new ContractDto("1","1","877",null)).getMessage());
    }

    @Test
    @DisplayName("Get invalid Contract")
    public void getInvalidContractTest(){
        assertNull(contractService.getContract(new ContractIdAndNumberDto(null,"1")).getId());
    }

    @Test
    @DisplayName("Get ids and numbers of invalid user")
    public void getContractIdsAndNumbersOfInvalidUserTest(){
        assertTrue(contractService.getContractIdsAndNumbers("").isEmpty());
    }

}
