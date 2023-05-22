package com.example.jwtsecurityfin.dto.request;

import com.example.jwtsecurityfin.domain.account.Type;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BudgetDTO {
    private String name;
    private String Money;
    private String Category;
    private Type type;
    private MultipartFile imgFile;
    private LocalDateTime registerTime;
    private List<String> SpendMoneyWith;
}
