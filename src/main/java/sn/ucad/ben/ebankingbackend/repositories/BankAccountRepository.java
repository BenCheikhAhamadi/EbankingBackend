package sn.ucad.ben.ebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.ucad.ben.ebankingbackend.entites.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
