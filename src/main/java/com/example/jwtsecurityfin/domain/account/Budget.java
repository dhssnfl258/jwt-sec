package com.example.jwtsecurityfin.domain.account;

import com.example.jwtsecurityfin.domain.travel.Travel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    private String title;
    private String Money;
    private String nation;
    private Double nationMoney;
    private LocalDateTime registerTime;
    private String fileUrl;
    @Enumerated(EnumType.STRING)
    private Type type;


    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

}
