package org.nowpat.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CurrencyMeanData {

    @Id
    private String code;
    @Column(name="table_name")
    private Character table;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int count;
    private float sum;
}
