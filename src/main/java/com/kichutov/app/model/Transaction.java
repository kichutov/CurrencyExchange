package com.kichutov.app.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "source_currency")
    private String sourceCurrency;
    @Column(name = "target_currency")
    private String targetCurrency;
    @Column(name = "source_amount")
    private BigDecimal sourceAmount;
    @Column(name = "target_amount")
    private BigDecimal targetAmount;
    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUser appUser;


    public Transaction(){
    }

    public Transaction (String sourceCurrency,
                        String targetCurrency,
                        BigDecimal sourceAmount,
                        BigDecimal targetAmount,
                        LocalDate transactionDate,
                        AppUser appUser) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
        this.transactionDate = transactionDate;
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", sourceCurrency='" + sourceCurrency + '\'' +
                ", targetCurrency='" + targetCurrency + '\'' +
                ", sourceAmount=" + sourceAmount +
                ", targetAmount=" + targetAmount +
                ", transactionDate=" + transactionDate +
                ", appUser=" + appUser +
                '}';
    }
}
