package com.example.jwtsecurityfin.controller.currency;

import com.example.jwtsecurityfin.domain.currency.Currency;
import com.example.jwtsecurityfin.domain.currency.CurrencyRepository;
import com.example.jwtsecurityfin.service.currency.ExchangeRateService;
import com.example.jwtsecurityfin.dto.response.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/exchange")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService, CurrencyRepository currencyRepository) {
        this.exchangeRateService = exchangeRateService;
        this.currencyRepository = currencyRepository;
    }


    @GetMapping("/rate")
    public ResponseEntity<List<Currency>> getExchangeRate() {
        ExchangeRateResponse exchangeRate = exchangeRateService.fetchExchangeRateData();
        Map<String, Double> conversionRates = exchangeRate.getConversion_rates();

        List<Currency> currencies = new ArrayList<>();
        LocalDate updateDate = convertToLocalDate(exchangeRate.getTime_last_update_utc());

        for (Map.Entry<String, Double> entry : conversionRates.entrySet()) {
            String nation = entry.getKey();
            Double rate = entry.getValue();

            Currency currency = new Currency();
            currency.setDate(updateDate);
            currency.setNation(nation);
            currency.setRate(rate);
            currencyRepository.save(currency);
            currencies.add(currency);
        }

        // Save currencies to database or perform any other necessary operations

        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }



    @GetMapping("/rate/v2")
    public ResponseEntity<List<Currency>> getExchangeRateV2() {
        exchangeRateService.fetchExchangeRateDataScheulded();

        return new ResponseEntity<>(exchangeRateService.fetchExchangeRates(),HttpStatus.OK);
    }

    //전체 환율 조회
    @GetMapping("rates")
    public ResponseEntity<List<Currency>> getExchangeRateAll() {

        return new ResponseEntity<>(exchangeRateService.fetchExchangeRates(),HttpStatus.OK);
    }

    private LocalDate convertToLocalDate(String timeLastUpdateUtc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(timeLastUpdateUtc, formatter);
        return localDateTime.toLocalDate();
    }
}



