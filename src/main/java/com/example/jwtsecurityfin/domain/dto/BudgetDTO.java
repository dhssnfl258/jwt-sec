package com.example.jwtsecurityfin.domain.dto;

import com.example.jwtsecurityfin.domain.account.Type;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BudgetDTO {
    private String name;
    private String Money;
    private String Category;
    private Type type;
    private MultipartFile imgFile;
}
