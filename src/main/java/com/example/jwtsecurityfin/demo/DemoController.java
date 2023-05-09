package com.example.jwtsecurityfin.demo;


import com.example.jwtsecurityfin.domain.account.Account;
import com.example.jwtsecurityfin.domain.account.AccountRepository;
import com.example.jwtsecurityfin.domain.dto.TravelAccountDTO;
import com.example.jwtsecurityfin.domain.travel.Travel;
import com.example.jwtsecurityfin.domain.travel.TravelRepository;
import com.example.jwtsecurityfin.user.User;
import com.example.jwtsecurityfin.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final AccountRepository accountRepository;

    public DemoController(UserRepository userRepository, TravelRepository travelRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
        this.accountRepository = accountRepository;
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
    public Account registerAccount(Authentication authentication, @RequestBody TravelAccountDTO travelAccountDTO){
        log.info("DTO::{} and {}", travelAccountDTO.getTrip(), travelAccountDTO.getTitle());
        Account account = new Account();
        account.setTitle(travelAccountDTO.getTitle());
        //exception check it plz
//        account.setTravel(travelRepository.findByTitle(travelAccount.getTravelName()).get());
//        accountRepository.save(account);
//        log.info("Account registered : {} ", account.getTitle());
//        return account;
        return null;
    }


    @GetMapping("/user/travel/account")
    public List<Account> showAccount(Authentication authentication, @RequestBody Travel travel){
       log.info("user authentication: {}, travel info : {}", authentication.getName() , travel.getTitle());
        return accountRepository.findAllByTravel(travel);
    }



}
