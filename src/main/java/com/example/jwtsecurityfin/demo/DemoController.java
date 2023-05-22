package com.example.jwtsecurityfin.demo;


import com.example.jwtsecurityfin.domain.account.Budget;
import com.example.jwtsecurityfin.domain.account.BudgetRepository;
import com.example.jwtsecurityfin.domain.currency.NationCode;
import com.example.jwtsecurityfin.domain.currency.NationCodeRepository;
import com.example.jwtsecurityfin.dto.TravelAccountDTO;
import com.example.jwtsecurityfin.domain.travel.Travel;
import com.example.jwtsecurityfin.domain.travel.TravelRepository;
import com.example.jwtsecurityfin.domain.user.User;
import com.example.jwtsecurityfin.domain.user.UserRepository;
import com.example.jwtsecurityfin.dto.TravelRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 *  this is demo controller
 *
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final BudgetRepository budgetRepository;
    private final NationCodeRepository nationCodeRepository;

    @Autowired
    public DemoController(UserRepository userRepository, TravelRepository travelRepository,
                          BudgetRepository budgetRepository,
                          NationCodeRepository nationCodeRepository) {
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
        this.budgetRepository = budgetRepository;
        this.nationCodeRepository = nationCodeRepository;
    }

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("hello from secured endpoint");
    }

    /**
     * user 정보
     * @param authentication jwt
     * @return String userinfo
     */
    @GetMapping("/user")
    public String userinfo( Authentication authentication){
        log.info("this is user info {}", authentication.getName());
//        ResponseEntity.ok(service.authenticate(request));

        return authentication.getName();
    }

    @GetMapping("/userinfo")
    public Optional<User> userinfo1( Authentication authentication){
        log.info("this is user info {}", authentication.getName());
//        ResponseEntity.ok(service.authenticate(request));
        String email = authentication.getName();
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail;
    }

    /**
     *  Create Travel
     *  설정정보 추가해야함
     *  travel entity 수정 요망
     *
     * @param authentication ::: jwt
     * @param travel ::: travel Object
     * @return Travel
     */
    @PostMapping("/user/createTravel")
    public Travel createTravel(Authentication authentication, @RequestBody Travel travel){
        log.info("this is user info {}", authentication.getName());
//        ResponseEntity.ok(service.authenticate(request));
        String email = authentication.getName();
        Optional<User> byEmail = userRepository.findByEmail(email);
        User a = byEmail.get();
        Travel travel1 = travel;
        travel1.setUser(a);
        travelRepository.save(travel1);
        return travel1;
    }

    /**
     *
     * 사용자 정보와 일치하는 Travel list 반환
     * @param authentication jwt
     * @return List <Travel>
     */
    @GetMapping("/user/TravelList")
    public List<Travel> showTravel(Authentication authentication){
        log.info("this is user info {}", authentication.getName());
//        ResponseEntity.ok(service.authenticate(request));
        String email = authentication.getName();
        Optional<User> byEmail = userRepository.findByEmail(email);
        User a = byEmail.get();
        return travelRepository.findAllByUser(a);
    }

    /**
     * Travel 삭제
     *
     * @param authentication : jwt
     * @param travel : travel
     * @return String :: DeletedTravelTitle
     */
    @DeleteMapping("/user/Travel/delete")
    public String delTravel(Authentication authentication, @RequestBody Travel travel){
        String del = travel.getTitle();
        travelRepository.delete(travel);
        return del;
    }


    @PostMapping("/user/travel/account/register")
    public Budget registerAccount(Authentication authentication, @RequestBody TravelAccountDTO travelAccountDTO){
        log.info("DTO::{} and {}", travelAccountDTO.getTrip(), travelAccountDTO.getTitle());
        Budget budget = new Budget();
        budget.setTitle(travelAccountDTO.getTitle());
        //exception check it plz
//        account.setTravel(travelRepository.findByTitle(travelAccount.getTravelName()).get());
//        accountRepository.save(account);
//        log.info("Account registered : {} ", account.getTitle());
//        return account;
        return null;
    }


    @GetMapping("/user/travel/account")
    public List<Budget> showAccount(Authentication authentication, @RequestBody Travel travel){
       log.info("user authentication: {}, travel info : {}", authentication.getName() , travel.getTitle());
        return budgetRepository.findAllByTravel(travel);
    }

    @GetMapping("/user/travel/test")
    public Travel test(Authentication authentication, @RequestBody TravelRequestDTO travelRequestDTO){
        log.info("user authentication: {}, travel info : {}", authentication.getName() , travelRequestDTO.getNation());
        Travel travel = new Travel();
        travel.setAmount(travelRequestDTO.getMoney());
        travel.setStartDate(travelRequestDTO.getStartDate());
        travel.setEndDate(travelRequestDTO.getEndDate());
        ArrayList<String> nation = travelRequestDTO.getNation();
        List<NationCode> nc = new ArrayList<>();
        for (String s : nation) {
            Optional<NationCode> byNation = nationCodeRepository.findByNation(s);
            NationCode nationCode = byNation.get();
            nc.add(nationCode);
        }
        travel.setNationCodes(nc);
        travelRepository.save(travel);
        return travel;
    }


}
