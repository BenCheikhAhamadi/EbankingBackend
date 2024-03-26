package sn.ucad.ben.ebankingbackend.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import sn.ucad.ben.ebankingbackend.dto.AccountOperationDto;
import sn.ucad.ben.ebankingbackend.dto.CurrentBankAccountDto;
import sn.ucad.ben.ebankingbackend.dto.CustomerDto;
import sn.ucad.ben.ebankingbackend.dto.SavingBankAccountDto;
import sn.ucad.ben.ebankingbackend.entites.AccountOperation;
import sn.ucad.ben.ebankingbackend.entites.CurrentAccount;
import sn.ucad.ben.ebankingbackend.entites.Customer;
import sn.ucad.ben.ebankingbackend.entites.SavingAccount;

@Service
public class BankAccountMapperImp {
    public CustomerDto fromCustomer(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }

    public  Customer fromCustomersDto(CustomerDto customerDto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;
    }

    public SavingBankAccountDto fromSavingBankAccount(SavingAccount savingAccount){
       SavingBankAccountDto savingBankAccountDto = new SavingBankAccountDto();
       BeanUtils.copyProperties(savingAccount,savingBankAccountDto);
       savingBankAccountDto.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
       savingBankAccountDto.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDto;
    }

    public SavingAccount fromSavingBankAccountDto(SavingBankAccountDto savingBankAccountDto){
      SavingAccount savingAccount = new SavingAccount();
      BeanUtils.copyProperties(savingBankAccountDto,savingAccount);
      savingAccount.setCustomer(fromCustomersDto(savingBankAccountDto.getCustomerDto()));
      return  savingAccount;
    }

    public CurrentBankAccountDto fromCurrentBankAccount(CurrentAccount currentAccount){
      CurrentBankAccountDto currentBankAccountDto = new CurrentBankAccountDto();
      BeanUtils.copyProperties(currentAccount,currentBankAccountDto);
      currentBankAccountDto.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDto.setType(currentAccount.getClass().getSimpleName());
      return  currentBankAccountDto;
    }

    public CurrentAccount fromCurrentBankAccountDto(CurrentBankAccountDto currentBankAccountDto){
       CurrentAccount currentAccount = new CurrentAccount();
       BeanUtils.copyProperties(currentBankAccountDto,currentAccount);
       currentAccount.setCustomer(fromCustomersDto(currentBankAccountDto.getCustomerDto()));
       return currentAccount;
    }

    public AccountOperationDto fromAccountOperation(AccountOperation accountOperation){
      AccountOperationDto accountOperationDto = new AccountOperationDto();
      BeanUtils.copyProperties(accountOperation,accountOperationDto);
      return accountOperationDto;
    }


}
