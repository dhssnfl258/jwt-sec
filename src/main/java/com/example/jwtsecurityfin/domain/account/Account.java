package com.example.jwtsecurityfin.domain.account;

import com.example.jwtsecurityfin.domain.travel.Travel;
import com.example.jwtsecurityfin.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    private String title;
    private String amount;

    @Enumerated(EnumType.STRING)
    private Type type;


    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

}
