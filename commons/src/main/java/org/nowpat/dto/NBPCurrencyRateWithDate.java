package org.nowpat.dto;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NBPCurrencyRateWithDate {

    @Id
    @GeneratedValue
    private Long id;
    private String code;
    private LocalDate date;
    private Float mid;
}
