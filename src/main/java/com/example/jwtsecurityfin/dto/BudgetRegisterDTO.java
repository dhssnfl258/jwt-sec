package com.example.jwtsecurityfin.dto;

import lombok.Data;

import java.util.Map;

@Data
public class BudgetRegisterDTO {
    private String name;
    private Map<String, Double> rate;
}
