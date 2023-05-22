package com.example.jwtsecurityfin.domain.currency;


import com.example.jwtsecurityfin.domain.travel.Travel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

//국가, 국가코드 엔티티

@Entity
//@Table(name = "currency")
@Getter
@Setter
@ToString
public class NationCode {
    @Id
    private String nation;
    private String code;

    @ManyToMany(mappedBy = "nationCodes")
    private List<Travel> travels;
}
