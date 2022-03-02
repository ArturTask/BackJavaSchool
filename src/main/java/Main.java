import ru.javaSchoolProject.dao.OptionsDao;
import ru.javaSchoolProject.dao.TariffDao;
import ru.javaSchoolProject.enums.OptionType;
import ru.javaSchoolProject.enums.Role;
import ru.javaSchoolProject.models.Options;
import ru.javaSchoolProject.models.Tariff;
import ru.javaSchoolProject.models.User;
import ru.javaSchoolProject.services.UserService;
import sun.jvm.hotspot.oops.TypeArray;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static TariffDao tariffDao = new TariffDao();
    private static OptionsDao optionsDao = new OptionsDao();
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






    }
}
