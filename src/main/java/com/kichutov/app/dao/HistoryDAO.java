package com.kichutov.app.dao;

import com.kichutov.app.model.AppUser;
import com.kichutov.app.model.Transaction;
import com.kichutov.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryDAO {

    private final TransactionRepository transactionRepository;

    @Autowired
    public HistoryDAO(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public LocalDate getEarlyTransactionDate(AppUser appUser) {
        List<Transaction> transactionList = transactionRepository.getTransactionByAppUser(appUser);
        LocalDate earlyTransactionDate = transactionList
                .stream()
                .map(Transaction::getTransactionDate)
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now());
        return earlyTransactionDate;
    }

    public LocalDate getLatestTransactionDate(AppUser appUser) {
        List<Transaction> transactionList = transactionRepository.getTransactionByAppUser(appUser);
        LocalDate latestTransactionDate = transactionList
                .stream()
                .map(Transaction::getTransactionDate)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now());
        return latestTransactionDate;
    }

    public List<String> getSourceCurrencyList(AppUser appUser) {
        List<Transaction> transactionList = transactionRepository.getTransactionByAppUser(appUser);
        List<String> sourceCurrencyList = transactionList
                .stream()
                .map(Transaction::getSourceCurrency)
                .distinct()
                .collect(Collectors.toList());
        return sourceCurrencyList;
    }

    public List<String> getTargetCurrencyList(AppUser appUser) {
        List<Transaction> transactionList = transactionRepository.getTransactionByAppUser(appUser);
        List<String> targetCurrencyList = transactionList
                .stream()
                .map(Transaction::getTargetCurrency)
                .distinct()
                .collect(Collectors.toList());
        return targetCurrencyList;
    }
}
