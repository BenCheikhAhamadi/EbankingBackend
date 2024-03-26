package sn.ucad.ben.ebankingbackend.dto;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDto {
    private String id;
    private double solde;
    private int curentPage;
    private  int totalPage;
    private int pageSize;
    private List<AccountOperationDto> accountOperationDtos;
}
