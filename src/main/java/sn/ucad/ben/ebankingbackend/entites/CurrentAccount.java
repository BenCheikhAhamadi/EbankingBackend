package sn.ucad.ben.ebankingbackend.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DiscriminatorValue("CC")
@Data  @NoArgsConstructor  @AllArgsConstructor
@Entity
public class CurrentAccount extends BankAccount{
    private  double decouvert;
}
