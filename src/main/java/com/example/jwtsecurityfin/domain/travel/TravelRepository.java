package com.example.jwtsecurityfin.domain.travel;

import com.example.jwtsecurityfin.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel, Integer> {
    List<Travel> findAllByUser(User user);
    Optional<Travel> findByTitle(String title);
}
