package com.wizeline.maven.learningjavamaven.service;
import com.wizeline.maven.learningjavamaven.enums.AccountType;
import com.wizeline.maven.learningjavamaven.enums.Country;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.repository.BankingAccountRepository;
import com.wizeline.maven.learningjavamaven.repository.UserRepositoryImpl;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.wizeline.maven.learningjavamaven.utils.Utils.*;
@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    BankingAccountRepository bankAccountRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    private static final Logger LOGGER = Logger.getLogger(BankAccountServiceImpl.class.getName());

    @Override
    public List<BankAccountDTO> getAccounts() {
        // Definicion de lista con la informacion de las cuentas existentes.
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        BankAccountDTO bankAccountOne =buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        accountDTOList.add(bankAccountOne);
        mongoTemplate.save(bankAccountOne);
        BankAccountDTO bankAccountTwo =buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2));
        accountDTOList.add(bankAccountTwo);
        mongoTemplate.save(bankAccountTwo);
        BankAccountDTO bankAccountThree =buildBankAccount("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4));
        accountDTOList.add(bankAccountThree);
        mongoTemplate.save(bankAccountThree);

        //Imprime en la Consola cuales son los records encontrados en la coleccion
        //bankAccountCollection de la mongo db
        mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUser()).forEach(
                (user) -> {
                    LOGGER.info("User stored in bankAccountCollection " + user );
                });

        return accountDTOList;
    }

    @Override
    public BankAccountDTO getAccountDetails(String user, String lastUsage) {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateformatter);
        return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
    }

    @Override
    public BankAccountDTO getAccountDetails(String user) {
        return buildBankAccount(user, true);
    }

    @Override
    public boolean deleteAccounts() {
        boolean eliminado = false;
        try {
            bankAccountRepository.deleteAll();
            eliminado=true;
        } catch (Exception e) {
            eliminado=false;

        }
        return eliminado;
    }

    @Override
    public List<BankAccountDTO> getAccountByUser(String user) {
        //Buscamos todos aquellos registros de tipo BankAccountDTO
        //que cumplen con la criteria de que el userName haga match
        //con la variable user
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(user));
        return mongoTemplate.find(query, BankAccountDTO.class);
    }

    // Creación de tipo de dato BankAccount
    private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(randomAcountNumber());
        bankAccountDTO.setAccountName("Dummy Account ".concat(randomInt()));
        bankAccountDTO.setUser(user);
        bankAccountDTO.setAccountBalance(randomBalance());
        bankAccountDTO.setAccountType(pickRandomAccountType());
        bankAccountDTO.setCountry(getCountry(country));
        bankAccountDTO.getLastUsage(); // <- Se invoca al metodo de acceso get() para obtener la fecha actual
        bankAccountDTO.setCreationDate(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }




    // Creación de tipo de dato BankAccount
    private BankAccountDTO buildBankAccount(String user, boolean isActive) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(123L);
        bankAccountDTO.setAccountName("Dummy Account");
        bankAccountDTO.setUser(user);
        bankAccountDTO.setAccountBalance(843.24);
        bankAccountDTO.setAccountType(AccountType.NOMINA);
        bankAccountDTO.setCountry("Mexico");
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }
    // Creación de tipo de dato BankAccount con la ayuda de la clase Utils.java
    private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, String lastUsage) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(randomAcountNumber());
        bankAccountDTO.setAccountName("Dummy Account ".concat(randomInt()));
        bankAccountDTO.setUser(user);
        bankAccountDTO.setAccountBalance(randomBalance());
        bankAccountDTO.setAccountType(pickRandomAccountType());
        bankAccountDTO.setCountry(getCountry(country));
        bankAccountDTO.setLastUsage(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }
}
