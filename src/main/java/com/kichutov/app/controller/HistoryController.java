package com.kichutov.app.controller;

import com.kichutov.app.dao.HistoryDAO;
import com.kichutov.app.model.AppUser;
import com.kichutov.app.model.Transaction;
import com.kichutov.app.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HistoryController {

    private final HistoryDAO historyDAO;
    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryDAO historyDAO,
                             HistoryService historyService) {
        this.historyDAO = historyDAO;
        this.historyService = historyService;
    }

    @GetMapping("/history")
    public String history(
            @AuthenticationPrincipal AppUser appUser,
            @RequestParam(value = "selectedDate", required = false) String selectedDate,
            @RequestParam(value = "sourceCurrency", required = false) String sourceCurrency,
            @RequestParam(value = "targetCurrency", required = false) String targetCurrency,
            Model model) {

        model.addAttribute("earlyTransactionDate", historyDAO.getEarlyTransactionDate(appUser));
        model.addAttribute("latestTransactionDate", historyDAO.getLatestTransactionDate(appUser));

        List<String> sourceCurrencyList = historyService.getSourceCurrencyList(appUser);
        List<String> targetCurrencyList = historyService.getTargetCurrencyList(appUser);

        model.addAttribute("sourceCurrencyList", sourceCurrencyList);
        model.addAttribute("targetCurrencyList", targetCurrencyList);

        model.addAttribute("sourceCurrency", sourceCurrency);
        model.addAttribute("targetCurrency", targetCurrency);
        model.addAttribute("selectedDate", selectedDate);

        if (!(selectedDate == null || sourceCurrency == null || targetCurrency == null)) {
            List<Transaction> transactionList = historyService.getTransactionList(appUser, LocalDate.parse(selectedDate), sourceCurrency, targetCurrency);
            model.addAttribute("transactionList", transactionList);
        }

        return "history";
    }
}
