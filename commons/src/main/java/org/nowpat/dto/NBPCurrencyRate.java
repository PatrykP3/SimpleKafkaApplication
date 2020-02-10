package org.nowpat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NBPCurrencyRate {

    private String currency;
    private String code;
    private Float mid;
}
