package com.example.jwtsecurityfin.demo;

import com.example.jwtsecurityfin.domain.account.Account;
import com.example.jwtsecurityfin.domain.account.AccountRepository;
import com.example.jwtsecurityfin.domain.dto.BudgetDTO;
import com.example.jwtsecurityfin.domain.travel.TravelRepository;
import com.example.jwtsecurityfin.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
public class BudGetUploadController {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;
    private final TravelRepository travelRepository;


    public BudGetUploadController(UserRepository userRepository,
                                  TravelRepository travelRepository,
                                  AccountRepository accountRepository,
                                  FileUploadService fileUploadService)
    {
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
        this.accountRepository = accountRepository;
        this.fileUploadService = fileUploadService;
    }


    @PostMapping("/trip/{tripid}/budget/register")
    public ResponseEntity<Account> uploadFile(@PathVariable("tripid") Integer tripid, @ModelAttribute BudgetDTO budgetDTO) {
        try {
            log.info("tripid ::{} ", tripid);
            log.info("register ::{} ", budgetDTO.getImgFile());
            String fileUrl = fileUploadService.uploadFile(budgetDTO.getImgFile());
            Account account = new Account();
            account.setTitle(budgetDTO.getName());
            account.setTravel(travelRepository.findById(tripid).get());
            account.setAmount(budgetDTO.getMoney());
            account.setFileUrl(fileUrl);
            accountRepository.save(account);
            return ResponseEntity.ok(account);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
