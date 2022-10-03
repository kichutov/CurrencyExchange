package com.kichutov.app.repository;

import com.kichutov.app.model.AppUser;
import com.kichutov.app.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> getTransactionByAppUser(AppUser appUser);

    List<Transaction> getTransactionsByAppUserAndTransactionDateAndSourceCurrencyAndTargetCurrency(AppUser appUser,
                                                                                                   LocalDate selectedDate,
                                                                                                   String sourceCurrency,
                                                                                                   String targetCurrency);

    List<Transaction> getTransactionsByAppUserAndTransactionDateAndSourceCurrency(AppUser appUser,
                                                                                  LocalDate selectedDate,
                                                                                  String sourceCurrency);

    List<Transaction> getTransactionByAppUserAndTransactionDateAndTargetCurrency(AppUser appUser,
                                                                                 LocalDate selectedDate,
                                                                                 String targetCurrency);

    List<Transaction> getTransactionByAppUserAndTransactionDate(AppUser appUser,
                                                                LocalDate selectedDate);
}
