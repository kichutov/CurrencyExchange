package com.kichutov.app.repository;

import com.kichutov.app.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findCurrencyByCharCode(String charCode);

}
