package sn.ucad.ben.ebankingbackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.ucad.ben.ebankingbackend.dto.AccountHistoryDto;
import sn.ucad.ben.ebankingbackend.dto.AccountOperationDto;
import sn.ucad.ben.ebankingbackend.dto.BankAccountDto;
import sn.ucad.ben.ebankingbackend.exceptions.BankAccountNotFoundException;
import sn.ucad.ben.ebankingbackend.services.BankAccountService;

import java.util.List;

@RestController
public class BankAccountController {
    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    @GetMapping("/accounts/{id}")
    public BankAccountDto getBankAccount(@PathVariable String id) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(id);
    }
    @GetMapping("/accounts")
    public List<BankAccountDto>listAccounts(){
        return bankAccountService.bankAccountList();
    }
   @GetMapping("/accounts/{id}/operations")
    public List<AccountOperationDto>getHistory(@PathVariable String id){
        return bankAccountService.accountHistory(id);
    }

    @GetMapping("/accounts/{id}/pageOperations")
    public AccountHistoryDto getAccountHistory(@PathVariable String id, @RequestParam(name="page",defaultValue = "0") int page,
                                               @RequestParam(name="size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(id,page,size);
    }
}
