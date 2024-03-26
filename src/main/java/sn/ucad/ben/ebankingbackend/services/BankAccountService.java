package sn.ucad.ben.ebankingbackend.services;

import sn.ucad.ben.ebankingbackend.dto.*;
import sn.ucad.ben.ebankingbackend.entites.BankAccount;
import sn.ucad.ben.ebankingbackend.entites.CurrentAccount;
import sn.ucad.ben.ebankingbackend.entites.Customer;
import sn.ucad.ben.ebankingbackend.entites.SavingAccount;
import sn.ucad.ben.ebankingbackend.exceptions.BankAccountNotFoundException;
import sn.ucad.ben.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
 CustomerDto saveCustomer(CustomerDto customerDto);
 CurrentBankAccountDto saveBankCurrentAccount(double initialBalance, double decouvert, Long customerId) throws CustomerNotFoundException;
 SavingBankAccountDto saveBankSavingAccount(double initialBalance, double taux_interet, Long customerId) throws CustomerNotFoundException;
 List<CustomerDto>listCustomer();
 BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException;
 void debit(String accoundId,double montant,String descriprtion) throws BankAccountNotFoundException;
 void credit(String accoundId,double montant,String descriprtion) throws BankAccountNotFoundException;
 void transfert(String accountIdSource, String accountIdDestination,double montant) throws BankAccountNotFoundException;

 List<BankAccountDto> bankAccountList();

 CustomerDto getCustomer(Long id) throws CustomerNotFoundException;

 CustomerDto updateCustomer(CustomerDto customerDto);

 void deleteCustomer(Long id);

 List<AccountOperationDto> accountHistory(String id);

 AccountHistoryDto getAccountHistory(String id, int page, int size) throws BankAccountNotFoundException;
}
