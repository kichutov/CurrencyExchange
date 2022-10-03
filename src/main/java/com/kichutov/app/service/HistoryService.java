package com.kichutov.app.service;

import com.kichutov.app.dao.HistoryDAO;
import com.kichutov.app.model.AppUser;
import com.kichutov.app.model.Transaction;
import com.kichutov.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {

    private final TransactionRepository transactionRepository;
    private final HistoryDAO historyDAO;

    @Autowired
    public HistoryService(TransactionRepository transactionRepository,
                          HistoryDAO historyDAO) {
        this.transactionRepository = transactionRepository;
        this.historyDAO = historyDAO;
    }

    public List<Transaction> getTransactionList(AppUser appUser, LocalDate selectedDate, String sourceCurrency, String targetCurrency) {
        List<Transaction> transactionList;
        if (sourceCurrency.equals("Все валюты") && targetCurrency.equals("Все валюты")) {
            transactionList = transactionRepository.getTransactionByAppUserAndTransactionDate(appUser, selectedDate);
        } else if (sourceCurrency.equals("Все валюты")) {
            transactionList = transactionRepository.getTransactionByAppUserAndTransactionDateAndTargetCurrency(appUser, selectedDate, targetCurrency);
        } else if (targetCurrency.equals("Все валюты")) {
            transactionList = transactionRepository.getTransactionsByAppUserAndTransactionDateAndSourceCurrency(appUser, selectedDate, sourceCurrency);
        } else {
            transactionList = transactionRepository.getTransactionsByAppUserAndTransactionDateAndSourceCurrencyAndTargetCurrency(appUser, selectedDate, sourceCurrency, targetCurrency);
        }
        return transactionList;
    }

    public List<String> getSourceCurrencyList(AppUser appUser) {
        List<String> sourceCurrencyList = new ArrayList<>();
        sourceCurrencyList.add("Все валюты");
        sourceCurrencyList.addAll(historyDAO.getSourceCurrencyList(appUser));
        return sourceCurrencyList;
    }

    public List<String> getTargetCurrencyList(AppUser appUser) {
        List<String> targetCurrencyList = new ArrayList<>();
        targetCurrencyList.add("Все валюты");
        targetCurrencyList.addAll(historyDAO.getTargetCurrencyList(appUser));
        return targetCurrencyList;
    }

}
