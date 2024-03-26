package sn.ucad.ben.ebankingbackend.dto;


import lombok.Data;
import sn.ucad.ben.ebankingbackend.entites.Customer;
import sn.ucad.ben.ebankingbackend.enums.AccountStatus;

import java.util.Date;


@Data

public class CurrentBankAccountDto extends BankAccountDto {
    private String id;
    private double solde;
    private Date dateOuverture;
    private AccountStatus status;
    private CustomerDto customerDto;
    private  double decouvert;

}
