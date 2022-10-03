package com.kichutov.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table (name = "currency")
public class Currency {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "num_code")
    private Integer numCode;
    @Column(name = "char_code")
    private String charCode;
    @Column(name = "nominal")
    private BigDecimal nominal;
    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private BigDecimal value;
    @Column(name = "update_date")
    private LocalDate updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumCode() {
        return numCode;
    }

    public void setNumCode(Integer numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                ", numCode=" + numCode +
                ", charCode='" + charCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", updateDate=" + updateDate +
                '}';
    }
}
