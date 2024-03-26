package sn.ucad.ben.ebankingbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ucad.ben.ebankingbackend.entites.BankAccount;
import sn.ucad.ben.ebankingbackend.entites.CurrentAccount;
import sn.ucad.ben.ebankingbackend.entites.SavingAccount;
import sn.ucad.ben.ebankingbackend.repositories.BankAccountRepository;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount =
                bankAccountRepository.findById("03226c04-6f31-4e97-b868-1d30e4f2b776").orElse(null);
        if(bankAccount!=null) {
            System.out.println("*********************************************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getSolde());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getDateOuverture());
            System.out.println(bankAccount.getCustomer().getNom());
            System.out.println(bankAccount.getClass().getSimpleName());
            if (bankAccount instanceof CurrentAccount) {
                System.out.println("Decouvet =>" + ((CurrentAccount) bankAccount).getDecouvert());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Taux interet=>" + ((SavingAccount) bankAccount).getTaux_interet());
            }
            bankAccount.getAccountOperations().forEach(operation -> {
                System.out.println(operation.getType() + "\t" + operation.getMontant() + "\t" + operation.getDateOperation());
            });
        }
    }
}
