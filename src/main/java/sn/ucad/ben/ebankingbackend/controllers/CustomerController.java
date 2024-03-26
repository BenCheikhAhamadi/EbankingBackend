package sn.ucad.ben.ebankingbackend.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sn.ucad.ben.ebankingbackend.dto.CustomerDto;
import sn.ucad.ben.ebankingbackend.entites.Customer;
import sn.ucad.ben.ebankingbackend.exceptions.CustomerNotFoundException;
import sn.ucad.ben.ebankingbackend.services.BankAccountService;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class CustomerController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDto>customerList(){
        return bankAccountService.listCustomer();
    }
    @GetMapping("/customers/{id}")
    public CustomerDto getCustomer(@PathVariable(name = "id") Long id) throws CustomerNotFoundException {
      return bankAccountService.getCustomer(id);
    }
    @PostMapping ("/customers")
    public  CustomerDto saveCustomer(@RequestBody CustomerDto customerDto){
        return bankAccountService.saveCustomer(customerDto);
    }
    @PutMapping("/customers/{id}")
   public  CustomerDto updateCustomer(@PathVariable Long id,@RequestBody CustomerDto customerDto){
     customerDto.setId(id);
     return bankAccountService.updateCustomer(customerDto);
   }
    @DeleteMapping("/customers/{id}")
   public void  deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
   }

}
