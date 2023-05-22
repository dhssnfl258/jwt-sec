package com.example.jwtsecurityfin.service.currency;

import com.example.jwtsecurityfin.domain.currency.Currency;
import com.example.jwtsecurityfin.domain.currency.CurrencyRepository;
import com.example.jwtsecurityfin.dto.response.ExchangeRateResponse;
import io.jsonwebtoken.lang.Collections;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRateService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final CurrencyRepository currencyRepository;

    @Autowired
    public ExchangeRateService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
    // 매일 자정마다 실행되도록 설정
    @Scheduled(cron = "0 0 0 * * *")
    public ExchangeRateResponse fetchExchangeRateData() {
        String apiUrl = "https://v6.exchangerate-api.com/v6/69caa501e7b43ee60009f2e3/latest/KRW?apiKey=69caa501e7b43ee60009f2e3";
        // API 호출 및 데이터 가져오기
        ExchangeRateResponse response = restTemplate.getForObject(apiUrl, ExchangeRateResponse.class);

        // 가져온 환율 정보를 처리하는 로직 작성
        // 예시: 로그에 출력
        System.out.println("Exchange rates: " + response.getTerms_of_use());
        return restTemplate.getForObject(apiUrl, ExchangeRateResponse.class);
    }
    

    //4시간 마다 스케쥴 작업
    @Scheduled(cron = "0 0 9/6 * * *")
    public void fetchExchangeRateDataScheulded() {

        LocalDate now = LocalDate.now();

        //오늘 날짜로 환율 데이터가 있는지 확인한다.
        List<Currency> nowCurrency = currencyRepository.findCurrenyByDate(now);
        System.out.println("nowCurreny : " + nowCurrency);
        //환율 데이터가 없다면 API 호출해서 저장한다.
        if (Collections.isEmpty(nowCurrency)) {
            String apiUrl = "https://v6.exchangerate-api.com/v6/69caa501e7b43ee60009f2e3/latest/KRW?apiKey=69caa501e7b43ee60009f2e3";
            // API 호출 및 데이터 가져오기
            ExchangeRateResponse response = restTemplate.getForObject(apiUrl, ExchangeRateResponse.class);

            System.out.println("Exchange rates: " + response.getTerms_of_use());
            ExchangeRateResponse exchangeRate = restTemplate.getForObject(apiUrl,
                ExchangeRateResponse.class);

            Map<String, Double> conversionRates = exchangeRate.getConversion_rates();

            LocalDate updateDate = convertToLocalDate(exchangeRate.getTime_last_update_utc());

            for (Map.Entry<String, Double> entry : conversionRates.entrySet()) {
                String nation = entry.getKey();
                Double rate = entry.getValue();

                Currency currency = new Currency();
                currency.setDate(updateDate);
                currency.setNation(nation);
                currency.setRate(rate);
                currencyRepository.save(currency);
            }
        }
        else {
            System.out.println("현재 날짜의 데이터는 이미 저장되어 있습니다 : " + now);
        }

    }

    private LocalDate convertToLocalDate(String timeLastUpdateUtc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(timeLastUpdateUtc, formatter);
        return localDateTime.toLocalDate();
    }

    
    //전체 환율 데이터 조회하기
    public List<Currency> fetchExchangeRates(){

        List<Currency> currenyAll = currencyRepository.findCurrenyAll();

        return currenyAll;
    }
}
