package sn.ucad.ben.ebankingbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.ucad.ben.ebankingbackend.entites.AccountOperation;
import sn.ucad.ben.ebankingbackend.entites.BankAccount;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByBankAccountId(String id);
    Page<AccountOperation> findByBankAccountId(String id, Pageable pageable);
}
