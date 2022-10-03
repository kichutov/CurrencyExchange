package com.kichutov.app.service;

import com.kichutov.app.repository.CurrencyRepository;
import com.kichutov.app.model.AppUser;
import com.kichutov.app.model.Currency;
import com.kichutov.app.model.Transaction;
import com.kichutov.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class ExchangeService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyService currencyService;
    private final TransactionRepository transactionRepository;

    @Autowired
    public ExchangeService(CurrencyRepository currencyRepository,
                           CurrencyService currencyService,
                           TransactionRepository transactionRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyService = currencyService;
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getExchangeResult(AppUser appUser, String sourceCurrency, String targetCurrency, BigDecimal sourceAmount) {
        // get CharCodes from Currencies
        String sourceCurrencyCharCode = sourceCurrency.substring(0,3);
        String targetCurrencyCharCode = targetCurrency.substring(0,3);

        // get objects of Currencies from database
        Currency sourceCurrencyFromDB = currencyRepository.findCurrencyByCharCode(sourceCurrencyCharCode);
        Currency targetCurrencyFromDB = currencyRepository.findCurrencyByCharCode(targetCurrencyCharCode);

        // trying to update Currencies Data for the current date
        while (!sourceCurrencyFromDB.getUpdateDate().equals(LocalDate.now()) ||
                !targetCurrencyFromDB.getUpdateDate().equals(LocalDate.now())) {
            currencyService.updateCurrencyData();
            sourceCurrencyFromDB = currencyRepository.findCurrencyByCharCode(sourceCurrencyCharCode);
            targetCurrencyFromDB = currencyRepository.findCurrencyByCharCode(targetCurrencyCharCode);
        }

        // calculate targetAmount
        BigDecimal exchangeRate1 = sourceCurrencyFromDB.getValue().divide(sourceCurrencyFromDB.getNominal(), 4, RoundingMode.HALF_UP);
        BigDecimal exchangeRate2 = targetCurrencyFromDB.getValue().divide(targetCurrencyFromDB.getNominal(), 4, RoundingMode.HALF_UP);
        BigDecimal targetAmount = (sourceAmount.multiply(exchangeRate1)).divide(exchangeRate2, 2, RoundingMode.HALF_UP);

        Transaction transaction = new Transaction(
                sourceCurrency,
                targetCurrency,
                sourceAmount,
                targetAmount,
                LocalDate.now(),
                appUser);
        transactionRepository.save(transaction);


        return targetAmount;
    }
}

