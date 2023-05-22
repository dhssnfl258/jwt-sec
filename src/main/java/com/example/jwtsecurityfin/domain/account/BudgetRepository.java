package com.example.jwtsecurityfin.domain.account;

import com.example.jwtsecurityfin.domain.travel.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findAllByTravel(Travel travel);
}
