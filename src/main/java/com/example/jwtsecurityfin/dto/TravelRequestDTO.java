package com.example.jwtsecurityfin.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class TravelRequestDTO {

    private ArrayList<String> nation;
    private Integer money;
    private LocalDate startDate;
    private LocalDate endDate;

}
