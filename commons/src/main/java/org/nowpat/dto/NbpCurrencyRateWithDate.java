package org.nowpat.dto;

import java.time.LocalDate;

import javax.persistence.Column;
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
public class NbpCurrencyRateWithDate {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name="table_name")
    private Character table;
    private String code;
    private LocalDate date;
    private Float mid;
}
