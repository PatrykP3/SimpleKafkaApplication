package org.nowpat.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class NBPRates {

    private String table;
    private String no;
    private LocalDate effectiveDate;
    private List<NBPCurrencyRate> rates;
}
