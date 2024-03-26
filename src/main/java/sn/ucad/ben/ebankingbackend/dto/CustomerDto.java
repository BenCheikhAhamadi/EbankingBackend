package sn.ucad.ben.ebankingbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import sn.ucad.ben.ebankingbackend.entites.BankAccount;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long id;
    @Column(nullable = false,length = 150)
    private String nom;
    @Column(nullable = false,length=150)
    private String prenom;
    @Column(nullable = false,length=150)
    private  String email;
    @Column(nullable = false,length = 150)
    private String  address ;
    @Column(nullable = false,length = 150)
    private  String num_tel;

}
