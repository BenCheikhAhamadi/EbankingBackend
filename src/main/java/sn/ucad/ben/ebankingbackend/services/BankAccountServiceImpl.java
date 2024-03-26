package sn.ucad.ben.ebankingbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ucad.ben.ebankingbackend.dto.*;
import sn.ucad.ben.ebankingbackend.entites.*;
import sn.ucad.ben.ebankingbackend.enums.OperationType;
import sn.ucad.ben.ebankingbackend.exceptions.BankAccountNotFoundException;
import sn.ucad.ben.ebankingbackend.exceptions.CustomerNotFoundException;
import sn.ucad.ben.ebankingbackend.exceptions.SoldeNotSufficientException;
import sn.ucad.ben.ebankingbackend.mapper.BankAccountMapperImp;
import sn.ucad.ben.ebankingbackend.repositories.AccountOperationRepository;
import sn.ucad.ben.ebankingbackend.repositories.BankAccountRepository;
import sn.ucad.ben.ebankingbackend.repositories.CustomerRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImp dtoMapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        log.info("Saving new customer");
        Customer customer = dtoMapper.fromCustomersDto(customerDto);
        Customer saveCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(saveCustomer);
    }

    @Override
    public CurrentBankAccountDto saveBankCurrentAccount(double initialSolde, double decouvert, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundException("Customer not exist");
        CurrentAccount currentAccount = new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setDateOuverture(new Date());
        currentAccount.setSolde( initialSolde);
        currentAccount.setDecouvert(decouvert);
        currentAccount.setCustomer(customer);
        CurrentAccount saveBankAccount = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(saveBankAccount);
    }

    @Override
    public SavingBankAccountDto saveBankSavingAccount(double initialSolde, double taux_interet, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundException("Customer not exist");
        SavingAccount savingAccount = new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setDateOuverture(new Date());
        savingAccount.setSolde( initialSolde);
        savingAccount.setTaux_interet(taux_interet);
        savingAccount.setCustomer(customer);
        SavingAccount saveBankAccunt = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(saveBankAccunt);
    }


    @Override
    public List<CustomerDto> listCustomer() {
        List<Customer> customers = customerRepository.findAll();

        List<CustomerDto> customerDtos=customers.stream()
                .map(customer -> dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        /*
        List<CustomerDto> customerDtos=new ArrayList<>();
        for (Customer customer:customers){
            CustomerDto customerDto = dtoMapper.fromCustomer(customer);
            customerDtos.add(customerDto);
        }

         */
       return customerDtos;
    }

    @Override
    public BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not fount"));
        if (bankAccount instanceof SavingAccount){
            SavingAccount savingAccount =(SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount(savingAccount);
        }else {
            CurrentAccount currentAccount =(CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }


    }

    @Override
    public void debit(String accountId, double montant, String descriprtion) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not fount"));
        if (bankAccount.getSolde()<montant)
            throw new SoldeNotSufficientException("Solde not sufficient");
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setMontant(montant);
        accountOperation.setDescription(descriprtion);
        accountOperation.setDateOperation(new Date());
        accountOperation.setBankAccount(bankAccount);
         accountOperationRepository.save(accountOperation);
         bankAccount.setSolde(bankAccount.getSolde()- montant);
         bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double montant, String descriprtion) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not fount"));
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setMontant(montant);
        accountOperation.setDescription(descriprtion);
        accountOperation.setDateOperation(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setSolde(bankAccount.getSolde()+ montant);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double montant) throws BankAccountNotFoundException,SoldeNotSufficientException {

        debit(accountIdSource,montant,"Transfert to"+accountIdDestination);
        credit(accountIdDestination,montant,"Transfert from"+accountIdSource);
    }
    @Override
    public List<BankAccountDto> bankAccountList(){
        List<BankAccount>bankAccounts=bankAccountRepository.findAll();
        List<BankAccountDto> bankAccountDtos=bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount){
                SavingAccount savingAccount =(SavingAccount) bankAccount;
                return  dtoMapper.fromSavingBankAccount(savingAccount);

            }else {
                CurrentAccount currentAccount =(CurrentAccount) bankAccount;
                return  dtoMapper.fromCurrentBankAccount(currentAccount);

            }
        }).collect(Collectors.toList());
        return bankAccountDtos;
    }
  @Override
    public CustomerDto getCustomer(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()->new CustomerNotFoundException("Customer not found"));
        return dtoMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        log.info("Saving new customer");
        Customer customer = dtoMapper.fromCustomersDto(customerDto);
        Customer saveCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(saveCustomer);
    }
   @Override
    public  void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
@Override
public List<AccountOperationDto> accountHistory(String id){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(id);
       return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
}

    @Override
    public AccountHistoryDto getAccountHistory(String id, int page, int size) throws BankAccountNotFoundException {

       Page<AccountOperation> accountOperations =accountOperationRepository.findByBankAccountId(id, PageRequest.of(page,size));
        BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);
        if (bankAccount==null)throw new BankAccountNotFoundException("Account not found");
       AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
        List<AccountOperationDto> accountOperationDtos = accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op))
                .collect(Collectors.toList());
        accountHistoryDto.setAccountOperationDtos(accountOperationDtos);
        accountHistoryDto.setId(bankAccount.getId());
        accountHistoryDto.setSolde(bankAccount.getSolde());
        accountHistoryDto.setCurentPage(page);
        accountHistoryDto.setPageSize(size);
        accountHistoryDto.setTotalPage(accountOperations.getTotalPages());
       return accountHistoryDto ;
    }

}
