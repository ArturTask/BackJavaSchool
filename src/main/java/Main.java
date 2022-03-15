import ru.javaSchoolProject.dao.ContractDao;
import ru.javaSchoolProject.dao.OptionsDao;
import ru.javaSchoolProject.dao.TariffDao;
import ru.javaSchoolProject.dao.UserDao;
import ru.javaSchoolProject.enums.OptionType;
import ru.javaSchoolProject.enums.Role;
import ru.javaSchoolProject.models.Contract;
import ru.javaSchoolProject.models.Options;
import ru.javaSchoolProject.models.Tariff;
import ru.javaSchoolProject.models.User;
import ru.javaSchoolProject.services.UserService;


import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    private static TariffDao tariffDao = new TariffDao();
    private static OptionsDao optionsDao = new OptionsDao();
    private static UserDao userDao = new UserDao();
    private static ContractDao contractDao = new ContractDao();


    public static void main(String[] args) {
        /* to delete tariff I need to find it first
        */
//        Tariff oldTariff = tariffDao.findTariffById(13);
//        tariffDao.delete(oldTariff);

        /* same for options
        impossible to delete entity by making same entity from frontend!!!!
        */
//        optionsDao.deleteOptionById(42);


        /* to update Options aka remove old and update ones that left and add new ones

        options are old (just representation
        newOptions are 3 new to add and according to my idea I need to delete first option from old tariff
        and add 3 new other 3 should be left untouched

        Tariff oldTariff = tariffDao.findTariffById(16);
        List<Options> options = Arrays.asList(new Options("nelol", OptionType.INTERNET,oldTariff ),new Options("2", OptionType.INTERNET,oldTariff ),new Options("3", OptionType.INTERNET,oldTariff ),new Options("4", OptionType.INTERNET,oldTariff ));
        List<Options> newOptions = Arrays.asList(new Options("2", OptionType.INTERNET,oldTariff ),new Options("3", OptionType.INTERNET,oldTariff ),new Options("4", OptionType.INTERNET,oldTariff ));


        Options op = oldTariff.getOptions().get(0); //get first
        optionsDao.deleteOption(op); //delete first
        oldTariff.setOptions(options); //set new ones
        tariffDao.updateTariff(oldTariff); //update

        */
//
//        User user = new User("log","1",Role.ADMIN);
//        userDao.save(user);
//        List<Options> options = new ArrayList<>();
//
//        Tariff tariff = new Tariff();
//        options.add(new Options("da",OptionType.INTERNET,1D,tariff));
//        options.add(new Options("net",OptionType.INTERNET,1D,tariff));
//
//        tariff.setTitle("tariff");
//        tariff.setCost(1d);
//        tariff.setDescription("ddd");
//        tariff.setOptions(options);
//
//        tariffDao.addTariff(tariff);
//
//
//        List<Options> updatedOptions = Collections.singletonList(tariffDao.findTariffById(1).getOptions().get(0));

//        Contract contract= new Contract(1,87770011098L,user, updatedOptions,tariff);

//        contractDao.save(contract);

//        Contract contract2= new Contract(2,87770011098L,user, updatedOptions,tariff);

//        contractDao.save(contract2);

//        optionsDao.deleteOptionById(1);

//        contractDao.deleteContract(6);




    }
}
