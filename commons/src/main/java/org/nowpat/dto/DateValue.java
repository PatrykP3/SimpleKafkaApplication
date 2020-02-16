package org.nowpat.dto;

import java.time.LocalDate;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DateValue {

    private LocalDate date;
    private Float value;
}
