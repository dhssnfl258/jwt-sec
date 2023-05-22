package com.example.jwtsecurityfin.controller.trip;


import com.example.jwtsecurityfin.domain.account.BudgetRepository;
import com.example.jwtsecurityfin.domain.travel.Travel;
import com.example.jwtsecurityfin.domain.travel.TravelRepository;
import com.example.jwtsecurityfin.domain.user.User;
import com.example.jwtsecurityfin.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/trip")
public class TripController {


    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final BudgetRepository budgetRepository;

    @Autowired
    public TripController(UserRepository userRepository, TravelRepository travelRepository, BudgetRepository budgetRepository) {
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
        this.budgetRepository = budgetRepository;
    }

    /**
     *  upload travel
     * @param authentication jwt
     * @param travel request body travel obj
     * @return Travel
     */
    @PostMapping("/user/createTravel")
    public ResponseEntity<?> createTrip(Authentication authentication, @RequestBody Travel travel) {
        String email = authentication.getName();
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            Travel trip = travel;
            trip.setUser(user);
            travelRepository.save(trip);
            return ResponseEntity.ok(trip);
        } else {
            // 로그인이 필요한 경우에 대한 처리
            String message = "로그인이 필요합니다.";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
    }

    /**
     * show Travel list
     * @param authentication jwt
     * @return List Travel
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
     *  delete travel
     * @param authentication jwt
     * @param tripId travel id
     * @return ok
     */
    @DeleteMapping("/user/Travel/delete/{tripId}")
    public ResponseEntity<?> delTravel(Authentication authentication,
//                            @RequestBody Travel travel,
                              @PathVariable Integer tripId
    ){
        travelRepository.deleteById(tripId);
        return ResponseEntity.ok(tripId);
    }


    /**
     * Travel 삭제
     *
     * @param authentication : jwt
     * @param travel : travel
     * @return String :: DeletedTravelTitle
     */
//    @DeleteMapping("/user/Travel/delete")
//    public String delTravel(Authentication authentication, @RequestBody Travel travel){
//        String del = travel.getTitle();
//        travelRepository.delete(travel);
//        return del;
//    }

}
