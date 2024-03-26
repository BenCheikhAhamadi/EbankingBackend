package sn.ucad.ben.ebankingbackend.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_customer")
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
    @OneToMany(mappedBy = "customer")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<BankAccount> bankAccounts;
}
