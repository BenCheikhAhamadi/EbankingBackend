package sn.ucad.ben.ebankingbackend.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@DiscriminatorValue("SA")
@Entity
public class SavingAccount extends BankAccount{
    private double taux_interet;
}
