package sn.ucad.ben.ebankingbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import sn.ucad.ben.ebankingbackend.dto.BankAccountDto;
import sn.ucad.ben.ebankingbackend.dto.CurrentBankAccountDto;
import sn.ucad.ben.ebankingbackend.dto.CustomerDto;
import sn.ucad.ben.ebankingbackend.dto.SavingBankAccountDto;
import sn.ucad.ben.ebankingbackend.entites.*;
import sn.ucad.ben.ebankingbackend.enums.AccountStatus;
import sn.ucad.ben.ebankingbackend.enums.OperationType;
import sn.ucad.ben.ebankingbackend.exceptions.BankAccountNotFoundException;
import sn.ucad.ben.ebankingbackend.exceptions.CustomerNotFoundException;
import sn.ucad.ben.ebankingbackend.exceptions.SoldeNotSufficientException;
import sn.ucad.ben.ebankingbackend.repositories.AccountOperationRepository;
import sn.ucad.ben.ebankingbackend.repositories.BankAccountRepository;
import sn.ucad.ben.ebankingbackend.repositories.CustomerRepository;
import sn.ucad.ben.ebankingbackend.services.BankAccountService;
import sn.ucad.ben.ebankingbackend.services.BankService;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiOpenBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiOpenBankingApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
		return args -> {
         Stream.of("Ben Cheikh","Diouf","Fatou").forEach(name->{
			 CustomerDto customer = new CustomerDto();
			 customer.setNom(name);
			 customer.setPrenom("Ahamadi");
			 customer.setAddress("Fann Hock");
			 customer.setEmail(name+"@gmail.com");
			 customer.setNum_tel("783836543");
			 bankAccountService.saveCustomer(customer);
		 });
		 bankAccountService.listCustomer().forEach(customer -> {
			 try {
				 bankAccountService.saveBankCurrentAccount(Math.random()*9000,9000, customer.getId());
			     bankAccountService.saveBankSavingAccount(Math.random()*120000,5.5, customer.getId());
			 } catch (CustomerNotFoundException e) {
				 e.printStackTrace();
			 }
		    });
			List<BankAccountDto> bankAccounts = bankAccountService.bankAccountList();
			for (BankAccountDto bankAccount:bankAccounts){
				for (int i = 0; i <10 ; i++) {
					String accountId;
					if(bankAccount instanceof SavingBankAccountDto){
						accountId=((SavingBankAccountDto) bankAccount).getId();
					} else{
						accountId=((CurrentBankAccountDto) bankAccount).getId();
					}
					bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
					bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
				}
			}
		};
	}

	//@Bean
	CommandLineRunner star(AccountOperationRepository accountOperationRepository,
						   CustomerRepository customerRepository,
						   BankAccountRepository bankAccountRepository){
		return args -> {
			Stream.of("Ben Cheikh","Diouf","Ahamadi","Fatou").forEach(name->{
				Customer customer = new Customer();
				customer.setNom(name);
				customer.setPrenom(name+"Ahamadi");
				customer.setAddress("Fann Hock");
				customer.setEmail("ben@gmail.com");
				customer.setNum_tel("783836543");
				customerRepository.save(customer);
			});
			customerRepository.findAll().forEach(cust->{
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setSolde(Math.random()*90000);
				currentAccount.setDateOuverture(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setDecouvert(9000);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setSolde(Math.random()*90000);
				savingAccount.setDateOuverture(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setTaux_interet(5.5);
				bankAccountRepository.save(savingAccount);
			});
			bankAccountRepository.findAll().forEach(acc->{
				for (int i = 0; i < 12; i++) {
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setDateOperation(new Date());
					accountOperation.setMontant(Math.random()*12000);
					accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
					accountOperation.setBankAccount(acc);
					accountOperationRepository.save(accountOperation);
				}

			});
		};
	}

}
