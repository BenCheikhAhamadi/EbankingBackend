package sn.ucad.ben.ebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.ucad.ben.ebankingbackend.entites.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
