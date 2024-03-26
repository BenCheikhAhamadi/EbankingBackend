package sn.ucad.ben.ebankingbackend.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.ucad.ben.ebankingbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;
@Entity
@Data @NoArgsConstructor  @AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",length = 4,discriminatorType = DiscriminatorType.STRING)
public class  BankAccount {
    @Id
    private String id;
    private double solde;
    private Date dateOuverture;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount",fetch =FetchType.EAGER)
    private List<AccountOperation> accountOperations;
}
