package com.example.jwtsecurityfin.controller.budget;

import com.example.jwtsecurityfin.domain.account.Budget;
import com.example.jwtsecurityfin.domain.account.BudgetRepository;
import com.example.jwtsecurityfin.domain.currency.Currency;
import com.example.jwtsecurityfin.domain.currency.CurrencyRepository;
import com.example.jwtsecurityfin.domain.currency.NationCode;
import com.example.jwtsecurityfin.dto.request.BudgetDTO;
import com.example.jwtsecurityfin.dto.BudgetRegisterDTO;
import com.example.jwtsecurityfin.domain.travel.Travel;
import com.example.jwtsecurityfin.domain.travel.TravelRepository;
import com.example.jwtsecurityfin.service.budget.FileUploadService;
import com.example.jwtsecurityfin.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class BudGetUploadController {
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;
    private final TravelRepository travelRepository;

    private final CurrencyRepository currencyRepository;


    public BudGetUploadController(UserRepository userRepository,
                                  TravelRepository travelRepository,
                                  BudgetRepository budgetRepository,
                                  FileUploadService fileUploadService,
                                  CurrencyRepository currencyRepository)
    {
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
        this.budgetRepository = budgetRepository;
        this.fileUploadService = fileUploadService;
        this.currencyRepository = currencyRepository;
    }

    /**
     *  budget register
     * @param tripid
     * @param budgetDTO
     * @return
     */
    @PostMapping("/trip/{tripid}/budget/register")
    public ResponseEntity<Budget> BudgetRegister(@PathVariable("tripid") Integer tripid, @RequestBody BudgetDTO budgetDTO) {
        try {
            log.info("tripid ::{} ", tripid);
            log.info("register ::{} ", budgetDTO.getImgFile());

            String fileUrl = fileUploadService.uploadFile(budgetDTO.getImgFile());

            Budget budget = new Budget();

            budget.setTitle(budgetDTO.getName());
            budget.setTravel(travelRepository.findById(tripid).get());
            budget.setMoney(budgetDTO.getMoney());
            budget.setFileUrl(fileUrl);
            budgetRepository.save(budget);
            return ResponseEntity.ok(budget);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/trip/{tripid}/budget/register")
    public ResponseEntity<BudgetRegisterDTO> budgetStatus(@PathVariable Integer tripid){
        BudgetRegisterDTO brg = new BudgetRegisterDTO();
        LocalDate date = LocalDate.now();
        LocalDate testDate = LocalDate.parse("2023-05-16");

        Travel t = travelRepository.findById(tripid).get();

        List<NationCode> nationCodes = t.getNationCodes();
        Map<String, Double> nationAndRate = new HashMap<>();

        for (NationCode nationCode : nationCodes) {
            String nation = nationCode.getNation();
            String code = nationCode.getCode();
            System.out.println(nation +" " + code);
            Currency byNationAndDate = currencyRepository.findByNationAndDate(code, testDate);
            log.info("byNationAndDate : {}, {}",byNationAndDate.getNation(),byNationAndDate.getDate());
            nationAndRate.put(nation, byNationAndDate.getRate());
            log.info("nation::{}, code::{}, rate::{}", nation,code, byNationAndDate.getRate());
        }
        String username = t.getUser().getName();
        log.info("{}",t.getTitle());
        log.info("{}", date);

        brg.setName(username);
        brg.setRate(nationAndRate);
        return ResponseEntity.ok(brg);

    }
    
}
