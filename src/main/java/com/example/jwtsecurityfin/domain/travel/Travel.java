package com.example.jwtsecurityfin.domain.travel;

import com.example.jwtsecurityfin.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private String sDate;
    private String eDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
