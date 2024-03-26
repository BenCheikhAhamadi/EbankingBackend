package sn.ucad.ben.ebankingbackend.dto;


import lombok.Data;

import sn.ucad.ben.ebankingbackend.enums.OperationType;
import java.util.Date;

@Data
public class AccountOperationDto {
    private Long id;
    private Date dateOperation;
    private double montant;
    private OperationType type;
    private String description;

}
