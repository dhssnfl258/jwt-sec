package com.example.jwtsecurityfin.domain.travel;

import com.example.jwtsecurityfin.domain.currency.NationCode;
import com.example.jwtsecurityfin.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

//여행 엔티티
@Getter
@Setter
@Entity
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long id;
    private String title;
    private Integer amount;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "travel_nation",
            joinColumns = @JoinColumn(name = "travel_id"),
            inverseJoinColumns = @JoinColumn(name = "nation_code")
    )
    private List<NationCode> nationCodes;
}
