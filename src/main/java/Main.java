import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


//    public static void main(String[] args) {
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

//    }

    public static void main(String[] args) {
       List<Tariff> tariffs = new ArrayList<>();
        List<User> users = new ArrayList<>();

        users.add(new User("admin",passwordEncoder.encode("admin"),Role.ADMIN));
        users.add(new User("Artur",passwordEncoder.encode("Artur"),Role.CUSTOMER));
        users.add(new User("Josh",passwordEncoder.encode("Josh"),Role.CUSTOMER));
        users.add(new User("John",passwordEncoder.encode("John"),Role.CUSTOMER));
        users.add(new User("Kate",passwordEncoder.encode("Kate"),Role.CUSTOMER));
        users.add(new User("Levi",passwordEncoder.encode("Levi"),Role.CUSTOMER));
        users.add(new User("Daniil",passwordEncoder.encode("Daniil"),Role.CUSTOMER));
        users.add(new User("Mary",passwordEncoder.encode("Mary"),Role.CUSTOMER));

        tariffs.add(new Tariff("Super",20d,"The most Superior tariff: 100 minutes, 100 messages, 20 gb of internet",null));
        List<Options> options = new ArrayList<>();
        options.add(new Options("50 messages",OptionType.MESSAGES,5d, tariffs.get(0)));
        options.add(new Options("50 minutes",OptionType.MINUTES,5d, tariffs.get(0)));
        options.add(new Options("10 gb",OptionType.INTERNET,10d, tariffs.get(0)));
        options.add(new Options("anti-spam",OptionType.UTIL,10d, tariffs.get(0)));
        options.add(new Options("call-security",OptionType.UTIL,10d, tariffs.get(0)));

        tariffs.get(0).setOptions(options);

        tariffs.add(new Tariff("Internet Master",30d,"For internet lovers: 0 minutes, 0 messages, 100 gb of internet",null));
        List<Options> options2 = new ArrayList<>();
        options2.add(new Options("50 messages",OptionType.MESSAGES,5d, tariffs.get(1)));
        options2.add(new Options("50 minutes",OptionType.MINUTES,5d, tariffs.get(1)));
        options2.add(new Options("50 gb",OptionType.INTERNET,15d, tariffs.get(1)));
        tariffs.get(1).setOptions(options2);

        tariffs.add(new Tariff("Call master",40d,"YOU like talking?: 1000 minutes, 100 messages, 20 gb of internet",null));
        List<Options> options3 = new ArrayList<>();
        options3.add(new Options("50 messages",OptionType.MESSAGES,5d, tariffs.get(2)));
        options3.add(new Options("50 minutes",OptionType.MINUTES,5d, tariffs.get(2)));
        options3.add(new Options("10 gb",OptionType.INTERNET,10d, tariffs.get(2)));
        options3.add(new Options("anti-spam",OptionType.UTIL,10d, tariffs.get(2)));
        tariffs.get(2).setOptions(options3);

        tariffs.add(new Tariff("Netariff",5d,"Choose your own options for tariff: 0 minutes, 0 messages, 0 gb of internet",null));
        List<Options> options4 = new ArrayList<>();
        options4.add(new Options("50 messages",OptionType.MESSAGES,5d, tariffs.get(3)));
        options4.add(new Options("100 messages",OptionType.MESSAGES,10d, tariffs.get(3)));
        options4.add(new Options("50 minutes",OptionType.MINUTES,5d, tariffs.get(3)));
        options4.add(new Options("100 minutes",OptionType.MINUTES,10d, tariffs.get(3)));
        options4.add(new Options("10 gb",OptionType.INTERNET,10d, tariffs.get(3)));
        options4.add(new Options("20 gb",OptionType.INTERNET,20d, tariffs.get(3)));
        options4.add(new Options("anti-spam",OptionType.UTIL,10d, tariffs.get(3)));
        tariffs.get(3).setOptions(options4);

        tariffs.add(new Tariff("MEGA",50d,"MEGA tariff: 1000 minutes, 1000 messages, 200 gb of internet",null));

        tariffs.add(new Tariff("Message master",30d,"If you like chatting: 100 minutes, 1000 messages, 20 gb of internet",null));
        List<Options> options5 = new ArrayList<>();
        options5.add(new Options("50 messages",OptionType.MESSAGES,5d, tariffs.get(5)));
        options5.add(new Options("50 minutes",OptionType.MINUTES,5d, tariffs.get(5)));
        options5.add(new Options("10 gb",OptionType.INTERNET,10d, tariffs.get(5)));
        options5.add(new Options("anti-spam",OptionType.UTIL,10d, tariffs.get(5)));
        tariffs.get(5).setOptions(options5);

        for (User u:users) {
            userDao.save(u);
        }

        for (Tariff t:tariffs) {
            tariffDao.addTariff(t);
        }

    }
}
