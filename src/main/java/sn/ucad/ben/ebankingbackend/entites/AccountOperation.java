package sn.ucad.ben.ebankingbackend.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.ucad.ben.ebankingbackend.enums.OperationType;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateOperation;
    private double montant;
    @ManyToOne
    private  BankAccount bankAccount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private String description;

}
